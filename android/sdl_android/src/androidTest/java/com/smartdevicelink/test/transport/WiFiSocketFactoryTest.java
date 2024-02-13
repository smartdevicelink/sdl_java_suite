package com.smartdevicelink.test.transport;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.util.Log;

import com.smartdevicelink.transport.utl.WiFiSocketFactory;

import junit.framework.TestCase;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.net.SocketFactory;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This is a unit test class for the WiFiSocketFactory class:
 * {@link com.smartdevicelink.transport.utl.WiFiSocketFactory}
 * <p>
 * Requires LOLLIPOP or later since the tests use android.net.NetworkCapabilities class
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class WiFiSocketFactoryTest extends TestCase {

    private static final String TAG = WiFiSocketFactoryTest.class.getSimpleName();

    private Context mMockContext;
    private PackageManager mMockPackageManager;
    private ConnectivityManager mMockConnMan;
    private SocketFactory mMockSocketFactory; // this is the SocketFactory that creates mWiFiBoundSocket
    private Socket mWiFiBoundSocket;

    private enum FactoryRet {
        RETURNS_NULL,
        RETURNS_CORRECT_FACTORY,
        RETURNS_ANOTHER_FACTORY,
    }

    private class MockNetworkConfig {
        // true to make a null Network
        boolean isNull;
        // specify the transport type of the Network
        int transportType;
        // spcify the type of SocketFactory returned from this Network
        FactoryRet factoryReturnValue;

        MockNetworkConfig(boolean isNull, int transportType, FactoryRet factoryReturnValue) {
            this.isNull = isNull;
            this.transportType = transportType;
            this.factoryReturnValue = factoryReturnValue;
        }
    }

    private void setupMockNetworks(MockNetworkConfig[] configs) {
        if (configs == null) {
            when(mMockConnMan.getAllNetworks()).thenReturn(null);
            return;
        }

        List<Network> networkList = new ArrayList<Network>(configs.length);

        for (MockNetworkConfig config : configs) {
            if (config.isNull) {
                networkList.add(null);
                continue;
            }

            Network network = mock(Network.class);

            NetworkCapabilities networkCapabilities = createNetworkCapabilitiesWithTransport(config.transportType);
            when(mMockConnMan.getNetworkCapabilities(network)).thenReturn(networkCapabilities);

            SocketFactory factory = null;
            switch (config.factoryReturnValue) {
                case RETURNS_NULL:
                    break;
                case RETURNS_CORRECT_FACTORY:
                    factory = mMockSocketFactory;
                    break;
                case RETURNS_ANOTHER_FACTORY:
                    // create another mock SocketFactory instance
                    factory = mock(SocketFactory.class);
                    break;
            }
            when(network.getSocketFactory()).thenReturn(factory);

            networkList.add(network);
        }

        when(mMockConnMan.getAllNetworks()).thenReturn(networkList.toArray(new Network[networkList.size()]));
    }

    private static NetworkCapabilities createNetworkCapabilitiesWithTransport(int transport) {
        // Creates a dummy NetworkCapabilities instance.
        // Since NetworkCapabilities class is 'final', we cannot create its mock. To create a dummy
        // instance, here we use reflection to call its constructor and a method that are marked
        // with "@hide".
        // It is possible that these methods will not be available in a future version of Android.
        // In that case we need to update our code accordingly.
        Class<NetworkCapabilities> c = NetworkCapabilities.class;
        try {
            Method addTransportTypeMethod = c.getMethod("addTransportType", int.class);
            addTransportTypeMethod.setAccessible(true);

            NetworkCapabilities instance = c.getDeclaredConstructor().newInstance();
            addTransportTypeMethod.invoke(instance, transport);
            Log.e(TAG, "Yes successful");
            return instance;
        } catch (Exception e) {
            Log.e(TAG, "Failed to create NetworkCapabilities instance using reflection: ", e);
            return null;
        }
    }

    // from https://stackoverflow.com/questions/40300469/mock-build-version-with-mockito
    // and https://stackoverflow.com/questions/13755117/android-changing-private-static-final-field-using-java-reflection
    private static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);
//        Field modifiersField = Field.class.getDeclaredField("modifiers");
        // This call might fail on some devices (for example, Nexus 6 with Android 5.0.1).
        // If that's the issue, we might want to introduce PowerMock.
        Field modifiersField = Field.class.getDeclaredField("accessFlags");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
    }


    @Override
    public void setUp() throws Exception {
        super.setUp();

        mMockContext = mock(Context.class);
        mMockPackageManager = mock(PackageManager.class);
        mMockConnMan = mock(ConnectivityManager.class);

        when(mMockContext.getPackageManager()).thenReturn(mMockPackageManager);
        when(mMockContext.getPackageName()).thenReturn("dummyPackageName");
        when(mMockContext.getSystemService(eq(Context.CONNECTIVITY_SERVICE))).thenReturn(mMockConnMan);

        when(mMockPackageManager.checkPermission(eq(Manifest.permission.ACCESS_NETWORK_STATE), anyString())).thenReturn(PackageManager.PERMISSION_GRANTED);

        mMockSocketFactory = mock(SocketFactory.class);
        mWiFiBoundSocket = new Socket();
        when(mMockSocketFactory.createSocket()).thenReturn(mWiFiBoundSocket);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    // test the happy path
    public void testWithWiFiNetwork() {
        setupMockNetworks(new MockNetworkConfig[]{
                new MockNetworkConfig(false, NetworkCapabilities.TRANSPORT_CELLULAR, FactoryRet.RETURNS_ANOTHER_FACTORY),
                new MockNetworkConfig(false, NetworkCapabilities.TRANSPORT_WIFI, FactoryRet.RETURNS_CORRECT_FACTORY),
        });

        Socket ret = WiFiSocketFactory.createSocket(mMockContext);

        assertNotNull("createSocket() should always return a Socket instance", ret);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            assertEquals("Returned Socket should be created through SocketFactory", mWiFiBoundSocket, ret);
        }
    }

    // test the case where SDK_INT is less than 21
    /* This is disabled since Travis CI uses an AVD with 5.1.1 and setFinalStatic() doesn't work on it.
    public void testPriorToLollipop() throws Exception {
        setupMockNetworks(new MockNetworkConfig[] {
                new MockNetworkConfig(false, NetworkCapabilities.TRANSPORT_CELLULAR, FactoryRet.RETURNS_ANOTHER_FACTORY),
                new MockNetworkConfig(false, NetworkCapabilities.TRANSPORT_WIFI, FactoryRet.RETURNS_CORRECT_FACTORY),
        });

        // simulate SDK_INT to less than LOLLIPOP
        int previousSDKInt = Build.VERSION.SDK_INT;
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), Build.VERSION_CODES.KITKAT_WATCH);

        Socket ret = WiFiSocketFactory.createSocket(mMockContext);

        // make sure we revert our change
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), previousSDKInt);

        assertNotNull("createSocket() should always return a Socket instance", ret);
        assertNotSame("Returned Socket shouldn't be created through SocketFactory since it is not available prior to LOLLIPOP",
                mWiFiBoundSocket, ret);
    }
    */

    // test the case where we do not have ACCESS_NETWORK_STATE permission
    public void testWithoutPermission() {
        setupMockNetworks(new MockNetworkConfig[]{
                new MockNetworkConfig(false, NetworkCapabilities.TRANSPORT_WIFI, FactoryRet.RETURNS_CORRECT_FACTORY),
        });

        // simulate the case where required permission isn't available
        when(mMockPackageManager.checkPermission(eq(Manifest.permission.ACCESS_NETWORK_STATE), anyString())).thenReturn(PackageManager.PERMISSION_DENIED);

        Socket ret = WiFiSocketFactory.createSocket(mMockContext);

        assertNotNull("createSocket() should always return a Socket instance", ret);
        assertNotSame("Returned Socket shouldn't be created through SocketFactory since we don't have required permission",
                mWiFiBoundSocket, ret);
    }

    // test the case where context.getPackageManager() returns null
    public void testPackageManagerNull() {
        setupMockNetworks(new MockNetworkConfig[]{
                new MockNetworkConfig(false, NetworkCapabilities.TRANSPORT_WIFI, FactoryRet.RETURNS_CORRECT_FACTORY),
        });

        // simulate the case where ConnectivityManager isn't available
        when(mMockContext.getPackageManager()).thenReturn(null);

        Socket ret = WiFiSocketFactory.createSocket(mMockContext);

        assertNotNull("createSocket() should always return a Socket instance", ret);
        assertNotSame("Returned Socket shouldn't be created through SocketFactory since PackageManager isn't available",
                mWiFiBoundSocket, ret);
    }

    // test the case where getSystemService() returns null
    public void testConnectivityManagerNull() {
        setupMockNetworks(new MockNetworkConfig[]{
                new MockNetworkConfig(false, NetworkCapabilities.TRANSPORT_WIFI, FactoryRet.RETURNS_CORRECT_FACTORY),
        });

        // simulate the case where ConnectivityManager isn't available
        when(mMockContext.getSystemService(eq(Context.CONNECTIVITY_SERVICE))).thenReturn(null);

        Socket ret = WiFiSocketFactory.createSocket(mMockContext);

        assertNotNull("createSocket() should always return a Socket instance", ret);
        assertNotSame("Returned Socket shouldn't be created through SocketFactory since ConnectivityManager isn't working",
                mWiFiBoundSocket, ret);
    }

    // test the case where ConnectivityManager returns null for the network list
    public void testNetworkListNull() {
        setupMockNetworks(null);

        Socket ret = WiFiSocketFactory.createSocket(mMockContext);

        assertNotNull("createSocket() should always return a Socket instance", ret);
        assertNotSame("Returned Socket shouldn't be created through SocketFactory since Network list isn't available",
                mWiFiBoundSocket, ret);
    }

    // test the case where the network list contains a null for Network instance
    public void testNetworkListHasNull() {
        setupMockNetworks(new MockNetworkConfig[]{
                // multiple Network instances in the list, the first one being NULL
                new MockNetworkConfig(true, 0, FactoryRet.RETURNS_ANOTHER_FACTORY),
                new MockNetworkConfig(false, NetworkCapabilities.TRANSPORT_WIFI, FactoryRet.RETURNS_CORRECT_FACTORY),
        });

        Socket ret = WiFiSocketFactory.createSocket(mMockContext);

        assertNotNull("createSocket() should always return a Socket instance", ret);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            assertEquals("Returned Socket should be created through SocketFactory", mWiFiBoundSocket, ret);
        }
    }

    // test the case where the phone isn't connected to Wi-Fi network
    public void testNoWiFiNetwork() {
        setupMockNetworks(new MockNetworkConfig[]{
                // none of the instances has TRANSPORT_WIFI in their capabilities
                new MockNetworkConfig(false, NetworkCapabilities.TRANSPORT_CELLULAR, FactoryRet.RETURNS_ANOTHER_FACTORY),
                new MockNetworkConfig(false, NetworkCapabilities.TRANSPORT_BLUETOOTH, FactoryRet.RETURNS_ANOTHER_FACTORY),
                new MockNetworkConfig(false, NetworkCapabilities.TRANSPORT_VPN, FactoryRet.RETURNS_ANOTHER_FACTORY),
        });

        Socket ret = WiFiSocketFactory.createSocket(mMockContext);

        assertNotNull("createSocket() should always return a Socket instance", ret);
        assertNotSame("Returned Socket shouldn't be created through SocketFactory since Wi-Fi network isn't available",
                mWiFiBoundSocket, ret);
    }

    // test the case where we get null for SocketFactory
    public void testSocketFactoryNull() {
        setupMockNetworks(new MockNetworkConfig[]{
                new MockNetworkConfig(false, NetworkCapabilities.TRANSPORT_CELLULAR, FactoryRet.RETURNS_ANOTHER_FACTORY),
                new MockNetworkConfig(false, NetworkCapabilities.TRANSPORT_WIFI, FactoryRet.RETURNS_NULL),
        });

        Socket ret = WiFiSocketFactory.createSocket(mMockContext);

        assertNotNull("createSocket() should always return a Socket instance", ret);
        assertNotSame("Returned Socket shouldn't be created through SocketFactory since SocketFactory isn't available",
                mWiFiBoundSocket, ret);
    }

    // test the case where we get a null for SocketFactory, then a valid one for another
    public void testSocketFactoryNull2() {
        setupMockNetworks(new MockNetworkConfig[]{
                new MockNetworkConfig(false, NetworkCapabilities.TRANSPORT_CELLULAR, FactoryRet.RETURNS_ANOTHER_FACTORY),
                new MockNetworkConfig(false, NetworkCapabilities.TRANSPORT_WIFI, FactoryRet.RETURNS_NULL),
                new MockNetworkConfig(false, NetworkCapabilities.TRANSPORT_WIFI, FactoryRet.RETURNS_CORRECT_FACTORY),
        });

        Socket ret = WiFiSocketFactory.createSocket(mMockContext);

        assertNotNull("createSocket() should always return a Socket instance", ret);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            assertEquals("Returned Socket should be created through SocketFactory", mWiFiBoundSocket, ret);
        }
    }

    // test the case where we get an exception with SocketFactory.createSocket()
    public void testFactoryReturnsException() throws IOException {
        setupMockNetworks(new MockNetworkConfig[]{
                new MockNetworkConfig(false, NetworkCapabilities.TRANSPORT_WIFI, FactoryRet.RETURNS_CORRECT_FACTORY),
        });

        when(mMockSocketFactory.createSocket()).thenThrow(new IOException("Dummy IOException for testing!"));

        Socket ret = WiFiSocketFactory.createSocket(mMockContext);

        assertNotNull("createSocket() should always return a Socket instance", ret);
        assertNotSame("Returned Socket shouldn't be created through SocketFactory since it throws an IOException",
                mWiFiBoundSocket, ret);
    }

    // Test the case we get multiple Network instances with Wi-Fi transport, and the SocketFactory of
    // the first one throws Exception and the other one succeeds.
    // This is to simulate Samsung Galaxy S9.
    public void testFactoryReturnsException2() throws IOException {
        setupMockNetworks(new MockNetworkConfig[]{
                new MockNetworkConfig(false, NetworkCapabilities.TRANSPORT_WIFI, FactoryRet.RETURNS_CORRECT_FACTORY),
                new MockNetworkConfig(false, NetworkCapabilities.TRANSPORT_WIFI, FactoryRet.RETURNS_CORRECT_FACTORY),
        });

        when(mMockSocketFactory.createSocket()).thenThrow(new IOException("Dummy IOException for testing!"))
                .thenReturn(mWiFiBoundSocket);

        Socket ret = WiFiSocketFactory.createSocket(mMockContext);

        assertNotNull("createSocket() should always return a Socket instance", ret);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            assertEquals("Returned Socket should be created through SocketFactory", mWiFiBoundSocket, ret);
        }
    }
}

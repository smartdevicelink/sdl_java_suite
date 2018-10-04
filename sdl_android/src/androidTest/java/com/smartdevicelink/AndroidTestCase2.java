package com.smartdevicelink;

import android.content.Context;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;

import junit.framework.TestCase;


public class AndroidTestCase2 extends TestCase {

    public Context mContext;

    public AndroidTestCase2(){
        mContext = InstrumentationRegistry.getTargetContext();
    }

    public Context getContext(){
        return mContext;
    }

    public void setContext(Context context){}


    public void assertActivityRequiresPermission(String packageName, String className, String permission){}
    public void assertReadingContentUriRequiresPermission(Uri uri, String permission){}
    public void assertWritingContentUriRequiresPermission(Uri uri, String permission){}


    protected void	scrubClass(Class<?> testCaseClass){}
    protected void setUp() throws Exception{}
    protected void tearDown() throws Exception{}

}

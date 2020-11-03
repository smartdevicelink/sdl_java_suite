package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.MediaServiceData;
import com.smartdevicelink.proxy.rpc.enums.MediaType;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.MediaServiceData}
 */
public class MediaServiceDataTests extends TestCase {

    private MediaServiceData msg;

    @Override
    public void setUp() {

        msg = new MediaServiceData();
        msg.setMediaType(TestValues.GENERAL_MEDIATYPE);
        msg.setMediaTitle(TestValues.GENERAL_STRING);
        msg.setMediaArtist(TestValues.GENERAL_STRING);
        msg.setMediaAlbum(TestValues.GENERAL_STRING);
        msg.setMediaImage(TestValues.GENERAL_IMAGE);
        msg.setPlaylistName(TestValues.GENERAL_STRING);
        msg.setIsExplicit(TestValues.GENERAL_BOOLEAN);
        msg.setTrackPlaybackProgress(TestValues.GENERAL_INTEGER);
        msg.setTrackPlaybackDuration(TestValues.GENERAL_INTEGER);
        msg.setQueuePlaybackProgress(TestValues.GENERAL_INTEGER);
        msg.setQueuePlaybackDuration(TestValues.GENERAL_INTEGER);
        msg.setQueueCurrentTrackNumber(TestValues.GENERAL_INTEGER);
        msg.setQueueTotalTrackCount(TestValues.GENERAL_INTEGER);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        MediaType mediaType = msg.getMediaType();
        String mediaTitle = msg.getMediaTitle();
        String mediaArtist = msg.getMediaArtist();
        String mediaAlbum = msg.getMediaAlbum();
        Image mediaImage = msg.getMediaImage();
        String playlistName = msg.getPlaylistName();
        boolean isExplicit = msg.getIsExplicit();
        Integer trackPlaybackProgress = msg.getTrackPlaybackProgress();
        Integer trackPlaybackDuration = msg.getTrackPlaybackDuration();
        Integer queuePlaybackProgress = msg.getQueuePlaybackProgress();
        Integer queuePlaybackDuration = msg.getQueuePlaybackDuration();
        Integer queueCurrentTrackNumber = msg.getQueueCurrentTrackNumber();
        Integer queueTotalTrackCount = msg.getQueueTotalTrackCount();

        // Valid Tests
        assertEquals(TestValues.GENERAL_MEDIATYPE, mediaType);
        assertEquals(TestValues.GENERAL_STRING, mediaTitle);
        assertEquals(TestValues.GENERAL_STRING, mediaArtist);
        assertEquals(TestValues.GENERAL_STRING, mediaAlbum);
        assertEquals(TestValues.GENERAL_IMAGE, mediaImage);
        assertEquals(TestValues.GENERAL_STRING, playlistName);
        assertEquals(TestValues.GENERAL_BOOLEAN, isExplicit);
        assertEquals(TestValues.GENERAL_INTEGER, trackPlaybackProgress);
        assertEquals(TestValues.GENERAL_INTEGER, trackPlaybackDuration);
        assertEquals(TestValues.GENERAL_INTEGER, queuePlaybackProgress);
        assertEquals(TestValues.GENERAL_INTEGER, queuePlaybackDuration);
        assertEquals(TestValues.GENERAL_INTEGER, queueCurrentTrackNumber);
        assertEquals(TestValues.GENERAL_INTEGER, queueTotalTrackCount);

        // Invalid/Null Tests
        MediaServiceData msg = new MediaServiceData();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getMediaType());
        assertNull(TestValues.NULL, msg.getMediaTitle());
        assertNull(TestValues.NULL, msg.getMediaArtist());
        assertNull(TestValues.NULL, msg.getMediaAlbum());
        assertNull(TestValues.NULL, msg.getMediaImage());
        assertNull(TestValues.NULL, msg.getPlaylistName());
        assertNull(TestValues.NULL, msg.getIsExplicit());
        assertNull(TestValues.NULL, msg.getTrackPlaybackProgress());
        assertNull(TestValues.NULL, msg.getTrackPlaybackDuration());
        assertNull(TestValues.NULL, msg.getQueuePlaybackProgress());
        assertNull(TestValues.NULL, msg.getQueuePlaybackDuration());
        assertNull(TestValues.NULL, msg.getQueueCurrentTrackNumber());
        assertNull(TestValues.NULL, msg.getQueueTotalTrackCount());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(MediaServiceData.KEY_MEDIA_TYPE, TestValues.GENERAL_MEDIATYPE);
            reference.put(MediaServiceData.KEY_MEDIA_TITLE, TestValues.GENERAL_STRING);
            reference.put(MediaServiceData.KEY_MEDIA_ARTIST, TestValues.GENERAL_STRING);
            reference.put(MediaServiceData.KEY_MEDIA_ALBUM, TestValues.GENERAL_STRING);
            reference.put(MediaServiceData.KEY_MEDIA_IMAGE, TestValues.GENERAL_IMAGE);
            reference.put(MediaServiceData.KEY_PLAYLIST_NAME, TestValues.GENERAL_STRING);
            reference.put(MediaServiceData.KEY_IS_EXPLICIT, TestValues.GENERAL_BOOLEAN);
            reference.put(MediaServiceData.KEY_TRACK_PLAYBACK_PROGRESS, TestValues.GENERAL_INTEGER);
            reference.put(MediaServiceData.KEY_TRACK_PLAYBACK_DURATION, TestValues.GENERAL_INTEGER);
            reference.put(MediaServiceData.KEY_QUEUE_PLAYBACK_PROGRESS, TestValues.GENERAL_INTEGER);
            reference.put(MediaServiceData.KEY_QUEUE_PLAYBACK_DURATION, TestValues.GENERAL_INTEGER);
            reference.put(MediaServiceData.KEY_QUEUE_CURRENT_TRACK_NUMBER, TestValues.GENERAL_INTEGER);
            reference.put(MediaServiceData.KEY_QUEUE_TOTAL_TRACK_COUNT, TestValues.GENERAL_INTEGER);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();

                if (key.equals(MediaServiceData.KEY_MEDIA_IMAGE)) {
                    JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                    assertTrue(TestValues.TRUE, Validator.validateImage(TestValues.GENERAL_IMAGE, new Image(hashTest)));
                } else {
                    assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }

}

package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.MediaServiceData;
import com.smartdevicelink.proxy.rpc.enums.MediaType;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.MediaServiceData}
 */
public class MediaServiceDataTests extends TestCase {

	private MediaServiceData msg;

	@Override
	public void setUp(){

		msg = new MediaServiceData();
		msg.setMediaType(Test.GENERAL_MEDIATYPE);
		msg.setMediaTitle(Test.GENERAL_STRING);
		msg.setMediaArtist(Test.GENERAL_STRING);
		msg.setMediaAlbum(Test.GENERAL_STRING);
		msg.setPlaylistName(Test.GENERAL_STRING);
		msg.setIsExplicit(Test.GENERAL_BOOLEAN);
		msg.setTrackPlaybackProgress(Test.GENERAL_INTEGER);
		msg.setTrackPlaybackDuration(Test.GENERAL_INTEGER);
		msg.setQueuePlaybackProgress(Test.GENERAL_INTEGER);
		msg.setQueuePlaybackDuration(Test.GENERAL_INTEGER);
		msg.setQueueCurrentTrackNumber(Test.GENERAL_INTEGER);
		msg.setQueueTotalTrackCount(Test.GENERAL_INTEGER);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
		// Test Values
		MediaType mediaType = msg.getMediaType();
		String mediaTitle = msg.getMediaTitle();
		String mediaArtist = msg.getMediaArtist();
		String mediaAlbum = msg.getMediaAlbum();
		String playlistName = msg.getPlaylistName();
		boolean isExplicit = msg.getIsExplicit();
		Integer trackPlaybackProgress = msg.getTrackPlaybackProgress();
		Integer trackPlaybackDuration = msg.getTrackPlaybackDuration();
		Integer queuePlaybackProgress = msg.getQueuePlaybackProgress();
		Integer queuePlaybackDuration = msg.getQueuePlaybackDuration();
		Integer queueCurrentTrackNumber = msg.getQueueCurrentTrackNumber();
		Integer queueTotalTrackCount = msg.getQueueTotalTrackCount();

		// Valid Tests
		assertEquals(Test.GENERAL_MEDIATYPE, mediaType);
		assertEquals(Test.GENERAL_STRING, mediaTitle);
		assertEquals(Test.GENERAL_STRING, mediaArtist);
		assertEquals(Test.GENERAL_STRING, mediaAlbum);
		assertEquals(Test.GENERAL_STRING, playlistName);
		assertEquals(Test.GENERAL_BOOLEAN, isExplicit);
		assertEquals(Test.GENERAL_INTEGER, trackPlaybackProgress);
		assertEquals(Test.GENERAL_INTEGER, trackPlaybackDuration);
		assertEquals(Test.GENERAL_INTEGER, queuePlaybackProgress);
		assertEquals(Test.GENERAL_INTEGER, queuePlaybackDuration);
		assertEquals(Test.GENERAL_INTEGER, queueCurrentTrackNumber);
		assertEquals(Test.GENERAL_INTEGER, queueTotalTrackCount);

		// Invalid/Null Tests
		MediaServiceData msg = new MediaServiceData();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getMediaType());
		assertNull(Test.NULL, msg.getMediaTitle());
		assertNull(Test.NULL, msg.getMediaArtist());
		assertNull(Test.NULL, msg.getMediaAlbum());
		assertNull(Test.NULL, msg.getPlaylistName());
		assertNull(Test.NULL, msg.getIsExplicit());
		assertNull(Test.NULL, msg.getTrackPlaybackProgress());
		assertNull(Test.NULL, msg.getTrackPlaybackDuration());
		assertNull(Test.NULL, msg.getQueuePlaybackProgress());
		assertNull(Test.NULL, msg.getQueuePlaybackDuration());
		assertNull(Test.NULL, msg.getQueueCurrentTrackNumber());
		assertNull(Test.NULL, msg.getQueueTotalTrackCount());
	}

	public void testJson(){
		JSONObject reference = new JSONObject();

		try{
			reference.put(MediaServiceData.KEY_MEDIA_TYPE, Test.GENERAL_MEDIATYPE);
			reference.put(MediaServiceData.KEY_MEDIA_TITLE, Test.GENERAL_STRING);
			reference.put(MediaServiceData.KEY_MEDIA_ARTIST, Test.GENERAL_STRING);
			reference.put(MediaServiceData.KEY_MEDIA_ALBUM, Test.GENERAL_STRING);
			reference.put(MediaServiceData.KEY_PLAYLIST_NAME, Test.GENERAL_STRING);
			reference.put(MediaServiceData.KEY_IS_EXPLICIT, Test.GENERAL_BOOLEAN);
			reference.put(MediaServiceData.KEY_TRACK_PLAYBACK_PROGRESS, Test.GENERAL_INTEGER);
			reference.put(MediaServiceData.KEY_TRACK_PLAYBACK_DURATION, Test.GENERAL_INTEGER);
			reference.put(MediaServiceData.KEY_QUEUE_PLAYBACK_PROGRESS, Test.GENERAL_INTEGER);
			reference.put(MediaServiceData.KEY_QUEUE_PLAYBACK_DURATION, Test.GENERAL_INTEGER);
			reference.put(MediaServiceData.KEY_QUEUE_CURRENT_TRACK_NUMBER, Test.GENERAL_INTEGER);
			reference.put(MediaServiceData.KEY_QUEUE_TOTAL_TRACK_COUNT, Test.GENERAL_INTEGER);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while(iterator.hasNext()){
				String key = (String) iterator.next();
				assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
			}
		} catch(JSONException e){
			fail(Test.JSON_FAIL);
		}
	}

}

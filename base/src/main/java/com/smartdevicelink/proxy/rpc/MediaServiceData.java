/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this 
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.MediaType;

import java.util.Hashtable;

/**
 * This data is related to what a media service should provide
 */
public class MediaServiceData extends RPCStruct {

	public static final String KEY_MEDIA_TYPE = "mediaType";
	public static final String KEY_MEDIA_TITLE = "mediaTitle";
	public static final String KEY_MEDIA_ARTIST = "mediaArtist";
	public static final String KEY_MEDIA_ALBUM = "mediaAlbum";
	public static final String KEY_MEDIA_IMAGE = "mediaImage";
	public static final String KEY_PLAYLIST_NAME = "playlistName";
	public static final String KEY_IS_EXPLICIT = "isExplicit";
	public static final String KEY_TRACK_PLAYBACK_PROGRESS = "trackPlaybackProgress";
	public static final String KEY_TRACK_PLAYBACK_DURATION = "trackPlaybackDuration";
	public static final String KEY_QUEUE_PLAYBACK_PROGRESS = "queuePlaybackProgress";
	public static final String KEY_QUEUE_PLAYBACK_DURATION = "queuePlaybackDuration";
	public static final String KEY_QUEUE_CURRENT_TRACK_NUMBER = "queueCurrentTrackNumber";
	public static final String KEY_QUEUE_TOTAL_TRACK_COUNT = "queueTotalTrackCount";

	// Constructors

	public MediaServiceData() { }

	public MediaServiceData(Hashtable<String, Object> hash) {
		super(hash);
	}

	// Setters and Getters

	/**
	 * @param mediaType - The type of the currently playing or paused track.
	 */
	public MediaServiceData setMediaType( MediaType mediaType) {
        setValue(KEY_MEDIA_TYPE, mediaType);
        return this;
    }

	/**
	 * @return mediaType - The type of the currently playing or paused track.
	 */
	public MediaType getMediaType() {
		return (MediaType) getObject(MediaType.class,KEY_MEDIA_TYPE);
	}

	/**
	 * Music: The name of the current track
	 * Podcast: The name of the current episode
	 * Audiobook: The name of the current chapter
	 * @param mediaTitle -
	 */
	public MediaServiceData setMediaTitle( String mediaTitle) {
        setValue(KEY_MEDIA_TITLE, mediaTitle);
        return this;
    }

	/**
	 * Music: The name of the current track
	 * Podcast: The name of the current episode
	 * Audiobook: The name of the current chapter
	 * @return mediaTitle -
	 */
	public String getMediaTitle() {
		return getString(KEY_MEDIA_TITLE);
	}

	/**
	 * Music: The name of the current album artist
	 * Podcast: The provider of the podcast (hosts, network, company)
	 * Audiobook: The book author's name
	 * @param mediaArtist -
	 */
	public MediaServiceData setMediaArtist( String mediaArtist) {
        setValue(KEY_MEDIA_ARTIST, mediaArtist);
        return this;
    }

	/**
	 * Music: The name of the current album artist
	 * Podcast: The provider of the podcast (hosts, network, company)
	 * Audiobook: The book author's name
	 * @return mediaArtist -
	 */
	public String getMediaArtist() {
		return getString(KEY_MEDIA_ARTIST);
	}

	/**
	 * Music: The name of the current album
	 * Podcast: The name of the current podcast show
	 * Audiobook: The name of the current book
	 * @param mediaAlbum -
	 */
	public MediaServiceData setMediaAlbum( String mediaAlbum) {
        setValue(KEY_MEDIA_ALBUM, mediaAlbum);
        return this;
    }

	/**
	 * Music: The name of the current album
	 * Podcast: The name of the current podcast show
	 * Audiobook: The name of the current book
	 * @return mediaAlbum -
	 */
	public String getMediaAlbum() {
		return getString(KEY_MEDIA_ALBUM);
	}

	/**
	 * Sets the media image associated with the currently playing media
	 * Music: The album art of the current track
	 * Podcast: The podcast or chapter artwork of the current podcast episode
	 * Audiobook: The book or chapter artwork of the current audiobook
	 * @param mediaImage
	 */
	public MediaServiceData setMediaImage( Image mediaImage) {
        setValue(KEY_MEDIA_IMAGE, mediaImage);
        return this;
    }

	/**
	 * Returns the media image associated with the currently playing media
	 * Music: The album art of the current track
	 * Podcast: The podcast or chapter artwork of the current podcast episode
	 * Audiobook: The book or chapter artwork of the current audiobook
	 */
	public Image getMediaImage(){
		return (Image) getObject(Image.class, KEY_MEDIA_IMAGE);
	}

	/**
	 * Music: The name of the playlist or radio station, if the user is playing from a playlist, otherwise, Null
	 * Podcast: The name of the playlist, if the user is playing from a playlist, otherwise, Null
	 * Audiobook: Likely not applicable, possibly a collection or "playlist" of books
	 * @param playlistName -
	 */
	public MediaServiceData setPlaylistName( String playlistName) {
        setValue(KEY_PLAYLIST_NAME, playlistName);
        return this;
    }

	/**
	 * Music: The name of the playlist or radio station, if the user is playing from a playlist, otherwise, Null
	 * Podcast: The name of the playlist, if the user is playing from a playlist, otherwise, Null
	 * Audiobook: Likely not applicable, possibly a collection or "playlist" of books
	 * @return playlistName -
	 */
	public String getPlaylistName() {
		return getString(KEY_PLAYLIST_NAME);
	}

	/**
	 * @param isExplicit - Whether or not the content currently playing (e.g. the track, episode, or book) contains explicit content
	 */
	public MediaServiceData setIsExplicit( Boolean isExplicit) {
        setValue(KEY_IS_EXPLICIT, isExplicit);
        return this;
    }

	/**
	 * @return isExplicit - Whether or not the content currently playing (e.g. the track, episode, or book) contains explicit content
	 */
	public Boolean getIsExplicit() {
		return getBoolean(KEY_IS_EXPLICIT);
	}

	/**
	 * Music: The current progress of the track in seconds
	 * Podcast: The current progress of the episode in seconds
	 * Audiobook: The current progress of the current segment (e.g. the chapter) in seconds
	 * @param trackPlaybackProgress -
	 */
	public MediaServiceData setTrackPlaybackProgress( Integer trackPlaybackProgress) {
        setValue(KEY_TRACK_PLAYBACK_PROGRESS, trackPlaybackProgress);
        return this;
    }

	/**
	 * Music: The current progress of the track in seconds
	 * Podcast: The current progress of the episode in seconds
	 * Audiobook: The current progress of the current segment (e.g. the chapter) in seconds
	 * @return trackPlaybackProgress -
	 */
	public Integer getTrackPlaybackProgress() {
		return getInteger(KEY_TRACK_PLAYBACK_PROGRESS);
	}

	/**
	 * Music: The total duration of the track in seconds
	 * Podcast: The total duration of the episode in seconds
	 * Audiobook: The total duration of the current segment (e.g. the chapter) in seconds
	 * @param trackPlaybackDuration -
	 */
	public MediaServiceData setTrackPlaybackDuration( Integer trackPlaybackDuration) {
        setValue(KEY_TRACK_PLAYBACK_DURATION, trackPlaybackDuration);
        return this;
    }

	/**
	 * Music: The total duration of the track in seconds
	 * Podcast: The total duration of the episode in seconds
	 * Audiobook: The total duration of the current segment (e.g. the chapter) in seconds
	 * @return trackPlaybackDuration -
	 */
	public Integer getTrackPlaybackDuration() {
		return getInteger(KEY_TRACK_PLAYBACK_DURATION);
	}

	/**
	 * Music: The current progress of the playback queue in seconds
	 * Podcast: The current progress of the playback queue in seconds
	 * Audiobook: The current progress of the playback queue (e.g. the book) in seconds
	 * @param queuePlaybackProgress -
	 */
	public MediaServiceData setQueuePlaybackProgress( Integer queuePlaybackProgress) {
        setValue(KEY_QUEUE_PLAYBACK_PROGRESS, queuePlaybackProgress);
        return this;
    }

	/**
	 * Music: The current progress of the playback queue in seconds
	 * Podcast: The current progress of the playback queue in seconds
	 * Audiobook: The current progress of the playback queue (e.g. the book) in seconds
	 * @return queuePlaybackProgress -
	 */
	public Integer getQueuePlaybackProgress() {
		return getInteger(KEY_QUEUE_PLAYBACK_PROGRESS);
	}

	/**
	 * Music: The total duration of the playback queue in seconds
	 * Podcast: The total duration of the playback queue in seconds
	 * Audiobook: The total duration of the playback queue (e.g. the book) in seconds
	 * @param queuePlaybackDuration -
	 */
	public MediaServiceData setQueuePlaybackDuration( Integer queuePlaybackDuration) {
        setValue(KEY_QUEUE_PLAYBACK_DURATION, queuePlaybackDuration);
        return this;
    }

	/**
	 * Music: The total duration of the playback queue in seconds
	 * Podcast: The total duration of the playback queue in seconds
	 * Audiobook: The total duration of the playback queue (e.g. the book) in seconds
	 * @return queuePlaybackDuration -
	 */
	public Integer getQueuePlaybackDuration() {
		return getInteger(KEY_QUEUE_PLAYBACK_DURATION);
	}

	/**
	 * Music: The current number (1 based) of the track in the playback queue
	 * Podcast: The current number (1 based) of the episode in the playback queue
	 * Audiobook: The current number (1 based) of the episode in the playback queue (e.g. the chapter number in the book)
	 * @param queueCurrentTrackNumber -
	 */
	public MediaServiceData setQueueCurrentTrackNumber( Integer queueCurrentTrackNumber) {
        setValue(KEY_QUEUE_CURRENT_TRACK_NUMBER, queueCurrentTrackNumber);
        return this;
    }

	/**
	 * Music: The current number (1 based) of the track in the playback queue
	 * Podcast: The current number (1 based) of the episode in the playback queue
	 * Audiobook: The current number (1 based) of the episode in the playback queue (e.g. the chapter number in the book)
	 * @return queueCurrentTrackNumber -
	 */
	public Integer getQueueCurrentTrackNumber() {
		return getInteger(KEY_QUEUE_CURRENT_TRACK_NUMBER);
	}

	/**
	 * Music: The total number of tracks in the playback queue
	 * Podcast: The total number of episodes in the playback queue
	 * Audiobook: The total number of sections in the playback queue (e.g. the number of chapters in the book)
	 * @param queueTotalTrackCount -
	 */
	public MediaServiceData setQueueTotalTrackCount( Integer queueTotalTrackCount) {
        setValue(KEY_QUEUE_TOTAL_TRACK_COUNT, queueTotalTrackCount);
        return this;
    }

	/**
	 * Music: The total number of tracks in the playback queue
	 * Podcast: The total number of episodes in the playback queue
	 * Audiobook: The total number of sections in the playback queue (e.g. the number of chapters in the book)
	 * @return queueTotalTrackCount -
	 */
	public Integer getQueueTotalTrackCount() {
		return getInteger(KEY_QUEUE_TOTAL_TRACK_COUNT);
	}

}

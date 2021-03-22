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
package com.smartdevicelink.proxy.rpc.enums;

/**
 * Enumeration of Static Icon Names
 */
public enum StaticIconName {

    /**
     * Icon Name accept call / active phone call in progress / initiate a phone call
     */
    ACCEPT_CALL("0x29"),

    /**
     * Icon Name add waypoint
     */
    ADD_WAYPOINT("0x1B"),

    /**
     * Icon Name album
     */
    ALBUM("0x21"),

    /**
     * Icon Name ambient lighting
     */
    AMBIENT_LIGHTING("0x3d"),

    /**
     * Icon Name arrow - north
     */
    ARROW_NORTH("0x40"),

    /**
     * Icon Name audio mute
     */
    AUDIO_MUTE("0x12"),

    /**
     * Icon Name audiobook episode
     */
    AUDIOBOOK_EPISODE("0x83"),

    /**
     * Icon Name audiobook narrator
     */
    AUDIOBOOK_NARRATOR("0x82"),

    @Deprecated
    AUXILLARY_AUDIO("0x45"),

    /**
     * Icon Name auxiliary audio
     */
    AUXILIARY_AUDIO("0x45"),

    /**
     * Icon Name back / return
     */
    BACK("0x86"),

    /**
     * Icon Name battery capacity 0 of 5
     */
    BATTERY_CAPACITY_0_OF_5("0xF7"),

    /**
     * Icon Name battery capacity 1 of 5
     */
    BATTERY_CAPACITY_1_OF_5("0xF8"),

    /**
     * Icon Name battery capacity 2 of 5
     */
    BATTERY_CAPACITY_2_OF_5("0xF9"),

    /**
     * Icon Name battery capacity 3 of 5
     */
    BATTERY_CAPACITY_3_OF_5("0xFA"),

    /**
     * Icon Name battery capacity 4 of 5
     */
    BATTERY_CAPACITY_4_OF_5("0xf6"),

    /**
     * Icon Name battery capacity 5 of 5
     */
    BATTERY_CAPACITY_5_OF_5("0xFB"),

    /**
     * Icon Name bluetooth audio source
     */
    BLUETOOTH_AUDIO_SOURCE("0x09"),

    /**
     * Icon Name bluetooth1
     */
    BLUETOOTH1("0xcc"),

    /**
     * Icon Name bluetooth2
     */
    BLUETOOTH2("0xCD"),

    /**
     * Icon Name browse
     */
    BROWSE("0x77"),

    /**
     * Icon Name cell phone in roaming mode
     */
    CELL_PHONE_IN_ROAMING_MODE("0x66"),

    /**
     * Icon Name cell service signal strength 0 of 5 bars
     */
    CELL_SERVICE_SIGNAL_STRENGTH_0_OF_5_BARS("0x67"),

    /**
     * Icon Name cell service signal strength 1 of 5 bars
     */
    CELL_SERVICE_SIGNAL_STRENGTH_1_OF_5_BARS("0x68"),

    /**
     * Icon Name cell service signal strength 2 of 5 bars
     */
    CELL_SERVICE_SIGNAL_STRENGTH_2_OF_5_BARS("0x69"),

    /**
     * Icon Name cell service signal strength 3 of 5 bars
     */
    CELL_SERVICE_SIGNAL_STRENGTH_3_OF_5_BARS("0x6A"),

    /**
     * Icon Name cell service signal strength 4 of 5 bars
     */
    CELL_SERVICE_SIGNAL_STRENGTH_4_OF_5_BARS("0x6B"),

    /**
     * Icon Name cell service signal strength 5 of 5 bars
     */
    CELL_SERVICE_SIGNAL_STRENGTH_5_OF_5_BARS("0xd3"),

    /**
     * Icon Name change lane left
     */
    CHANGE_LANE_LEFT("0xc3"),

    /**
     * Icon Name change lane right
     */
    CHANGE_LANE_RIGHT("0xc1"),

    /**
     * Icon Name check box checked
     */
    CHECK_BOX_CHECKED("0x27"),

    /**
     * Icon Name check box unchecked
     */
    CHECK_BOX_UNCHECKED("0x28"),

    /**
     * Icon Name climate
     */
    CLIMATE("0xd1"),

    /**
     * Icon Name clock
     */
    CLOCK("0xfc"),

    /**
     * Icon Name compose (e.g. message)
     */
    COMPOSE("0x1A"),

    /**
     * Icon Name contact / person
     */
    CONTACT("0x5C"),

    /**
     * Icon Name continue
     */
    CONTINUE("0x42"),

    /**
     * Icon Name dash / bullet point
     */
    DASH("0x7F"),

    /**
     * Icon Name date / calendar
     */
    DATE("0x87"),

    /**
     * Icon Name delete/remove - trash
     */
    DELETE("0x0F"),

    /**
     * Icon Name destination
     */
    DESTINATION("0x94"),

    /**
     * Icon Name destination ferry ahead
     */
    DESTINATION_FERRY_AHEAD("0x4D"),

    /**
     * Icon Name ebookmark (e.g. message, feed)
     */
    EBOOKMARK("0x2B"),

    /**
     * Icon Name end call / reject call
     */
    END_CALL("0x2C"),

    /**
     * Icon Name fail / X
     */
    FAIL("0xD6"),

    /**
     * Icon Name fast forward 30 secs
     */
    FAST_FORWARD_30_SECS("0x08"),

    /**
     * Icon Name favorite / heart
     */
    FAVORITE_HEART("0x0E"),

    /**
     * Icon Name favorite / star
     */
    FAVORITE_STAR("0x95"),

    /**
     * Icon Name fax number
     */
    FAX_NUMBER("0x80"),

    /**
     * Icon Name filename
     */
    FILENAME("0x50"),

    /**
     * Icon Name filter / search
     */
    FILTER("0x79"),

    /**
     * Icon Name folder
     */
    FOLDER("0x1C"),

    /**
     * Icon Name fuel prices
     */
    FUEL_PRICES("0xe9"),

    /**
     * Icon Name full map
     */
    FULL_MAP("0x0c"),

    /**
     * Icon Name generic phone number
     */
    GENERIC_PHONE_NUMBER("0x53"),

    /**
     * Icon Name genre
     */
    GENRE("0x4E"),

    /**
     * Icon Name global keyboard
     */
    GLOBAL_KEYBOARD("0xea"),

    /**
     * Icon Name highway exit information
     */
    HIGHWAY_EXIT_INFORMATION("0xf4"),

    /**
     * Icon Name home phone number
     */
    HOME_PHONE_NUMBER("0x55"),

    /**
     * Icon Name hyperlink
     */
    HYPERLINK("0x78"),

    /**
     * Icon Name ID3 tag unknown
     */
    ID3_TAG_UNKNOWN("0x51"),

    /**
     * Icon Name incoming calls (in list of phone calls)
     */
    INCOMING_CALLS("0x57"),

    /**
     * Icon Name information
     */
    INFORMATION("0x5d"),

    /**
     * Icon Name IPOD media source
     */
    IPOD_MEDIA_SOURCE("0x0D"),

    /**
     * Icon Name join calls
     */
    JOIN_CALLS("0x02"),

    /**
     * Icon Name keep left
     */
    KEEP_LEFT("0x46"),

    /**
     * Icon Name keep right
     */
    KEEP_RIGHT("0x48"),

    /**
     * Icon Name key / keycode
     */
    KEY("0x7D"),

    /**
     * Icon Name left
     */
    LEFT("0x9f"),

    /**
     * Icon Name left arrow / back
     */
    LEFT_ARROW("0x4B"),

    /**
     * Icon Name left exit
     */
    LEFT_EXIT("0xaf"),

    /**
     * Icon Name LINE IN audio source
     */
    LINE_IN_AUDIO_SOURCE("0x06"),

    /**
     * Icon Name locked
     */
    LOCKED("0x22"),

    /**
     * Icon Name media control - left arrow
     */
    MEDIA_CONTROL_LEFT_ARROW("0x17"),

    /**
     * Icon Name media control - recording
     */
    MEDIA_CONTROL_RECORDING("0x20"),

    /**
     * Icon Name media control - right arrow
     */
    MEDIA_CONTROL_RIGHT_ARROW("0x15"),

    /**
     * Icon Name media control - stop (e.g. streaming)
     */
    MEDIA_CONTROL_STOP("0x16"),

    /**
     * Icon Name microphone
     */
    MICROPHONE("0xe8"),

    /**
     * Icon Name missed calls (in list of phone calls)
     */
    MISSED_CALLS("0x58"),

    /**
     * Icon Name mobile phone number
     */
    MOBILE_PHONE_NUMBER("0x54"),

    /**
     * Icon Name move down / download
     */
    MOVE_DOWN("0xE5"),

    /**
     * Icon Name move up
     */
    MOVE_UP("0xe4"),

    /**
     * Icon Name MP3 tag artist
     */
    MP3_TAG_ARTIST("0x24"),

    /**
     * Icon Name navigation / navigation settings
     */
    NAVIGATION("0x8e"),

    /**
     * Icon Name navigation current direction
     */
    NAVIGATION_CURRENT_DIRECTION("0x0a"),

    /**
     * Icon Name negative rating - thumbs down
     */
    NEGATIVE_RATING_THUMBS_DOWN("0x14"),

    /**
     * Icon Name new/unread text message/email
     */
    NEW("0x5E"),

    /**
     * Icon Name office phone number / work phone number
     */
    OFFICE_PHONE_NUMBER("0x56"),

    /**
     * Icon Name opened/read text message/email
     */
    OPENED("0x5F"),

    /**
     * Icon Name origin / nearby locale / current position
     */
    ORIGIN("0x96"),

    /**
     * Icon Name outgoing calls (in list of phone calls)
     */
    OUTGOING_CALLS("0x59"),

    /**
     * Icon Name phone call 1
     */
    PHONE_CALL_1("0x1D"),

    /**
     * Icon Name phone call 2
     */
    PHONE_CALL_2("0x1E"),

    /**
     * Icon Name phone device
     */
    PHONE_DEVICE("0x03"),

    /**
     * Icon Name phonebook
     */
    PHONEBOOK("0x81"),

    /**
     * Icon Name photo / picture
     */
    PHOTO("0x88"),

    /**
     * Icon Name play / pause - pause active
     */
    PLAY("0xD0"),

    /**
     * Icon Name playlist
     */
    PLAYLIST("0x4F"),

    /**
     * Icon Name pop-up
     */
    POPUP("0x76"),

    /**
     * Icon Name positive rating - thumbs up
     */
    POSITIVE_RATING_THUMBS_UP("0x13"),

    /**
     * Icon Name power
     */
    POWER("0x5b"),

    /**
     * Icon Name primary phone (favorite)
     */
    PRIMARY_PHONE("0x1F"),

    /**
     * Icon Name radio button checked
     */
    RADIO_BUTTON_CHECKED("0x25"),

    /**
     * Icon Name radio button unchecked
     */
    RADIO_BUTTON_UNCHECKED("0x26"),

    /**
     * Icon Name recent calls / history
     */
    RECENT_CALLS("0xe7"),

    /**
     * Icon Name recent destinations
     */
    RECENT_DESTINATIONS("0xf2"),

    /**
     * Icon Name redo
     */
    REDO("0x19"),

    /**
     * Icon Name refresh
     */
    REFRESH("0x97"),

    /**
     * Icon Name remote diagnostics - check engine
     */
    REMOTE_DIAGNOSTICS_CHECK_ENGINE("0x7E"),

    /**
     * Icon Name rendered 911 assist / emergency assistance
     */
    RENDERED_911_ASSIST("0xac"),

    /**
     * Icon Name repeat
     */
    REPEAT("0xe6"),

    /**
     * Icon Name repeat play
     */
    REPEAT_PLAY("0x73"),

    /**
     * Icon Name reply
     */
    REPLY("0x04"),

    /**
     * Icon Name rewind 30 secs
     */
    REWIND_30_SECS("0x07"),

    /**
     * Icon Name right
     */
    RIGHT("0xa3"),

    /**
     * Icon Name right exit
     */
    RIGHT_EXIT("0xb1"),

    /**
     * Icon Name ringtones
     */
    RINGTONES("0x5A"),

    /**
     * Icon Name roundabout left hand 1
     */
    ROUNDABOUT_LEFT_HAND_1("0xee"),

    /**
     * Icon Name roundabout left hand 2
     */
    ROUNDABOUT_LEFT_HAND_2("0x8c"),

    /**
     * Icon Name roundabout left hand 3
     */
    ROUNDABOUT_LEFT_HAND_3("0x84"),

    /**
     * Icon Name roundabout left hand 4
     */
    ROUNDABOUT_LEFT_HAND_4("0x72"),

    /**
     * Icon Name roundabout left hand 5
     */
    ROUNDABOUT_LEFT_HAND_5("0x6e"),

    /**
     * Icon Name roundabout left hand 6
     */
    ROUNDABOUT_LEFT_HAND_6("0x64"),

    /**
     * Icon Name roundabout left hand 7
     */
    ROUNDABOUT_LEFT_HAND_7("0x60"),

    /**
     * Icon Name roundabout right hand 1
     */
    ROUNDABOUT_RIGHT_HAND_1("0x62"),

    /**
     * Icon Name roundabout right hand 2
     */
    ROUNDABOUT_RIGHT_HAND_2("0x6c"),

    /**
     * Icon Name roundabout right hand 3
     */
    ROUNDABOUT_RIGHT_HAND_3("0x70"),

    /**
     * Icon Name roundabout right hand 4
     */
    ROUNDABOUT_RIGHT_HAND_4("0x7a"),

    /**
     * Icon Name roundabout right hand 5
     */
    ROUNDABOUT_RIGHT_HAND_5("0x8a"),

    /**
     * Icon Name roundabout right hand 6
     */
    ROUNDABOUT_RIGHT_HAND_6("0xec"),

    /**
     * Icon Name roundabout right hand 7
     */
    ROUNDABOUT_RIGHT_HAND_7("0xf0"),

    /**
     * Icon Name RSS
     */
    RSS("0x89"),

    /**
     * Icon Name settings / menu
     */
    SETTINGS("0x49"),

    /**
     * Icon Name sharp left
     */
    SHARP_LEFT("0xa5"),

    /**
     * Icon Name sharp right
     */
    SHARP_RIGHT("0xa7"),

    /**
     * Icon Name show
     */
    SHOW("0xe1"),

    /**
     * Icon Name shuffle play
     */
    SHUFFLE_PLAY("0x74"),

    /**
     * Icon Name ski places / elevation / altitude
     */
    SKI_PLACES("0xab"),

    /**
     * Icon Name slight left
     */
    SLIGHT_LEFT("0x9d"),

    /**
     * Icon Name slight right
     */
    SLIGHT_RIGHT("0xa1"),

    /**
     * Icon Name smartphone
     */
    SMARTPHONE("0x05"),

    /**
     * Icon Name sort list
     */
    SORT_LIST("0x7B"),

    /**
     * Icon Name speed dial numbers - number 0
     */
    SPEED_DIAL_NUMBERS_NUMBER_0("0xE0"),

    /**
     * Icon Name speed dial numbers - number 1
     */
    SPEED_DIAL_NUMBERS_NUMBER_1("0xD7"),

    /**
     * Icon Name speed dial numbers - number 2
     */
    SPEED_DIAL_NUMBERS_NUMBER_2("0xD8"),

    /**
     * Icon Name speed dial numbers - number 3
     */
    SPEED_DIAL_NUMBERS_NUMBER_3("0xD9"),

    /**
     * Icon Name speed dial numbers - number 4
     */
    SPEED_DIAL_NUMBERS_NUMBER_4("0xDA"),

    /**
     * Icon Name speed dial numbers - number 5
     */
    SPEED_DIAL_NUMBERS_NUMBER_5("0xDB"),

    /**
     * Icon Name speed dial numbers - number 6
     */
    SPEED_DIAL_NUMBERS_NUMBER_6("0xDC"),

    /**
     * Icon Name speed dial numbers - number 7
     */
    SPEED_DIAL_NUMBERS_NUMBER_7("0xDD"),

    /**
     * Icon Name speed dial numbers - number 8
     */
    SPEED_DIAL_NUMBERS_NUMBER_8("0xDE"),

    /**
     * Icon Name speed dial numbers - number 9
     */
    SPEED_DIAL_NUMBERS_NUMBER_9("0xDF"),

    /**
     * Icon Name success / check
     */
    SUCCESS("0xD5"),

    /**
     * Icon Name track title / song title
     */
    TRACK_TITLE("0x4C"),

    /**
     * Icon Name traffic report
     */
    TRAFFIC_REPORT("0x2A"),

    /**
     * Icon Name turn list
     */
    TURN_LIST("0x10"),

    /**
     * Icon Name u-turn left traffic
     */
    UTURN_LEFT_TRAFFIC("0xad"),

    /**
     * Icon Name u-turn right traffic
     */
    UTURN_RIGHT_TRAFFIC("0xa9"),

    /**
     * Icon Name undo
     */
    UNDO("0x18"),

    /**
     * Icon Name unlocked
     */
    UNLOCKED("0x23"),

    /**
     * Icon Name USB media audio source
     */
    USB_MEDIA_AUDIO_SOURCE("0x0B"),

    /**
     * Icon Name voice control scrollbar - list item no. 1
     */
    VOICE_CONTROL_SCROLLBAR_LIST_ITEM_NO_1("0xC7"),

    /**
     * Icon Name voice control scrollbar - list item no. 2
     */
    VOICE_CONTROL_SCROLLBAR_LIST_ITEM_NO_2("0xC8"),

    /**
     * Icon Name voice control scrollbar - list item no. 3
     */
    VOICE_CONTROL_SCROLLBAR_LIST_ITEM_NO_3("0xC9"),

    /**
     * Icon Name voice control scrollbar - list item no. 4
     */
    VOICE_CONTROL_SCROLLBAR_LIST_ITEM_NO_4("0xCA"),

    /**
     * Icon Name voice recognition - failed
     */
    VOICE_RECOGNITION_FAILED("0x90"),

    /**
     * Icon Name voice recognition - pause
     */
    VOICE_RECOGNITION_PAUSE("0x92"),

    /**
     * Icon Name voice recognition - successful
     */
    VOICE_RECOGNITION_SUCCESSFUL("0x8F"),

    /**
     * Icon Name voice recognition - system active
     */
    VOICE_RECOGNITION_SYSTEM_ACTIVE("0x11"),

    /**
     * Icon Name voice recognition - system listening
     */
    VOICE_RECOGNITION_SYSTEM_LISTENING("0x91"),

    /**
     * Icon Name voice recognition - try again
     */
    VOICE_RECOGNITION_TRY_AGAIN("0x93"),

    /**
     * Icon Name warning / safety alert
     */
    WARNING("0xfe"),

    /**
     * Icon Name weather
     */
    WEATHER("0xeb"),

    /**
     * Icon Name wifi full
     */
    WIFI_FULL("0x43"),

    /**
     * Icon Name zoom in
     */
    ZOOM_IN("0x98"),

    /**
     * Icon Name zoom out
     */
    ZOOM_OUT("0x9a"),

    ;

    private final String VALUE;

    private StaticIconName(String value) {
        this.VALUE = value;
    }

    public static StaticIconName valueForString(String value) {
        if (value == null) {
            return null;
        }

        for (StaticIconName type : StaticIconName.values()) {
            if (type.toString().equals(value)) {
                return type;
            }
        }

        return null;
    }

    /**
     * Returns the string representation of the hex value associated with this static icon
     *
     * @return string of the hex value representation of this static icon
     */
    @Override
    public String toString() {
        return VALUE;
    }

}
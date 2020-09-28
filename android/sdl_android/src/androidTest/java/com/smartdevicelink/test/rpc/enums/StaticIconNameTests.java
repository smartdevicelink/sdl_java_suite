package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.StaticIconName;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.enums.StaticIconName}
 */
public class StaticIconNameTests extends TestCase {

    public void testValidEnums() {
        String example = "0x29";
        StaticIconName ACCEPT_CALL = StaticIconName.valueForString(example);
        assertNotNull("ACCEPT_CALL returned null", ACCEPT_CALL);

        example = "0x1B";
        StaticIconName ADD_WAYPOINT = StaticIconName.valueForString(example);
        assertNotNull("ADD_WAYPOINT returned null", ADD_WAYPOINT);

        example = "0x21";
        StaticIconName ALBUM = StaticIconName.valueForString(example);
        assertNotNull("ALBUM returned null", ALBUM);

        example = "0x3d";
        StaticIconName AMBIENT_LIGHTING = StaticIconName.valueForString(example);
        assertNotNull("AMBIENT_LIGHTING returned null", AMBIENT_LIGHTING);

        example = "0x40";
        StaticIconName ARROW_NORTH = StaticIconName.valueForString(example);
        assertNotNull("ARROW_NORTH returned null", ARROW_NORTH);

        example = "0x12";
        StaticIconName AUDIO_MUTE = StaticIconName.valueForString(example);
        assertNotNull("AUDIO_MUTE returned null", AUDIO_MUTE);

        example = "0x83";
        StaticIconName AUDIOBOOK_EPISODE = StaticIconName.valueForString(example);
        assertNotNull("AUDIOBOOK_EPISODE returned null", AUDIOBOOK_EPISODE);

        example = "0x82";
        StaticIconName AUDIOBOOK_NARRATOR = StaticIconName.valueForString(example);
        assertNotNull("AUDIOBOOK_NARRATOR returned null", AUDIOBOOK_NARRATOR);

        example = "0x45";
        StaticIconName AUXILLARY_AUDIO = StaticIconName.valueForString(example);
        assertNotNull("AUXILLARY_AUDIO returned null", AUXILLARY_AUDIO);

        example = "0x86";
        StaticIconName BACK = StaticIconName.valueForString(example);
        assertNotNull("BACK returned null", BACK);

        example = "0xF7";
        StaticIconName BATTERY_CAPACITY_0_OF_5 = StaticIconName.valueForString(example);
        assertNotNull("BATTERY_CAPACITY_0_OF_5 returned null", BATTERY_CAPACITY_0_OF_5);

        example = "0xF8";
        StaticIconName BATTERY_CAPACITY_1_OF_5 = StaticIconName.valueForString(example);
        assertNotNull("BATTERY_CAPACITY_1_OF_5 returned null", BATTERY_CAPACITY_1_OF_5);

        example = "0xF9";
        StaticIconName BATTERY_CAPACITY_2_OF_5 = StaticIconName.valueForString(example);
        assertNotNull("BATTERY_CAPACITY_2_OF_5 returned null", BATTERY_CAPACITY_2_OF_5);

        example = "0xFA";
        StaticIconName BATTERY_CAPACITY_3_OF_5 = StaticIconName.valueForString(example);
        assertNotNull("BATTERY_CAPACITY_3_OF_5 returned null", BATTERY_CAPACITY_3_OF_5);

        example = "0xf6";
        StaticIconName BATTERY_CAPACITY_4_OF_5 = StaticIconName.valueForString(example);
        assertNotNull("BATTERY_CAPACITY_4_OF_5 returned null", BATTERY_CAPACITY_4_OF_5);

        example = "0xFB";
        StaticIconName BATTERY_CAPACITY_5_OF_5 = StaticIconName.valueForString(example);
        assertNotNull("BATTERY_CAPACITY_5_OF_5 returned null", BATTERY_CAPACITY_5_OF_5);

        example = "0x09";
        StaticIconName BLUETOOTH_AUDIO_SOURCE = StaticIconName.valueForString(example);
        assertNotNull("BLUETOOTH_AUDIO_SOURCE returned null", BLUETOOTH_AUDIO_SOURCE);

        example = "0xcc";
        StaticIconName BLUETOOTH1 = StaticIconName.valueForString(example);
        assertNotNull("BLUETOOTH1 returned null", BLUETOOTH1);

        example = "0xCD";
        StaticIconName BLUETOOTH2 = StaticIconName.valueForString(example);
        assertNotNull("BLUETOOTH2 returned null", BLUETOOTH2);

        example = "0x77";
        StaticIconName BROWSE = StaticIconName.valueForString(example);
        assertNotNull("BROWSE returned null", BROWSE);

        example = "0x66";
        StaticIconName CELL_PHONE_IN_ROAMING_MODE = StaticIconName.valueForString(example);
        assertNotNull("CELL_PHONE_IN_ROAMING_MODE returned null", CELL_PHONE_IN_ROAMING_MODE);

        example = "0x67";
        StaticIconName CELL_SERVICE_SIGNAL_STRENGTH_0_OF_5_BARS = StaticIconName.valueForString(example);
        assertNotNull("CELL_SERVICE_SIGNAL_STRENGTH_0_OF_5_BARS returned null", CELL_SERVICE_SIGNAL_STRENGTH_0_OF_5_BARS);

        example = "0x68";
        StaticIconName CELL_SERVICE_SIGNAL_STRENGTH_1_OF_5_BARS = StaticIconName.valueForString(example);
        assertNotNull("CELL_SERVICE_SIGNAL_STRENGTH_1_OF_5_BARS returned null", CELL_SERVICE_SIGNAL_STRENGTH_1_OF_5_BARS);

        example = "0x69";
        StaticIconName CELL_SERVICE_SIGNAL_STRENGTH_2_OF_5_BARS = StaticIconName.valueForString(example);
        assertNotNull("CELL_SERVICE_SIGNAL_STRENGTH_2_OF_5_BARS returned null", CELL_SERVICE_SIGNAL_STRENGTH_2_OF_5_BARS);

        example = "0x6A";
        StaticIconName CELL_SERVICE_SIGNAL_STRENGTH_3_OF_5_BARS = StaticIconName.valueForString(example);
        assertNotNull("CELL_SERVICE_SIGNAL_STRENGTH_3_OF_5_BARS returned null", CELL_SERVICE_SIGNAL_STRENGTH_3_OF_5_BARS);

        example = "0x6B";
        StaticIconName CELL_SERVICE_SIGNAL_STRENGTH_4_OF_5_BARS = StaticIconName.valueForString(example);
        assertNotNull("CELL_SERVICE_SIGNAL_STRENGTH_4_OF_5_BARS returned null", CELL_SERVICE_SIGNAL_STRENGTH_4_OF_5_BARS);

        example = "0xd3";
        StaticIconName CELL_SERVICE_SIGNAL_STRENGTH_5_OF_5_BARS = StaticIconName.valueForString(example);
        assertNotNull("CELL_SERVICE_SIGNAL_STRENGTH_5_OF_5_BARS returned null", CELL_SERVICE_SIGNAL_STRENGTH_5_OF_5_BARS);

        example = "0xc3";
        StaticIconName CHANGE_LANE_LEFT = StaticIconName.valueForString(example);
        assertNotNull("CHANGE_LANE_LEFT returned null", CHANGE_LANE_LEFT);

        example = "0xc1";
        StaticIconName CHANGE_LANE_RIGHT = StaticIconName.valueForString(example);
        assertNotNull("CHANGE_LANE_RIGHT returned null", CHANGE_LANE_RIGHT);

        example = "0x27";
        StaticIconName CHECK_BOX_CHECKED = StaticIconName.valueForString(example);
        assertNotNull("CHECK_BOX_CHECKED returned null", CHECK_BOX_CHECKED);

        example = "0x28";
        StaticIconName CHECK_BOX_UNCHECKED = StaticIconName.valueForString(example);
        assertNotNull("CHECK_BOX_UNCHECKED returned null", CHECK_BOX_UNCHECKED);

        example = "0xd1";
        StaticIconName CLIMATE = StaticIconName.valueForString(example);
        assertNotNull("CLIMATE returned null", CLIMATE);

        example = "0xfc";
        StaticIconName CLOCK = StaticIconName.valueForString(example);
        assertNotNull("CLOCK returned null", CLOCK);

        example = "0x1A";
        StaticIconName COMPOSE = StaticIconName.valueForString(example);
        assertNotNull("COMPOSE returned null", COMPOSE);

        example = "0x5C";
        StaticIconName CONTACT = StaticIconName.valueForString(example);
        assertNotNull("CONTACT returned null", CONTACT);

        example = "0x42";
        StaticIconName CONTINUE = StaticIconName.valueForString(example);
        assertNotNull("CONTINUE returned null", CONTINUE);

        example = "0x7F";
        StaticIconName DASH = StaticIconName.valueForString(example);
        assertNotNull("DASH returned null", DASH);

        example = "0x87";
        StaticIconName DATE = StaticIconName.valueForString(example);
        assertNotNull("DATE returned null", DATE);

        example = "0x0F";
        StaticIconName DELETE = StaticIconName.valueForString(example);
        assertNotNull("DELETE returned null", DELETE);

        example = "0x94";
        StaticIconName DESTINATION = StaticIconName.valueForString(example);
        assertNotNull("DESTINATION returned null", DESTINATION);

        example = "0x4D";
        StaticIconName DESTINATION_FERRY_AHEAD = StaticIconName.valueForString(example);
        assertNotNull("DESTINATION_FERRY_AHEAD returned null", DESTINATION_FERRY_AHEAD);

        example = "0x2B";
        StaticIconName EBOOKMARK = StaticIconName.valueForString(example);
        assertNotNull("EBOOKMARK returned null", EBOOKMARK);

        example = "0x2C";
        StaticIconName END_CALL = StaticIconName.valueForString(example);
        assertNotNull("END_CALL returned null", END_CALL);

        example = "0xD6";
        StaticIconName FAIL = StaticIconName.valueForString(example);
        assertNotNull("FAIL returned null", FAIL);

        example = "0x08";
        StaticIconName FAST_FORWARD_30_SECS = StaticIconName.valueForString(example);
        assertNotNull("FAST_FORWARD_30_SECS returned null", FAST_FORWARD_30_SECS);

        example = "0x0E";
        StaticIconName FAVORITE_HEART = StaticIconName.valueForString(example);
        assertNotNull("FAVORITE_HEART returned null", FAVORITE_HEART);

        example = "0x95";
        StaticIconName FAVORITE_STAR = StaticIconName.valueForString(example);
        assertNotNull("FAVORITE_STAR returned null", FAVORITE_STAR);

        example = "0x80";
        StaticIconName FAX_NUMBER = StaticIconName.valueForString(example);
        assertNotNull("FAX_NUMBER returned null", FAX_NUMBER);

        example = "0x50";
        StaticIconName FILENAME = StaticIconName.valueForString(example);
        assertNotNull("FILENAME returned null", FILENAME);

        example = "0x79";
        StaticIconName FILTER = StaticIconName.valueForString(example);
        assertNotNull("FILTER returned null", FILTER);

        example = "0x1C";
        StaticIconName FOLDER = StaticIconName.valueForString(example);
        assertNotNull("FOLDER returned null", FOLDER);

        example = "0xe9";
        StaticIconName FUEL_PRICES = StaticIconName.valueForString(example);
        assertNotNull("FUEL_PRICES returned null", FUEL_PRICES);

        example = "0x0c";
        StaticIconName FULL_MAP = StaticIconName.valueForString(example);
        assertNotNull("FULL_MAP returned null", FULL_MAP);

        example = "0x53";
        StaticIconName GENERIC_PHONE_NUMBER = StaticIconName.valueForString(example);
        assertNotNull("GENERIC_PHONE_NUMBER returned null", GENERIC_PHONE_NUMBER);

        example = "0x4E";
        StaticIconName GENRE = StaticIconName.valueForString(example);
        assertNotNull("GENRE returned null", GENRE);

        example = "0xea";
        StaticIconName GLOBAL_KEYBOARD = StaticIconName.valueForString(example);
        assertNotNull("GLOBAL_KEYBOARD returned null", GLOBAL_KEYBOARD);

        example = "0xf4";
        StaticIconName HIGHWAY_EXIT_INFORMATION = StaticIconName.valueForString(example);
        assertNotNull("HIGHWAY_EXIT_INFORMATION returned null", HIGHWAY_EXIT_INFORMATION);

        example = "0x55";
        StaticIconName HOME_PHONE_NUMBER = StaticIconName.valueForString(example);
        assertNotNull("HOME_PHONE_NUMBER returned null", HOME_PHONE_NUMBER);

        example = "0x78";
        StaticIconName HYPERLINK = StaticIconName.valueForString(example);
        assertNotNull("HYPERLINK returned null", HYPERLINK);

        example = "0x51";
        StaticIconName ID3_TAG_UNKNOWN = StaticIconName.valueForString(example);
        assertNotNull("ID3_TAG_UNKNOWN returned null", ID3_TAG_UNKNOWN);

        example = "0x57";
        StaticIconName INCOMING_CALLS = StaticIconName.valueForString(example);
        assertNotNull("INCOMING_CALLS returned null", INCOMING_CALLS);

        example = "0x5d";
        StaticIconName INFORMATION = StaticIconName.valueForString(example);
        assertNotNull("INFORMATION returned null", INFORMATION);

        example = "0x0D";
        StaticIconName IPOD_MEDIA_SOURCE = StaticIconName.valueForString(example);
        assertNotNull("IPOD_MEDIA_SOURCE returned null", IPOD_MEDIA_SOURCE);

        example = "0x02";
        StaticIconName JOIN_CALLS = StaticIconName.valueForString(example);
        assertNotNull("JOIN_CALLS returned null", JOIN_CALLS);

        example = "0x46";
        StaticIconName KEEP_LEFT = StaticIconName.valueForString(example);
        assertNotNull("KEEP_LEFT returned null", KEEP_LEFT);

        example = "0x48";
        StaticIconName KEEP_RIGHT = StaticIconName.valueForString(example);
        assertNotNull("KEEP_RIGHT returned null", KEEP_RIGHT);

        example = "0x7D";
        StaticIconName KEY = StaticIconName.valueForString(example);
        assertNotNull("KEY returned null", KEY);

        example = "0x9f";
        StaticIconName LEFT = StaticIconName.valueForString(example);
        assertNotNull("LEFT returned null", LEFT);

        example = "0x4B";
        StaticIconName LEFT_ARROW = StaticIconName.valueForString(example);
        assertNotNull("LEFT_ARROW returned null", LEFT_ARROW);

        example = "0xaf";
        StaticIconName LEFT_EXIT = StaticIconName.valueForString(example);
        assertNotNull("LEFT_EXIT returned null", LEFT_EXIT);

        example = "0x06";
        StaticIconName LINE_IN_AUDIO_SOURCE = StaticIconName.valueForString(example);
        assertNotNull("LINE_IN_AUDIO_SOURCE returned null", LINE_IN_AUDIO_SOURCE);

        example = "0x22";
        StaticIconName LOCKED = StaticIconName.valueForString(example);
        assertNotNull("LOCKED returned null", LOCKED);

        example = "0x17";
        StaticIconName MEDIA_CONTROL_LEFT_ARROW = StaticIconName.valueForString(example);
        assertNotNull("MEDIA_CONTROL_LEFT_ARROW returned null", MEDIA_CONTROL_LEFT_ARROW);

        example = "0x20";
        StaticIconName MEDIA_CONTROL_RECORDING = StaticIconName.valueForString(example);
        assertNotNull("MEDIA_CONTROL_RECORDING returned null", MEDIA_CONTROL_RECORDING);

        example = "0x15";
        StaticIconName MEDIA_CONTROL_RIGHT_ARROW = StaticIconName.valueForString(example);
        assertNotNull("MEDIA_CONTROL_RIGHT_ARROW returned null", MEDIA_CONTROL_RIGHT_ARROW);

        example = "0x16";
        StaticIconName MEDIA_CONTROL_STOP = StaticIconName.valueForString(example);
        assertNotNull("MEDIA_CONTROL_STOP returned null", MEDIA_CONTROL_STOP);

        example = "0xe8";
        StaticIconName MICROPHONE = StaticIconName.valueForString(example);
        assertNotNull("MICROPHONE returned null", MICROPHONE);

        example = "0x58";
        StaticIconName MISSED_CALLS = StaticIconName.valueForString(example);
        assertNotNull("MISSED_CALLS returned null", MISSED_CALLS);

        example = "0x54";
        StaticIconName MOBILE_PHONE_NUMBER = StaticIconName.valueForString(example);
        assertNotNull("MOBILE_PHONE_NUMBER returned null", MOBILE_PHONE_NUMBER);

        example = "0xE5";
        StaticIconName MOVE_DOWN = StaticIconName.valueForString(example);
        assertNotNull("MOVE_DOWN returned null", MOVE_DOWN);

        example = "0xe4";
        StaticIconName MOVE_UP = StaticIconName.valueForString(example);
        assertNotNull("MOVE_UP returned null", MOVE_UP);

        example = "0x24";
        StaticIconName MP3_TAG_ARTIST = StaticIconName.valueForString(example);
        assertNotNull("MP3_TAG_ARTIST returned null", MP3_TAG_ARTIST);

        example = "0x8e";
        StaticIconName NAVIGATION = StaticIconName.valueForString(example);
        assertNotNull("NAVIGATION returned null", NAVIGATION);

        example = "0x0a";
        StaticIconName NAVIGATION_CURRENT_DIRECTION = StaticIconName.valueForString(example);
        assertNotNull("NAVIGATION_CURRENT_DIRECTION returned null", NAVIGATION_CURRENT_DIRECTION);

        example = "0x14";
        StaticIconName NEGATIVE_RATING_THUMBS_DOWN = StaticIconName.valueForString(example);
        assertNotNull("NEGATIVE_RATING_THUMBS_DOWN returned null", NEGATIVE_RATING_THUMBS_DOWN);

        example = "0x5E";
        StaticIconName NEW = StaticIconName.valueForString(example);
        assertNotNull("NEW returned null", NEW);

        example = "0x56";
        StaticIconName OFFICE_PHONE_NUMBER = StaticIconName.valueForString(example);
        assertNotNull("OFFICE_PHONE_NUMBER returned null", OFFICE_PHONE_NUMBER);

        example = "0x5F";
        StaticIconName OPENED = StaticIconName.valueForString(example);
        assertNotNull("OPENED returned null", OPENED);

        example = "0x96";
        StaticIconName ORIGIN = StaticIconName.valueForString(example);
        assertNotNull("ORIGIN returned null", ORIGIN);

        example = "0x59";
        StaticIconName OUTGOING_CALLS = StaticIconName.valueForString(example);
        assertNotNull("OUTGOING_CALLS returned null", OUTGOING_CALLS);

        example = "0x1D";
        StaticIconName PHONE_CALL_1 = StaticIconName.valueForString(example);
        assertNotNull("PHONE_CALL_1 returned null", PHONE_CALL_1);

        example = "0x1E";
        StaticIconName PHONE_CALL_2 = StaticIconName.valueForString(example);
        assertNotNull("PHONE_CALL_2 returned null", PHONE_CALL_2);

        example = "0x03";
        StaticIconName PHONE_DEVICE = StaticIconName.valueForString(example);
        assertNotNull("PHONE_DEVICE returned null", PHONE_DEVICE);

        example = "0x81";
        StaticIconName PHONEBOOK = StaticIconName.valueForString(example);
        assertNotNull("PHONEBOOK returned null", PHONEBOOK);

        example = "0x88";
        StaticIconName PHOTO = StaticIconName.valueForString(example);
        assertNotNull("PHOTO returned null", PHOTO);

        example = "0xD0";
        StaticIconName PLAY = StaticIconName.valueForString(example);
        assertNotNull("PLAY returned null", PLAY);

        example = "0x4F";
        StaticIconName PLAYLIST = StaticIconName.valueForString(example);
        assertNotNull("PLAYLIST returned null", PLAYLIST);

        example = "0x76";
        StaticIconName POPUP = StaticIconName.valueForString(example);
        assertNotNull("POPUP returned null", POPUP);

        example = "0x13";
        StaticIconName POSITIVE_RATING_THUMBS_UP = StaticIconName.valueForString(example);
        assertNotNull("POSITIVE_RATING_THUMBS_UP returned null", POSITIVE_RATING_THUMBS_UP);

        example = "0x5b";
        StaticIconName POWER = StaticIconName.valueForString(example);
        assertNotNull("POWER returned null", POWER);

        example = "0x1F";
        StaticIconName PRIMARY_PHONE = StaticIconName.valueForString(example);
        assertNotNull("PRIMARY_PHONE returned null", PRIMARY_PHONE);

        example = "0x25";
        StaticIconName RADIO_BUTTON_CHECKED = StaticIconName.valueForString(example);
        assertNotNull("RADIO_BUTTON_CHECKED returned null", RADIO_BUTTON_CHECKED);

        example = "0x26";
        StaticIconName RADIO_BUTTON_UNCHECKED = StaticIconName.valueForString(example);
        assertNotNull("RADIO_BUTTON_UNCHECKED returned null", RADIO_BUTTON_UNCHECKED);

        example = "0xe7";
        StaticIconName RECENT_CALLS = StaticIconName.valueForString(example);
        assertNotNull("RECENT_CALLS returned null", RECENT_CALLS);

        example = "0xf2";
        StaticIconName RECENT_DESTINATIONS = StaticIconName.valueForString(example);
        assertNotNull("RECENT_DESTINATIONS returned null", RECENT_DESTINATIONS);

        example = "0x19";
        StaticIconName REDO = StaticIconName.valueForString(example);
        assertNotNull("REDO returned null", REDO);

        example = "0x97";
        StaticIconName REFRESH = StaticIconName.valueForString(example);
        assertNotNull("REFRESH returned null", REFRESH);

        example = "0x7E";
        StaticIconName REMOTE_DIAGNOSTICS_CHECK_ENGINE = StaticIconName.valueForString(example);
        assertNotNull("REMOTE_DIAGNOSTICS_CHECK_ENGINE returned null", REMOTE_DIAGNOSTICS_CHECK_ENGINE);

        example = "0xac";
        StaticIconName RENDERED_911_ASSIST = StaticIconName.valueForString(example);
        assertNotNull("RENDERED_911_ASSIST returned null", RENDERED_911_ASSIST);

        example = "0xe6";
        StaticIconName REPEAT = StaticIconName.valueForString(example);
        assertNotNull("REPEAT returned null", REPEAT);

        example = "0x73";
        StaticIconName REPEAT_PLAY = StaticIconName.valueForString(example);
        assertNotNull("REPEAT_PLAY returned null", REPEAT_PLAY);

        example = "0x04";
        StaticIconName REPLY = StaticIconName.valueForString(example);
        assertNotNull("REPLY returned null", REPLY);

        example = "0x07";
        StaticIconName REWIND_30_SECS = StaticIconName.valueForString(example);
        assertNotNull("REWIND_30_SECS returned null", REWIND_30_SECS);

        example = "0xa3";
        StaticIconName RIGHT = StaticIconName.valueForString(example);
        assertNotNull("RIGHT returned null", RIGHT);

        example = "0xb1";
        StaticIconName RIGHT_EXIT = StaticIconName.valueForString(example);
        assertNotNull("RIGHT_EXIT returned null", RIGHT_EXIT);

        example = "0x5A";
        StaticIconName RINGTONES = StaticIconName.valueForString(example);
        assertNotNull("RINGTONES returned null", RINGTONES);

        example = "0xee";
        StaticIconName ROUNDABOUT_LEFT_HAND_1 = StaticIconName.valueForString(example);
        assertNotNull("ROUNDABOUT_LEFT_HAND_1 returned null", ROUNDABOUT_LEFT_HAND_1);

        example = "0x8c";
        StaticIconName ROUNDABOUT_LEFT_HAND_2 = StaticIconName.valueForString(example);
        assertNotNull("ROUNDABOUT_LEFT_HAND_2 returned null", ROUNDABOUT_LEFT_HAND_2);

        example = "0x84";
        StaticIconName ROUNDABOUT_LEFT_HAND_3 = StaticIconName.valueForString(example);
        assertNotNull("ROUNDABOUT_LEFT_HAND_3 returned null", ROUNDABOUT_LEFT_HAND_3);

        example = "0x72";
        StaticIconName ROUNDABOUT_LEFT_HAND_4 = StaticIconName.valueForString(example);
        assertNotNull("ROUNDABOUT_LEFT_HAND_4 returned null", ROUNDABOUT_LEFT_HAND_4);

        example = "0x6e";
        StaticIconName ROUNDABOUT_LEFT_HAND_5 = StaticIconName.valueForString(example);
        assertNotNull("ROUNDABOUT_LEFT_HAND_5 returned null", ROUNDABOUT_LEFT_HAND_5);

        example = "0x64";
        StaticIconName ROUNDABOUT_LEFT_HAND_6 = StaticIconName.valueForString(example);
        assertNotNull("ROUNDABOUT_LEFT_HAND_6 returned null", ROUNDABOUT_LEFT_HAND_6);

        example = "0x60";
        StaticIconName ROUNDABOUT_LEFT_HAND_7 = StaticIconName.valueForString(example);
        assertNotNull("ROUNDABOUT_LEFT_HAND_7 returned null", ROUNDABOUT_LEFT_HAND_7);

        example = "0x62";
        StaticIconName ROUNDABOUT_RIGHT_HAND_1 = StaticIconName.valueForString(example);
        assertNotNull("ROUNDABOUT_RIGHT_HAND_1 returned null", ROUNDABOUT_RIGHT_HAND_1);

        example = "0x6c";
        StaticIconName ROUNDABOUT_RIGHT_HAND_2 = StaticIconName.valueForString(example);
        assertNotNull("ROUNDABOUT_RIGHT_HAND_2 returned null", ROUNDABOUT_RIGHT_HAND_2);

        example = "0x70";
        StaticIconName ROUNDABOUT_RIGHT_HAND_3 = StaticIconName.valueForString(example);
        assertNotNull("ROUNDABOUT_RIGHT_HAND_3 returned null", ROUNDABOUT_RIGHT_HAND_3);

        example = "0x7a";
        StaticIconName ROUNDABOUT_RIGHT_HAND_4 = StaticIconName.valueForString(example);
        assertNotNull("ROUNDABOUT_RIGHT_HAND_4 returned null", ROUNDABOUT_RIGHT_HAND_4);

        example = "0x8a";
        StaticIconName ROUNDABOUT_RIGHT_HAND_5 = StaticIconName.valueForString(example);
        assertNotNull("ROUNDABOUT_RIGHT_HAND_5 returned null", ROUNDABOUT_RIGHT_HAND_5);

        example = "0xec";
        StaticIconName ROUNDABOUT_RIGHT_HAND_6 = StaticIconName.valueForString(example);
        assertNotNull("ROUNDABOUT_RIGHT_HAND_6 returned null", ROUNDABOUT_RIGHT_HAND_6);

        example = "0xf0";
        StaticIconName ROUNDABOUT_RIGHT_HAND_7 = StaticIconName.valueForString(example);
        assertNotNull("ROUNDABOUT_RIGHT_HAND_7 returned null", ROUNDABOUT_RIGHT_HAND_7);

        example = "0x89";
        StaticIconName RSS = StaticIconName.valueForString(example);
        assertNotNull("RSS returned null", RSS);

        example = "0x49";
        StaticIconName SETTINGS = StaticIconName.valueForString(example);
        assertNotNull("SETTINGS returned null", SETTINGS);

        example = "0xa5";
        StaticIconName SHARP_LEFT = StaticIconName.valueForString(example);
        assertNotNull("SHARP_LEFT returned null", SHARP_LEFT);

        example = "0xe1";
        StaticIconName SHOW = StaticIconName.valueForString(example);
        assertNotNull("SHOW returned null", SHOW);

        example = "0x74";
        StaticIconName SHUFFLE_PLAY = StaticIconName.valueForString(example);
        assertNotNull("SHUFFLE_PLAY returned null", SHUFFLE_PLAY);

        example = "0xab";
        StaticIconName SKI_PLACES = StaticIconName.valueForString(example);
        assertNotNull("SKI_PLACES returned null", SKI_PLACES);

        example = "0x9d";
        StaticIconName SLIGHT_LEFT = StaticIconName.valueForString(example);
        assertNotNull("SLIGHT_LEFT returned null", SLIGHT_LEFT);

        example = "0xa1";
        StaticIconName SLIGHT_RIGHT = StaticIconName.valueForString(example);
        assertNotNull("SLIGHT_RIGHT returned null", SLIGHT_RIGHT);

        example = "0x05";
        StaticIconName SMARTPHONE = StaticIconName.valueForString(example);
        assertNotNull("SMARTPHONE returned null", SMARTPHONE);

        example = "0x7B";
        StaticIconName SORT_LIST = StaticIconName.valueForString(example);
        assertNotNull("SORT_LIST returned null", SORT_LIST);

        example = "0xE0";
        StaticIconName SPEED_DIAL_NUMBERS_NUMBER_0 = StaticIconName.valueForString(example);
        assertNotNull("SPEED_DIAL_NUMBERS_NUMBER_0 returned null", SPEED_DIAL_NUMBERS_NUMBER_0);

        example = "0xD7";
        StaticIconName SPEED_DIAL_NUMBERS_NUMBER_1 = StaticIconName.valueForString(example);
        assertNotNull("SPEED_DIAL_NUMBERS_NUMBER_1 returned null", SPEED_DIAL_NUMBERS_NUMBER_1);

        example = "0xD8";
        StaticIconName SPEED_DIAL_NUMBERS_NUMBER_2 = StaticIconName.valueForString(example);
        assertNotNull("SPEED_DIAL_NUMBERS_NUMBER_2 returned null", SPEED_DIAL_NUMBERS_NUMBER_2);

        example = "0xD9";
        StaticIconName SPEED_DIAL_NUMBERS_NUMBER_3 = StaticIconName.valueForString(example);
        assertNotNull("SPEED_DIAL_NUMBERS_NUMBER_3 returned null", SPEED_DIAL_NUMBERS_NUMBER_3);

        example = "0xDA";
        StaticIconName SPEED_DIAL_NUMBERS_NUMBER_4 = StaticIconName.valueForString(example);
        assertNotNull("SPEED_DIAL_NUMBERS_NUMBER_4 returned null", SPEED_DIAL_NUMBERS_NUMBER_4);

        example = "0xDB";
        StaticIconName SPEED_DIAL_NUMBERS_NUMBER_5 = StaticIconName.valueForString(example);
        assertNotNull("SPEED_DIAL_NUMBERS_NUMBER_5 returned null", SPEED_DIAL_NUMBERS_NUMBER_5);

        example = "0xDC";
        StaticIconName SPEED_DIAL_NUMBERS_NUMBER_6 = StaticIconName.valueForString(example);
        assertNotNull("SPEED_DIAL_NUMBERS_NUMBER_6 returned null", SPEED_DIAL_NUMBERS_NUMBER_6);

        example = "0xDD";
        StaticIconName SPEED_DIAL_NUMBERS_NUMBER_7 = StaticIconName.valueForString(example);
        assertNotNull("SPEED_DIAL_NUMBERS_NUMBER_7 returned null", SPEED_DIAL_NUMBERS_NUMBER_7);

        example = "0xDE";
        StaticIconName SPEED_DIAL_NUMBERS_NUMBER_8 = StaticIconName.valueForString(example);
        assertNotNull("SPEED_DIAL_NUMBERS_NUMBER_8 returned null", SPEED_DIAL_NUMBERS_NUMBER_8);

        example = "0xDF";
        StaticIconName SPEED_DIAL_NUMBERS_NUMBER_9 = StaticIconName.valueForString(example);
        assertNotNull("SPEED_DIAL_NUMBERS_NUMBER_9 returned null", SPEED_DIAL_NUMBERS_NUMBER_9);

        example = "0xD5";
        StaticIconName SUCCESS = StaticIconName.valueForString(example);
        assertNotNull("SUCCESS returned null", SUCCESS);

        example = "0x4C";
        StaticIconName TRACK_TITLE = StaticIconName.valueForString(example);
        assertNotNull("TRACK_TITLE returned null", TRACK_TITLE);

        example = "0x2A";
        StaticIconName TRAFFIC_REPORT = StaticIconName.valueForString(example);
        assertNotNull("TRAFFIC_REPORT returned null", TRAFFIC_REPORT);

        example = "0x10";
        StaticIconName TURN_LIST = StaticIconName.valueForString(example);
        assertNotNull("TURN_LIST returned null", TURN_LIST);

        example = "0xad";
        StaticIconName UTURN_LEFT_TRAFFIC = StaticIconName.valueForString(example);
        assertNotNull("UTURN_LEFT_TRAFFIC returned null", UTURN_LEFT_TRAFFIC);

        example = "0xa9";
        StaticIconName UTURN_RIGHT_TRAFFIC = StaticIconName.valueForString(example);
        assertNotNull("UTURN_RIGHT_TRAFFIC returned null", UTURN_RIGHT_TRAFFIC);

        example = "0x18";
        StaticIconName UNDO = StaticIconName.valueForString(example);
        assertNotNull("UNDO returned null", UNDO);

        example = "0x23";
        StaticIconName UNLOCKED = StaticIconName.valueForString(example);
        assertNotNull("UNLOCKED returned null", UNLOCKED);

        example = "0x0B";
        StaticIconName USB_MEDIA_AUDIO_SOURCE = StaticIconName.valueForString(example);
        assertNotNull("USB_MEDIA_AUDIO_SOURCE returned null", USB_MEDIA_AUDIO_SOURCE);

        example = "0xC7";
        StaticIconName VOICE_CONTROL_SCROLLBAR_LIST_ITEM_NO_1 = StaticIconName.valueForString(example);
        assertNotNull("VOICE_CONTROL_SCROLLBAR_LIST_ITEM_NO_1 returned null", VOICE_CONTROL_SCROLLBAR_LIST_ITEM_NO_1);

        example = "0xC8";
        StaticIconName VOICE_CONTROL_SCROLLBAR_LIST_ITEM_NO_2 = StaticIconName.valueForString(example);
        assertNotNull("VOICE_CONTROL_SCROLLBAR_LIST_ITEM_NO_2 returned null", VOICE_CONTROL_SCROLLBAR_LIST_ITEM_NO_2);

        example = "0xC9";
        StaticIconName VOICE_CONTROL_SCROLLBAR_LIST_ITEM_NO_3 = StaticIconName.valueForString(example);
        assertNotNull("VOICE_CONTROL_SCROLLBAR_LIST_ITEM_NO_3 returned null", VOICE_CONTROL_SCROLLBAR_LIST_ITEM_NO_3);

        example = "0xCA";
        StaticIconName VOICE_CONTROL_SCROLLBAR_LIST_ITEM_NO_4 = StaticIconName.valueForString(example);
        assertNotNull("VOICE_CONTROL_SCROLLBAR_LIST_ITEM_NO_4 returned null", VOICE_CONTROL_SCROLLBAR_LIST_ITEM_NO_4);

        example = "0x90";
        StaticIconName VOICE_RECOGNITION_FAILED = StaticIconName.valueForString(example);
        assertNotNull("VOICE_RECOGNITION_FAILED returned null", VOICE_RECOGNITION_FAILED);

        example = "0x92";
        StaticIconName VOICE_RECOGNITION_PAUSE = StaticIconName.valueForString(example);
        assertNotNull("VOICE_RECOGNITION_PAUSE returned null", VOICE_RECOGNITION_PAUSE);

        example = "0x8F";
        StaticIconName VOICE_RECOGNITION_SUCCESSFUL = StaticIconName.valueForString(example);
        assertNotNull("VOICE_RECOGNITION_SUCCESSFUL returned null", VOICE_RECOGNITION_SUCCESSFUL);

        example = "0x11";
        StaticIconName VOICE_RECOGNITION_SYSTEM_ACTIVE = StaticIconName.valueForString(example);
        assertNotNull("VOICE_RECOGNITION_SYSTEM_ACTIVE returned null", VOICE_RECOGNITION_SYSTEM_ACTIVE);

        example = "0x91";
        StaticIconName VOICE_RECOGNITION_SYSTEM_LISTENING = StaticIconName.valueForString(example);
        assertNotNull("VOICE_RECOGNITION_SYSTEM_LISTENING returned null", VOICE_RECOGNITION_SYSTEM_LISTENING);

        example = "0x93";
        StaticIconName VOICE_RECOGNITION_TRY_AGAIN = StaticIconName.valueForString(example);
        assertNotNull("VOICE_RECOGNITION_TRY_AGAIN returned null", VOICE_RECOGNITION_TRY_AGAIN);

        example = "0xfe";
        StaticIconName WARNING = StaticIconName.valueForString(example);
        assertNotNull("WARNING returned null", WARNING);

        example = "0xeb";
        StaticIconName WEATHER = StaticIconName.valueForString(example);
        assertNotNull("WEATHER returned null", WEATHER);

        example = "0x43";
        StaticIconName WIFI_FULL = StaticIconName.valueForString(example);
        assertNotNull("WIFI_FULL returned null", WIFI_FULL);

        example = "0x98";
        StaticIconName ZOOM_IN = StaticIconName.valueForString(example);
        assertNotNull("ZOOM_IN returned null", ZOOM_IN);

        example = "0x9a";
        StaticIconName ZOOM_OUT = StaticIconName.valueForString(example);
        assertNotNull("ZOOM_OUT returned null", ZOOM_OUT);
    }

    /**
     * Verifies that an invalid assignment is null.
     */
    public void testInvalidEnum() {
        String example = "SoMeThInG";
        try {
            StaticIconName SOMETHING = StaticIconName.valueForString(example);
            assertNull("Result of valueForString should be null.", SOMETHING);
        } catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
        }
    }

    /**
     * Verifies that a null assignment is invalid.
     */
    public void testNullEnum() {
        String example = null;
        try {
            StaticIconName temp = StaticIconName.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
        } catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
        }
    }

    /**
     * Verifies the possible enum values of StaticIconNames.
     */
    public void testListEnum() {

        List<StaticIconName> enumValueList = Arrays.asList(StaticIconName.values());

        List<StaticIconName> enumTestList = new ArrayList<>();
        enumTestList.add(StaticIconName.ACCEPT_CALL);
        enumTestList.add(StaticIconName.ADD_WAYPOINT);
        enumTestList.add(StaticIconName.ALBUM);
        enumTestList.add(StaticIconName.AMBIENT_LIGHTING);
        enumTestList.add(StaticIconName.ARROW_NORTH);
        enumTestList.add(StaticIconName.AUDIO_MUTE);
        enumTestList.add(StaticIconName.AUDIOBOOK_EPISODE);
        enumTestList.add(StaticIconName.AUDIOBOOK_NARRATOR);
        enumTestList.add(StaticIconName.AUXILLARY_AUDIO);
        enumTestList.add(StaticIconName.BACK);
        enumTestList.add(StaticIconName.BATTERY_CAPACITY_0_OF_5);
        enumTestList.add(StaticIconName.BATTERY_CAPACITY_1_OF_5);
        enumTestList.add(StaticIconName.BATTERY_CAPACITY_2_OF_5);
        enumTestList.add(StaticIconName.BATTERY_CAPACITY_3_OF_5);
        enumTestList.add(StaticIconName.BATTERY_CAPACITY_4_OF_5);
        enumTestList.add(StaticIconName.BATTERY_CAPACITY_5_OF_5);
        enumTestList.add(StaticIconName.BLUETOOTH_AUDIO_SOURCE);
        enumTestList.add(StaticIconName.BLUETOOTH1);
        enumTestList.add(StaticIconName.BLUETOOTH2);
        enumTestList.add(StaticIconName.BROWSE);
        enumTestList.add(StaticIconName.CELL_PHONE_IN_ROAMING_MODE);
        enumTestList.add(StaticIconName.CELL_SERVICE_SIGNAL_STRENGTH_0_OF_5_BARS);
        enumTestList.add(StaticIconName.CELL_SERVICE_SIGNAL_STRENGTH_1_OF_5_BARS);
        enumTestList.add(StaticIconName.CELL_SERVICE_SIGNAL_STRENGTH_2_OF_5_BARS);
        enumTestList.add(StaticIconName.CELL_SERVICE_SIGNAL_STRENGTH_3_OF_5_BARS);
        enumTestList.add(StaticIconName.CELL_SERVICE_SIGNAL_STRENGTH_4_OF_5_BARS);
        enumTestList.add(StaticIconName.CELL_SERVICE_SIGNAL_STRENGTH_5_OF_5_BARS);
        enumTestList.add(StaticIconName.CHANGE_LANE_LEFT);
        enumTestList.add(StaticIconName.CHANGE_LANE_RIGHT);
        enumTestList.add(StaticIconName.CHECK_BOX_CHECKED);
        enumTestList.add(StaticIconName.CHECK_BOX_UNCHECKED);
        enumTestList.add(StaticIconName.CLIMATE);
        enumTestList.add(StaticIconName.CLOCK);
        enumTestList.add(StaticIconName.COMPOSE);
        enumTestList.add(StaticIconName.CONTACT);
        enumTestList.add(StaticIconName.CONTINUE);
        enumTestList.add(StaticIconName.DASH);
        enumTestList.add(StaticIconName.DATE);
        enumTestList.add(StaticIconName.DELETE);
        enumTestList.add(StaticIconName.DESTINATION);
        enumTestList.add(StaticIconName.DESTINATION_FERRY_AHEAD);
        enumTestList.add(StaticIconName.EBOOKMARK);
        enumTestList.add(StaticIconName.END_CALL);
        enumTestList.add(StaticIconName.FAIL);
        enumTestList.add(StaticIconName.FAST_FORWARD_30_SECS);
        enumTestList.add(StaticIconName.FAVORITE_HEART);
        enumTestList.add(StaticIconName.FAVORITE_STAR);
        enumTestList.add(StaticIconName.FAX_NUMBER);
        enumTestList.add(StaticIconName.FILENAME);
        enumTestList.add(StaticIconName.FILTER);
        enumTestList.add(StaticIconName.FOLDER);
        enumTestList.add(StaticIconName.FUEL_PRICES);
        enumTestList.add(StaticIconName.FULL_MAP);
        enumTestList.add(StaticIconName.GENERIC_PHONE_NUMBER);
        enumTestList.add(StaticIconName.GENRE);
        enumTestList.add(StaticIconName.GLOBAL_KEYBOARD);
        enumTestList.add(StaticIconName.HIGHWAY_EXIT_INFORMATION);
        enumTestList.add(StaticIconName.HOME_PHONE_NUMBER);
        enumTestList.add(StaticIconName.HYPERLINK);
        enumTestList.add(StaticIconName.ID3_TAG_UNKNOWN);
        enumTestList.add(StaticIconName.INCOMING_CALLS);
        enumTestList.add(StaticIconName.INFORMATION);
        enumTestList.add(StaticIconName.IPOD_MEDIA_SOURCE);
        enumTestList.add(StaticIconName.JOIN_CALLS);
        enumTestList.add(StaticIconName.KEEP_LEFT);
        enumTestList.add(StaticIconName.KEEP_RIGHT);
        enumTestList.add(StaticIconName.KEY);
        enumTestList.add(StaticIconName.LEFT);
        enumTestList.add(StaticIconName.LEFT_ARROW);
        enumTestList.add(StaticIconName.LEFT_EXIT);
        enumTestList.add(StaticIconName.LINE_IN_AUDIO_SOURCE);
        enumTestList.add(StaticIconName.LOCKED);
        enumTestList.add(StaticIconName.MEDIA_CONTROL_LEFT_ARROW);
        enumTestList.add(StaticIconName.MEDIA_CONTROL_RECORDING);
        enumTestList.add(StaticIconName.MEDIA_CONTROL_RIGHT_ARROW);
        enumTestList.add(StaticIconName.MEDIA_CONTROL_STOP);
        enumTestList.add(StaticIconName.MICROPHONE);
        enumTestList.add(StaticIconName.MISSED_CALLS);
        enumTestList.add(StaticIconName.MOBILE_PHONE_NUMBER);
        enumTestList.add(StaticIconName.MOVE_DOWN);
        enumTestList.add(StaticIconName.MOVE_UP);
        enumTestList.add(StaticIconName.MP3_TAG_ARTIST);
        enumTestList.add(StaticIconName.NAVIGATION);
        enumTestList.add(StaticIconName.NAVIGATION_CURRENT_DIRECTION);
        enumTestList.add(StaticIconName.NEGATIVE_RATING_THUMBS_DOWN);
        enumTestList.add(StaticIconName.NEW);
        enumTestList.add(StaticIconName.OFFICE_PHONE_NUMBER);
        enumTestList.add(StaticIconName.OPENED);
        enumTestList.add(StaticIconName.ORIGIN);
        enumTestList.add(StaticIconName.OUTGOING_CALLS);
        enumTestList.add(StaticIconName.PHONE_CALL_1);
        enumTestList.add(StaticIconName.PHONE_CALL_2);
        enumTestList.add(StaticIconName.PHONE_DEVICE);
        enumTestList.add(StaticIconName.PHONEBOOK);
        enumTestList.add(StaticIconName.PHOTO);
        enumTestList.add(StaticIconName.PLAY);
        enumTestList.add(StaticIconName.PLAYLIST);
        enumTestList.add(StaticIconName.POPUP);
        enumTestList.add(StaticIconName.POSITIVE_RATING_THUMBS_UP);
        enumTestList.add(StaticIconName.POWER);
        enumTestList.add(StaticIconName.PRIMARY_PHONE);
        enumTestList.add(StaticIconName.RADIO_BUTTON_CHECKED);
        enumTestList.add(StaticIconName.RADIO_BUTTON_UNCHECKED);
        enumTestList.add(StaticIconName.RECENT_CALLS);
        enumTestList.add(StaticIconName.RECENT_DESTINATIONS);
        enumTestList.add(StaticIconName.REDO);
        enumTestList.add(StaticIconName.REFRESH);
        enumTestList.add(StaticIconName.REMOTE_DIAGNOSTICS_CHECK_ENGINE);
        enumTestList.add(StaticIconName.RENDERED_911_ASSIST);
        enumTestList.add(StaticIconName.REPEAT);
        enumTestList.add(StaticIconName.REPEAT_PLAY);
        enumTestList.add(StaticIconName.REPLY);
        enumTestList.add(StaticIconName.REWIND_30_SECS);
        enumTestList.add(StaticIconName.RIGHT);
        enumTestList.add(StaticIconName.RIGHT_EXIT);
        enumTestList.add(StaticIconName.RINGTONES);
        enumTestList.add(StaticIconName.ROUNDABOUT_LEFT_HAND_1);
        enumTestList.add(StaticIconName.ROUNDABOUT_LEFT_HAND_2);
        enumTestList.add(StaticIconName.ROUNDABOUT_LEFT_HAND_3);
        enumTestList.add(StaticIconName.ROUNDABOUT_LEFT_HAND_4);
        enumTestList.add(StaticIconName.ROUNDABOUT_LEFT_HAND_5);
        enumTestList.add(StaticIconName.ROUNDABOUT_LEFT_HAND_6);
        enumTestList.add(StaticIconName.ROUNDABOUT_LEFT_HAND_7);
        enumTestList.add(StaticIconName.ROUNDABOUT_RIGHT_HAND_1);
        enumTestList.add(StaticIconName.ROUNDABOUT_RIGHT_HAND_2);
        enumTestList.add(StaticIconName.ROUNDABOUT_RIGHT_HAND_3);
        enumTestList.add(StaticIconName.ROUNDABOUT_RIGHT_HAND_4);
        enumTestList.add(StaticIconName.ROUNDABOUT_RIGHT_HAND_5);
        enumTestList.add(StaticIconName.ROUNDABOUT_RIGHT_HAND_6);
        enumTestList.add(StaticIconName.ROUNDABOUT_RIGHT_HAND_7);
        enumTestList.add(StaticIconName.RSS);
        enumTestList.add(StaticIconName.SETTINGS);
        enumTestList.add(StaticIconName.SHARP_LEFT);
        enumTestList.add(StaticIconName.SHARP_RIGHT);
        enumTestList.add(StaticIconName.SHOW);
        enumTestList.add(StaticIconName.SHUFFLE_PLAY);
        enumTestList.add(StaticIconName.SKI_PLACES);
        enumTestList.add(StaticIconName.SLIGHT_LEFT);
        enumTestList.add(StaticIconName.SLIGHT_RIGHT);
        enumTestList.add(StaticIconName.SMARTPHONE);
        enumTestList.add(StaticIconName.SORT_LIST);
        enumTestList.add(StaticIconName.SPEED_DIAL_NUMBERS_NUMBER_0);
        enumTestList.add(StaticIconName.SPEED_DIAL_NUMBERS_NUMBER_1);
        enumTestList.add(StaticIconName.SPEED_DIAL_NUMBERS_NUMBER_2);
        enumTestList.add(StaticIconName.SPEED_DIAL_NUMBERS_NUMBER_3);
        enumTestList.add(StaticIconName.SPEED_DIAL_NUMBERS_NUMBER_4);
        enumTestList.add(StaticIconName.SPEED_DIAL_NUMBERS_NUMBER_5);
        enumTestList.add(StaticIconName.SPEED_DIAL_NUMBERS_NUMBER_6);
        enumTestList.add(StaticIconName.SPEED_DIAL_NUMBERS_NUMBER_7);
        enumTestList.add(StaticIconName.SPEED_DIAL_NUMBERS_NUMBER_8);
        enumTestList.add(StaticIconName.SPEED_DIAL_NUMBERS_NUMBER_9);
        enumTestList.add(StaticIconName.SUCCESS);
        enumTestList.add(StaticIconName.TRACK_TITLE);
        enumTestList.add(StaticIconName.TRAFFIC_REPORT);
        enumTestList.add(StaticIconName.TURN_LIST);
        enumTestList.add(StaticIconName.UTURN_LEFT_TRAFFIC);
        enumTestList.add(StaticIconName.UTURN_RIGHT_TRAFFIC);
        enumTestList.add(StaticIconName.UNDO);
        enumTestList.add(StaticIconName.UNLOCKED);
        enumTestList.add(StaticIconName.USB_MEDIA_AUDIO_SOURCE);
        enumTestList.add(StaticIconName.VOICE_CONTROL_SCROLLBAR_LIST_ITEM_NO_1);
        enumTestList.add(StaticIconName.VOICE_CONTROL_SCROLLBAR_LIST_ITEM_NO_2);
        enumTestList.add(StaticIconName.VOICE_CONTROL_SCROLLBAR_LIST_ITEM_NO_3);
        enumTestList.add(StaticIconName.VOICE_CONTROL_SCROLLBAR_LIST_ITEM_NO_4);
        enumTestList.add(StaticIconName.VOICE_RECOGNITION_FAILED);
        enumTestList.add(StaticIconName.VOICE_RECOGNITION_PAUSE);
        enumTestList.add(StaticIconName.VOICE_RECOGNITION_SUCCESSFUL);
        enumTestList.add(StaticIconName.VOICE_RECOGNITION_SYSTEM_ACTIVE);
        enumTestList.add(StaticIconName.VOICE_RECOGNITION_SYSTEM_LISTENING);
        enumTestList.add(StaticIconName.VOICE_RECOGNITION_TRY_AGAIN);
        enumTestList.add(StaticIconName.WARNING);
        enumTestList.add(StaticIconName.WEATHER);
        enumTestList.add(StaticIconName.WIFI_FULL);
        enumTestList.add(StaticIconName.ZOOM_IN);
        enumTestList.add(StaticIconName.ZOOM_OUT);

        assertTrue("Enum value list does not match enum class list",
                enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
    }

}

package com.communication.client.impl;

public class CommandResource {

	public static final byte[] APP_GUID = { (byte) 0x87, (byte) 0x93, (byte) 0x82, (byte) 0x86, (byte) 0x82,
			(byte) 0x80, (byte) 0x8F, (byte) 0x8F, (byte) 0x86, (byte) 0x94, (byte) 0x88, (byte) 0x83, (byte) 0xF0,
			(byte) 0xF1, (byte) 0xF2, (byte) 0xF3, 0x00, 0x00, 0x00, 0x01 };
	
	public static final short CLIENT_BIND_SERVER = 0x1001;
	
	public static final short WIFI_GET_SSID = 0x1101;
	public static final short WIFI_SET_SSID = 0x1102;
	public static final short WIFI_GET_KEY = 0x1103;
	public static final short WIFI_SET_KEY = 0x1104;
	public static final short WIFI_SET_LIMIT_CONNECT_NUM = 0x1105;
	public static final short WIFI_GET_LIMIT_CONNECT_NUM = 0x1106;

	// M:add protocol by xrl 2016/12/02 @{
	public static final short WIFI_GET_SSID_KEY = 0x1107;
	public static final short WIFI_SET_SSID_KEY = 0x1108;
	//@}
	
	
	public static final short VIDEO_GET_TIME = 0x120B;
	public static final short VIDEO_SET_TIME = 0x120C;
	public static final short VIDEO_START_RECODING = 0x120D;
	public static final short VIDEO_STOP_RECODING = 0x120E;
	public static final short VIDEO_GET_USER_RECORDING_STATUS = 0x1211;
	public static final short VIDEO_GET_ONE_FRAME_FOR_ALGORITHM = 0x1212;
	
	public static final short CAPTURE_SHUTTER_PRESS = 0x1301;
	public static final short CAPTURE_GET_LOCATION_INFO = 0x130B;
	public static final short CAPTURE_SET_LOCATION_INFO = 0x130C;
	public static final short CAPTURE_ENTER_CAPTURE_MODE = 0x1313;
	public static final short CAPTURE_EXIT_CAPTURE_MODE = 0x1314;
	public static final short CAPTURE_IS_CAPTURE_MODE = 0x1315;
	
	public static final short FILE_GET_STORAGE_COUNTS = 0x1501;
	public static final short FILE_GET_OBJECT_HANDLES = 0x1502;
	public static final short FILE_GET_OBJECT_INFO = 0x1503;
	public static final short FILE_DOWNLOAD_THUMB_FILE = 0x1504;
	public static final short FILE_DOWNLOAD_FILE = 0x1505;
	public static final short FILE_DELETE_ALL = 0x1506;
	public static final short FILE_DELETE_BATCH = 0x1507;
	public static final short FILE_GET_THUMB_INFO = 0x1508;
	
	public static final short SYS_GET_SIM_FLOW = 0x1604;
	public static final short SYS_REBOOT = 0x1607;
	public static final short SYS_CANCEL_CMD = 0x1608;
	public static final short SYS_GET_STORAGE_CAPACITY = 0x1609;
	public static final short SYS_SET_APN = 0x160A;
	public static final short SYS_RESET = 0x160B;
	public static final short SYS_FORMAT_UNRECOGNIZED_SDCARD = 0x160C;
	
	public static final short SYS_UPGRADE_CHECK_VERSION = 0x160D;
	public static final short SYS_UPGRADE_DOWNLOAD_OTA_FILE = 0x160E;
	public static final short SYS_UPGRADE_EXEC_UPGRADE = 0x160F;
	
	public static final short SYS_CAPTURE = 0x180B;
	public static final short SYS_VIDEO_START = 0x180C;
	public static final short SYS_VIDEO_STOP = 0x180D;
	
	// M:add protocol by xrl 2016/12/02 @{
		public static final short SYS_GET_SIM_FLOW_WARNING_VALUE = 0x1613;
	//	public static final short SYS_GET_FATIGUE_DRIVING_WARN_SWITCH_STATUS = 0x1614;
		public static final short SYS_AP_SWITCH_TO_WIFI = 0x1614;//kang
		public static final short SYS_GET_SYSTEM_VOLUME = 0x1615;
		public static final short SYS_SET_SYSTEM_VOLUME = 0x1616;
		//@}
		// M:add protocol by xrl 2016/12/06 @{
		public static final short SYS_GET_LDWS_SWITCH_STATUS = 0x1617;
		public static final short SYS_LDWS_SWITCH = 0x1618;
		//@}
		
	public static final short ERR_SUCCESS = 0x00;
	public static final short ERR_INVALID_PARAMETER = 0x01;
	public static final short ERR_FAIL = 0x02;
	public static final short ERR_ALREADY_RECORDING = 0x26;
	public static final short ERR_NOT_START_RECORDING = 0x27;
	public static final short ERR_FILE_NOT_EXIST = 0x28;
	public static final short ERR_CRC_ERROR = 0x29;
	public static final short ERR_SIM_NOT_EXIST = 0x2A;
	public static final short ERR_APN_EXIST = 0x2B;
	public static final short ERR_START_POSITION_ERROR = 0x2C;
	public static final short ERR_BATTERY_NOT_ENOUGH = 0x2D;
	public static final short ERR_CLIENT_UNBIND = 0x2E;
	public static final short ERR_CURRENT_CAPTUREING = 0x2F;
	public static final short ERR_REMOTE_ALREADY_RECORDING = 0x30;
	public static final short ERR_UNRECOGNIZED_SDCARD_NOT_EXIST = 0x31;
	public static final short ERR_CAPTURE_MODE = 0x32;
	public static final short ERR_VIDEO_MODE = 0x33;
	
	public static final short EVENT_STOP_CAPTURING = 0x0010;
	public static final short EVENT_STOP_RECORDING = 0x0012;
	public static final short EVENT_COMPLETE_CAPTURING = 0x000D;
	public static final short EVENT_COMPLETE_RECORDING = 0x000E;
	public static final short EVENT_START_RECORDING = 0x0011;
	public static final short EVENT_WIFI_HEART_BEAT = 0x0015;
	public static final short EVENT_CAMERA_PAUSE = 0x0016;
	public static final short EVENT_CAMERA_RESUME = 0x0017;
	public static final short EVENT_FORMAT_UNRECOGNIZED_SDCARD = 0x0018;
	
	public static final short EVENT_FOTA_CHECK_VERSION_SUCCESS = 0x0019;
	public static final short EVENT_FOTA_CHECK_VERSION_FAIL = 0x001A;
	public static final short EVENT_FOTA_DOWNLOAD_FILE_FINISH = 0x1B;
	public static final short EVENT_FOTA_DOWNLOAD_FILE_FAIL = 0x001C;
	public static final short EVENT_FOTA_SYSTEM_UPGRADE_START = 0x001D;
	public static final short EVENT_FOTA_SYSTEM_UPGRADE_FAIL = 0x001E;
	public static final short EVENT_CLIENT_HEART_BEAT = 0x001F;
	
	public static final short VIDEO_GET_VOICE_RECORD_STATUS = 101;
	public static final short VIDEO_SET_VOICE_RECORD_STATUS = 102;
	public static final short VIDEO_GET_VIDEO_RESOLUTION = 103;
	public static final short VIDEO_SET_VIDEO_RESOLUTION = 104;
	public static final short VIDEO_GET_TIMESTAMP_STATUS = 108;
	public static final short VIDEO_SET_TIMESTAMP_STATUS = 109;
	
	public static final short VIDEO_START_LIVESTREAM = 130;
	public static final short VIDEO_STOP_LIVESTREAM = 131;
	public static final short VIDEO_PLAY_START = 132;
	public static final short VIDEO_PLAY_STOP = 133;
	public static final short VIDEO_PLAY_PAUSE = 134;
	public static final short VIDEO_PLAY_SEEK = 135;
	public static final short VIDEO_GET_QV_THUMB = 136;
	public static final short VIDEO_PLAY_RESUME = 137;

	public static final short VIDEO_GET_VIDEO_QUALITY = 142;
	public static final short VIDEO_SET_VIDEO_QUALITY = 143;
	public static final short VIDEO_GET_REMAIN_TIME = 144;
	public static final short VIDEO_GET_VIS_STATUS = 145;
	public static final short VIDEO_SET_VIS_STATUS = 146;
	public static final short CAPTURE_IMAGE_START = 152;
	public static final short CAPTURE_GET_IMAGE_LIST = 153;
	public static final short CAPTURE_GET_IMAGE_INFO = 154;
	public static final short SYS_GET_DR_STATUS = 201;
	public static final short SYS_GET_RTC = 202;
	public static final short SYS_SET_RTC = 203;
	public static final short SYSTEM_GET_DATE_TIME_FORMAT = 204;
	public static final short SYSTEM_SET_DATE_TIME_FORMAT = 205;
	public static final short SYS_GET_GSENSOR = 206;
	public static final short SYS_SET_GSENSOR = 207;
	public static final short SYS_GET_LCD_AUTO_SHUTDOWN_VALUE = 208;
	public static final short SYS_SET_LCD_AUTO_SHUTDOWN_VALUE = 209;
	public static final short SYS_SET_SPEAKER = 210;
	public static final short SYS_GET_SPEAKER = 211;
	public static final short SYS_GET_FILESYS_FORMAT = 212;
	public static final short SYS_GET_FREE_SPACE = 213;
	public static final short SYS_GET_TOTAL_SPACE = 214;
	public static final short SYS_GET_FW_VERSION = 215;
	public static final short SYS_FORMAT_SD_CARD = 216;
	
	public static final short SYS_GET_IMEI = 0x1619;
	public static final short SYS_SET_SIM_FLOW_TOTAL = 0x1620;
	
	public static final short SYS_UPLOAD_FILE = 218;
	public static final short SYS_FW_UPGRADE = 219;
	public static final short SYS_GET_FUNCTION_MODE = 221;
	public static final short SYS_SET_FUNCTION_MODE = 222;
	public static final short SYS_GET_USE_STORAGE = 230;
	public static final short SYS_GET_BATTERY_LEVEL = 231;
	// public static final short SYS_GET_SD_CHECK = 232;
	public static final short SYS_GET_LIVEVIEW_MODE = 262;

	public static final short CAPTURE_GET_TIMELAPSE_RATE = 303;
	public static final short CAPTURE_SET_TIMELAPSE_RATE = 304;
	public static final short CAPTURE_GET_TIMELAPSE_DURATION = 305;
	public static final short CAPTURE_SET_TIMELAPSE_DURATION = 306;
	
	public static final short CAPTURE_GET_QV_IMAGE = 312;
	public static final short CAPTURE_GET_EXPOSURE_STATUS = 313;
	public static final short CAPTURE_SET_EXPOSURE_STATUS = 314;
	public static final short CAPTURE_GET_ISO_STATUS = 321;
	public static final short CAPTURE_SET_ISO_STATUS = 322;
	public static final short CAPTURE_GET_WB_STATUS = 323;
	public static final short CAPTURE_SET_WB_STATUS = 324;

	public static final short CAPTURE_TIMELAPSECAPTURE_PAUSE = 325;
	public static final short CAPTURE_TIMELAPSECAPTURE_RESUME = 326;
	public static final short CAPTURE_TIMELAPSECAPTURE_START = 327;
	public static final short CAPTURE_TIMELAPSECAPTURE_STOP = 328;
	public static final short CAPTURE_GET_TIMELAPSE_FRAMERATE = 331;
	public static final short CAPTURE_SET_TIMELAPSE_FRAMERATE = 332;
	public static final short CAPTURE_GET_IMAGE_RESOLUTION = 335;
	public static final short CAPTURE_SET_IMAGE_RESOLUTION = 336;
	public static final short FILE_GET_FILE_FULLHD = 403;
	
	public static final short FILE_DOWNLOAD_TIMELAPSE_FRAME = 410;

	public static final short INITIAL_CHECK_VALIDATION = 501;
	public static final short INITIAL_GET_INITIAL_PARAMETERS = 502;

	public static final short CAPTURE_MODE_SETTING = 2001;
	public static final short LENS_ZOOM_ACTION = 2002;
	public static final short LENS_AUTO_FOCUS = 2003;
	public static final short CAPTURE_SET_NIGHTMODE = 2004;
	public static final short CAPTURE_GET_NIGHTMODE = 2005;
	public static final short CAPTURE_GET_IMAGE_QUALITY = 2006;
	public static final short CAPTURE_SET_IMAGE_QUALITY = 2007;
	public static final short CAPTURE_GET_METERING_MODE = 2008;
	public static final short CAPTURE_SET_METERING_MODE = 2009;
	public static final short CAPTURE_GET_FOCUS_MODE = 2010;
	public static final short CAPTURE_SET_FOCUS_MODE = 2011;
	public static final short CAPTURE_SET_USER_SELECTED_ZONE = 2012;
	public static final short CAPTURE_GET_MACRO_MODE = 2013;
	public static final short CAPTURE_SET_MACRO_MODE = 2014;
	public static final short CAPTURE_GET_FACEDETECT_STATUS = 2015;
	public static final short CAPTURE_SET_FACEDETECT_STATUS = 2016;
	public static final short CAPTURE_GET_BURST_MODE = 2017;
	public static final short CAPTURE_SET_BURST_MODE = 2018;
	public static final short CAPTURE_GET_DIGITAL_ZOOM = 2019;
	public static final short CAPTURE_SET_DIGITAL_ZOOM = 2020;
	public static final short CAPTURE_GET_OPTICAL_ZOOM = 2021;
	public static final short CAPTURE_SET_OPTICAL_ZOOM = 2022;
	public static final short PHOTO_CAPTURE_START = 2023;
	public static final short PHOTO_GET_IMAGE_LIST = 2024;
	public static final short PHOTO_GET_IMAGE_INFO = 2025;
	public static final short PHOTO_GET_REMAIN_STORAGE = 2026;
	public static final short PHOTO_GET_ROTATION_DEGREE = 2027;
	public static final short PHOTO_SET_ROTATION_DEGREE = 2028;
	public static final short PHOTO_GET_GPS_INFO_FROM_APP = 2029;
	public static final short PHOTO_SET_GPS_INFO_FROM_APP = 2030;
	public static final short PHOTO_GET_VIS_STATUS = 2031;
	public static final short PHOTO_SET_VIS_STATUS = 2032;
	public static final short CAPTURE_GET_IRDUTY_CYCLE = 2033;
	public static final short CAPTURE_SET_IRDUTY_CYCLE = 2034;
	public static final short CAPTURE_GET_STROBEMODE = 2035;
	public static final short CAPTURE_SET_STROBEMODE = 2036;
	public static final short SYSTEM_SET_SCENEMODE = 2037;
	public static final short SYSTEM_GET_SCENEMODE = 2038;
	public static final short SYSTEM_SET_IROVEREXPCRITERION = 2039;
	public static final short SYSTEM_GET_IROVEREXPSTATUS = 2040;
	public static final short SYSTEM_GET_PRODUCTID = 2041;

	public static final short EVENT_SD_CARD_FULL = 001;
	public static final short EVENT_SD_CARD_UNPLUG = 002;
	public static final short EVENT_SD_CARD_WRONG_FORMAT = 003;
	public static final short EVENT_SD_CARD_ONREADY = 004;
	public static final short EVENT_SD_CARD_FORMAT_BEGIN = 005;
	public static final short EVENT_SD_CARD_FORMAT_END = 006;
	public static final short EVENT_NEW_CLIP_FILE = 007;
	public static final short EVNET_OVERRIDE_CLIP_FILE = 8;
	public static final short EVENT_OVERRIDE_EMERGENCY_CLIP = 9;
	public static final short EVENT_GSENSOR_MSG = 010;
	public static final short EVENT_GSENSOR_EMERGENCY_BEGIN = 011;
	public static final short EVENT_GSENSOR_EMERGENCY_END = 012;
	public static final short EVENT_PUSH_EMERGENCY_BEGIN = 013;
	public static final short EVENT_PUSH_EMERGENCY_END = 014;
	public static final short EVENT_WIFI_SIGNAL_STRENGTH = 015;
	public static final short EVENT_CMD_CANCELED = 016;
	public static final short EVENT_SD_CARD_WRITE_PROTECT = 0x0011;
	public static final short EVENT_SD_UNUSABLE = 0x0012;
	public static final short EVENT_HIGH_TEMPERATURE = 0x0013;
	public static final short EVENT_LENS_ERROR = 0x0014;
	public static final short EVENT_VIDEO_PLAY_ERROR = 0x0017;
	public static final short EVENT_NO_SD_CARD = 0X0018;
	public static final short EVENT_OBJECT_ADDED = 0x4002;
	public static final short EVNET_TELEWIDE_CHANGE_DONE = 0x4003;
	public static final short EVENT_IR_ONOFF_CHANGE_DONE = 0x4004;
	public static final short EVENT_START_VIDEO_AF = 0x4005;
	public static final short EVENT_BATTERY_LEVEL_CHANGED = 0x4006;
	public static final short EVENT_ERROR_CAPTURING = 0x4007;
	public static final short EVENT_ERROR_RECORDING = 0x4008;
	
	
	public static final short EVENT_START_CAPTURING = 0x400D;
	public static final short EVENT_VIDEO_PLAYBACK_FINISH = 0x400F;
	public static final short EVENT_TIME_LAPSE_CAPTURE_ONE = 0x4010;
	public static final short EVENT_FUNCTION_MODE_CHANGE_DONE = 0x4011;
	public static final short EVENT_LVSTRAM_READY = 0x4012;
	public static final short EVENT_SLOW_MOTION_CHANGE = 0x4013;
	public static final short EVENT_AF_STOP = 0x4016;
	public static final short EVENT_WIFI_STATUS_SYNC = 0x5001;

	// public static final short EVENT_ID_AUTOPOWER_OFF_EVENT = 0xC0B2 ;

	
	
	public static final short ERR_OPEN_FILE_FAIL = 0x0B;
	public static final short ERR_READ_FILE_FAIL = 0x0C;
	public static final short ERR_REQUEST_MEM_FAIL = 0x0D;
	public static final short ERR_SEND_WIFI_EVENT_FAIL = 0x0E;
	public static final short ERR_DEVICE_NOT_READY = 0x10;
	public static final short ERR_DEVICE_BUSY = 0x11;
	public static final short ERR_IMCOMPLETE_TRANSFER = 0x12;
	public static final short ERR_CAPTURE_GET_QV_FAIL = 0x13;
	public static final short ERR_SD_CAPACITY_UNKNOWN = 0x14;
	public static final short ERR_INVALID_OBJECT_HANDLE = 0x15;
	public static final short ERR_GET_THUMB_FAIL = 0x16;
	public static final short ERR_GET_HD_IMG_FAIL = 0x17;
	public static final short ERR_UPGRADE_FW_NOT_FOUND = 0x18;
	public static final short ERR_UPGRADE_FW_INVALID = 0x19;
	public static final short ERR_GET_OBJINFO_FAIL = 0x1A;
	public static final short ERR_INVALID_MODE = 0x1B;
	public static final short ERR_SAME_MODE = 0x1C;
	public static final short ERR_GET_FILE_NOT_READY = 0x1D;
	public static final short ERR_SD_CAPACITY_FULL = 0x1E;
	public static final short ERR_ABORT = 0x1F;
	public static final short ERR_RECORD_SLOW_CARD = 0x20;
	public static final short ERR_RECORD_WRITE_FAIL = 0x21;
	public static final short ERR_UPLOAD_FAIL = 0x22;
	public static final short ERR_UPGRADE_FW_VERSION_NOT_MATCH = 0x23;
	public static final short ERR_NETDB_REQUEST_FAIL = 0x24;
	public static final short ERR_VIDEO_SEEK_FAIL = 0x25;
	public static final short ERR_NETDB_NOT_READY = 0x26;
	public static final short ERR_UPGRADE_BATTERY_LEVEL_FAIL = 0x27;
	public static final short ERR_UPGRADE_MCU_VERSION_NOT_MATCH = 0x28;
	public static final short ERR_UPGRADE_BOOT_NOT_FOUND = 0x2A;
	public static final short ERR_UPGRADE_BOOT_VERSION_NOT_MATCH = 0x2B;
	public static final short ERR_UPGRADE_BOOT_INVALID = 0x2C;
	public static final short ERR_UPGRADE_BLE_INVALID = 0x2D;
	public static final short ERR_RAWDATA_DOWNLOADFAIL = 0x2E;
	public static final short ERR_RECORD_COMPRESSING_FAIL = 0x2F;
	public static final short ERR_EVENT_QUEUE_FULL = 0x30;
	public static final short ERR_NO_SD_CARD = 0x31;
	public static final short ERR_TIMELAPSE_LOW_BAT = 0x32;
	public static final short ERR_NOT_AUTOSAVE = 0x33;
	public static final short ERR_SOCKETCLOSE = 0x34;
	public static final short ERR_UNAVAILABLE_PORC_RECORDING = 0x35;
	public static final short ERR_DARK_PROTECTION = 0x36;
	public static final short ERR_NOT_PROVIDE_AUTOBACKUPLIB = 0x37;
	public static final short ERR_IR_PROTECTION = 0x38;
	public static final short ERR_SYSTEM_SKIPCMD = 0xFE;
	public static final short ERR_SYSTEM_ERROR = 0xFF;

}

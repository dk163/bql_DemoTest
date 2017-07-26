package com.protruly.minaclient;

public class Item {
	public final static int BIND_SERVICE = 0;
	public final static int ENTER_CAPTURE_MODE = 1;
	public final static int EXIT_CAPTURE_MODE = 2;
	public final static int CAPTURE = 3;
	public final static int VIDEO_START = 4;
	public final static int VIDEO_STOP = 5;
	public final static int GET_VOLUME = 6;
	public final static int SET_VOLUME = 7;
	public final static int GET_WIFI_SSID = 8;
	public final static int GET_SIM_FLOW = 9;
	public final static int GET_SYSTEM_IMEI = 10;
	public final static int GET_FILE_COUNT = 11;
	public final static int SWITCH_AP_1 = 12;
	public final static int SWITCH_AP_STATUS = 13;
	public final static int SWITCH_AP_0 = 14;
	public final static int BIND_SERVER = 15;
	public final static int SEND_BROADCAST = 16;

	public final static String[] mTestItemList ={
		"绑定",         
		"进入拍照模式",	
		"退出拍照模式",	
		"拍照",			
		"录像开始",		
		"录像结束",		
		"获取音量",		
		"设置音量",		
		"获取ssid",		
		"流量查询",	    
		"获取imei号",    
		"获取文件个数",
		"ap switch 1",
		"get switch ap status",
		"ap switch 0",
		"conenct server",
		"发UDP 广播"
	};
}

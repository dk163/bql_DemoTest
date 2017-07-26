package com.communication.client.constant;

public interface Constant {

	public static class ReturnCode {

		public static String CODE_404 = "404";

		public static String CODE_403 = "403";

		public static String CODE_405 = "405";

		public static String CODE_200 = "200";

		public static String CODE_206 = "206";

		public static String CODE_500 = "500";
	}

	public static String UTF8 = "UTF-8";

	public static byte MESSAGE_SEPARATE = '\b';

	public static int CIM_DEFAULT_MESSAGE_ORDER = 1;

	public static final String SESSION_KEY = "account";

	public static final String HEARTBEAT_KEY = "heartbeat";

	/**
	 * 服务端心跳请求命令
	 */
	public static final String CMD_HEARTBEAT_REQUEST = "cmd_server_hb_request";
	
	/**
	 * 客户端心跳响应命令
	 */
	public static final String CMD_HEARTBEAT_RESPONSE = "cmd_client_hb_response";

	public static class SessionStatus {
		public static int STATUS_OK = 0;
		public static int STATUS_CLOSED = 1;
	}

	public static class MessageType {
		// 用户会 踢出下线消息类型
		public static String TYPE_999 = "999";
	}

	static final String CIM_SERVER_HOST = "localhost";
	
	//static final String CIM_SERVER_IP = "172.16.23.18";
	
	static final String CIM_SERVER_IP = "192.168.43.1";
	
	static final int CIM_SERVER_PORT_SEND = 7000;
	static final int CIM_SERVER_PORT_RECEIVE = 9001;
	static final int CIM_SERVER_PORT_EVENT = 9002;
	static final int CIM_SERVER_PORT_FILE = 9003;
	static final int CIM_SERVER_PORT_THUMNAIL = 9004;
    
	// 各种包长度
	static final int REPLY_PACKET_HEADER_LENGTH = 9;
	static final int SENT_PACKET_HEADER_LENGTH = 9;
	static final int EVENT_PACKET_HEADER_LENGTH = SENT_PACKET_HEADER_LENGTH;
	
	// 接收包头起始字节
	static final byte SEND_PACKEY_IDENTIFY = (byte)0xFC;
	
	// 发送包头起始字节
	static final byte REPLAY_PACKEY_IDENTIFY = (byte)0xFB;
	
	// 事件包头起始字节
	static final byte EVENT_PACKEY_IDENTIFY = (byte)0xFA;
	
	// 包头版本号
	public static byte PACKEY_VERSION =  (byte)0x05;
	
	// 是否加密，0为不加密，1为加密
	public static byte PACKEY_ENCRYPT =  (byte)0x00;
	public final static String UNIQUE_ID = "k28_mina_test_client";
	// 空闲时间值UNIT S
	static final int IDLE_TIME = 5;
	static final int TIME_OUT = 60;
	static final int HEAT_ITME = 2;
	// 是否支持多用户
	static final boolean SUPPORT_MUTIL_USER = false;
}
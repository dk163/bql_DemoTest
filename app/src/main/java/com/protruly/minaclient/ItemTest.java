package com.protruly.minaclient;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Arrays;

import org.apache.mina.core.buffer.IoBuffer;

import com.communication.client.constant.Constant;
import com.communication.client.impl.CommandManger;
import com.communication.client.impl.CommandResource;
import com.communication.client.model.SentPacket;
import com.communication.client.session.SessionManager;
import com.communication.client.utils.StringUtil;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class ItemTest extends Handler{
	private static final String TAG = CommandManger.TAG;
    public static final CharsetDecoder decoder = (Charset.forName("UTF-8")).newDecoder();
    public static final CharsetEncoder encoder = (Charset.forName("UTF-8")).newEncoder();
    private Handler mHandler;
    private static ItemTest mItemTest;
    private MainActivity mAcitivty;
    
    public void setmAcitivty(MainActivity mAcitivty) {
		this.mAcitivty = mAcitivty;
	}

	public static ItemTest getInstance(){
    	if(mItemTest == null){
    		mItemTest = new ItemTest();
    	}
    	return mItemTest;
    }
    
	public ItemTest(){
		HandlerThread handlerThread = new HandlerThread(TAG);
		handlerThread.start();
		mHandler = new CommandHandler(handlerThread.getLooper());
	}

	private class CommandHandler extends Handler{
		public CommandHandler(Looper looper){
			super(looper);
		}
		
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Item.BIND_SERVICE:
				sendCommandToServer(CommandResource.CLIENT_BIND_SERVER, null);
				break;
			case Item.ENTER_CAPTURE_MODE:
				sendCommandToServer(CommandResource.CAPTURE_ENTER_CAPTURE_MODE, null);
				break;
			case Item.EXIT_CAPTURE_MODE:
				sendCommandToServer(CommandResource.CAPTURE_EXIT_CAPTURE_MODE, null);
				break;
				
			case Item.CAPTURE:
				sendCommandToServer(CommandResource.CAPTURE_SHUTTER_PRESS, null);
				break;
			case Item.VIDEO_START:
				sendCommandToServer(CommandResource.VIDEO_START_RECODING, null);
				break;
			case Item.VIDEO_STOP:
				sendCommandToServer(CommandResource.VIDEO_STOP_RECODING, null);
				break;	
			case Item.GET_FILE_COUNT:
				sendCommandToServer(CommandResource.FILE_GET_STORAGE_COUNTS, null);
				break;
			case Item.GET_VOLUME:
				Log.d(TAG, "commandGetVolume");
				sendCommandToServer(CommandResource.SYS_GET_SYSTEM_VOLUME, null);
				break;
			case Item.SET_VOLUME:
				int value = 15;
				sendCommandToServer(CommandResource.SYS_SET_SYSTEM_VOLUME, new byte[]{(byte)(value&0xFF)});
				break;	
			case Item.GET_WIFI_SSID:
				sendCommandToServer(CommandResource.WIFI_GET_SSID, null);
				break;

			case Item.GET_SIM_FLOW:
				sendCommandToServer(CommandResource.SYS_GET_SIM_FLOW, null);
				break;
			case Item.GET_SYSTEM_IMEI:
				sendCommandToServer(CommandResource.SYS_GET_IMEI, null);
				break;
			case Item.SWITCH_AP_1:
				byte bo = 1;
				String ssid = "kang";
				System.out.println(ssid.getBytes().length);
				String pwd = "123456789";
				System.out.println(pwd.getBytes().length);
				int type = 1;
				
				IoBuffer buff = IoBuffer.allocate(5).setAutoExpand(true);
		        buff.put(bo);
//		        try {
//			        buff.putInt(ssid.length());
//					buff.putString(ssid, encoder);
//					buff.putInt(pwd.length());
//					buff.putString(pwd, encoder);
//				} catch (CharacterCodingException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
		        buff.put(StringUtil.intTo2byte(ssid.length()));
		        buff.put(ssid.getBytes());
		        buff.put(StringUtil.intTo2byte(pwd.length()));
		        buff.put(pwd.getBytes());
		        buff.put(StringUtil.intTo2byte(type));
		        //buff.putInt(type);
				buff.flip();
							
				sendCommandToServer((short)(0x1614), buff.array());		       
				break;
			case Item.SWITCH_AP_0:
				byte bo2 = 0;
				
				IoBuffer buff2 = IoBuffer.allocate(5).setAutoExpand(true);
		        buff2.put(bo2);
		        buff2.flip();
				sendCommandToServer((short)(0x1614), buff2.array());
				break;
			case Item.SWITCH_AP_STATUS:							
				sendCommandToServer((short)(0x1615), null);
				break;
			case Item.BIND_SERVER:
				mAcitivty.startConnectServer();
				break;
			case Item.SEND_BROADCAST:
				MulticastManager mMulticastManager = new MulticastManager(mAcitivty);
		        mMulticastManager.startMulticast();
				break;
			default:
				break;
			}
		}
	}
	
	public void sendItemTestForIndex(int position){
		mHandler.sendEmptyMessage(position);
	}
	
	public void bindServer(){
		mHandler.sendEmptyMessage(Item.BIND_SERVICE);
	}
	
	private void sendCommandToServer(short type,  byte[] value){
		SentPacket st = new SentPacket(type);
		IoBuffer mBuffer = IoBuffer.allocate(4).setAutoExpand(true);
        mBuffer.put(StringUtil.intTo2byte(Constant.UNIQUE_ID.length()));
        try {
			mBuffer.putString(Constant.UNIQUE_ID, encoder);
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
        
        if(value != null && value.length != 0){
        	 mBuffer.put(value);	
        }
        mBuffer.flip();
        
        byte[] mm = new byte[mBuffer.limit()];
        mBuffer.get(mm);
        Log.d(TAG, ",sendCommandToServer:"+Arrays.toString(mm));
        st.setBody(mm);
        
        CommandSend.send(st);
	}

  /*
    private void sendCommandToServer(short type,  long value){
		SentPacket st = new SentPacket(type);
		IoBuffer mBuffer = IoBuffer.allocate(4).setAutoExpand(true);
	    mBuffer.put(StringUtil.intTo2byte(Constant.UNIQUE_ID.length()));
	    try {
			mBuffer.putString(Constant.UNIQUE_ID, encoder);
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		} 
	    mBuffer.put(StringUtil.longTo8bytes(value));
	    mBuffer.flip();
	    
	    byte[] mm = new byte[mBuffer.limit()];
	    mBuffer.get(mm);
	    Log.d(TAG, ",sendCommandToServer:"+Arrays.toString(mm));
	    st.setBody(mm);
	    send(st);
	}

	private void sendCommandToServer(short type){
		SentPacket st = new SentPacket(type);
		IoBuffer mBuffer = IoBuffer.allocate(4).setAutoExpand(true);
	    mBuffer.put(StringUtil.intTo2byte(Constant.UNIQUE_ID.length()));
	    try {
			mBuffer.putString(Constant.UNIQUE_ID, encoder);
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
	    mBuffer.flip();
	    
	    byte[] mm = new byte[mBuffer.limit()];
	    mBuffer.get(mm);
	    Log.d(TAG, ",sendCommandToServer:"+Arrays.toString(mm));
	    st.setBody(mm);
	
	    send(st);
	}


	private void sendCommandToServer(short type, boolean value){
		SentPacket st= new SentPacket(type);
		IoBuffer mBuffer = IoBuffer.allocate(4).setAutoExpand(true);
	    mBuffer.put(StringUtil.intTo2byte(Constant.UNIQUE_ID.length()));
	    try {
			mBuffer.putString(Constant.UNIQUE_ID, encoder);
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
	    
	    if(value){
	    	mBuffer.put(new byte[]{(byte) (0x01)});  
	    }else{
		    mBuffer.put(new byte[]{(byte) (0x00)});  
	    }
	    mBuffer.flip();
	    
	    byte[] mm = new byte[mBuffer.limit()];
	    mBuffer.get(mm);
	    Log.d(TAG, ",sendCommandToServer:"+Arrays.toString(mm));
	    st.setBody(mm);
	    
	    send(st);
	}

	public void sendCommandToServer(short type, int value) {
		SentPacket st = new SentPacket(type);
		IoBuffer mBuffer = IoBuffer.allocate(4).setAutoExpand(true);
	    mBuffer.put(StringUtil.intTo2byte(Constant.UNIQUE_ID.length()));
	    try {
	    	mBuffer.putString(Constant.UNIQUE_ID, encoder);
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}  
	    mBuffer.put(StringUtil.intTo2byte(value));
	    mBuffer.flip();
	    
	    byte[] mm = new byte[mBuffer.limit()];
	    mBuffer.get(mm);
	    Log.d(TAG, ",sendCommandToServer:"+Arrays.toString(mm));
	    st.setBody(mm);
	    
	    send(st);
	}

	public void sendCommandToServer(short type, String value) {
		Log.d(TAG, ",sendCommandToServer: value = "+value);
		
		SentPacket st = new SentPacket(type);
		IoBuffer mBuffer = IoBuffer.allocate(4).setAutoExpand(true);
	    mBuffer.put(StringUtil.intTo2byte(Constant.UNIQUE_ID.length()));
	    try {
			mBuffer.putString(Constant.UNIQUE_ID, encoder);
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
	    mBuffer.put(StringUtil.intTo2byte(value.length()));
	    mBuffer.put(value.getBytes());
	    mBuffer.flip();
	    
	    byte[] mm = new byte[mBuffer.limit()];
	    mBuffer.get(mm);
	    Log.d(TAG, ",sendCommandToServer:"+Arrays.toString(mm));
	    st.setBody(mm);
	
	    send(st);
	}*/
	
	private static byte[] stringAddLen(byte[] body) {
		byte[] newByte = new byte[body.length+2];
		//System.arraycopy(body, 0, newByte, 0, body.length);
		//System.arraycopy(new byte[]{(byte) (0)}, 0, newByte, body.length, 1);
		System.arraycopy(StringUtil.intTo2byte(body.length), 0, newByte, 0, 2);
		System.arraycopy(body, 0, newByte, 2, body.length);
		
		return newByte;
	}
}

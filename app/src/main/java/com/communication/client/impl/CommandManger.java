package com.communication.client.impl;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.mina.core.buffer.IoBuffer;
import android.util.Log;
import com.communication.client.constant.Constant;
import com.communication.client.handler.CResponseHandler;
import com.communication.client.model.EventPacket;
import com.communication.client.model.ReplyPacket;
import com.communication.client.session.SessionManager;
import com.communication.client.utils.StringUtil;
import com.protruly.minaclient.CommandSend;

public final class CommandManger implements CResponseHandler {
	private volatile static CommandManger instance;
	
	public static String TAG = "TCPClient";
	public static final CharsetDecoder decoder = (Charset.forName("UTF-8")).newDecoder();
    public static final CharsetEncoder encoder = (Charset.forName("UTF-8")).newEncoder();
    
	public static CommandManger getInstance() {
		if (instance == null) {
			synchronized (CommandManger.class) {
				if (instance == null) {
					instance = new CommandManger();
				}
			}
		}
		return instance;
	}
	
	private boolean checkConnected() {
		return SessionManager.getInstance().isConnected();
	}

	private  Timer mTimer = null;
	private  HeartbeatTimerTask mTimerTask = null;

    public void startHeartBeat(){
    	Log.d(TAG, "startHeartBeat enter");
        
    	if(mTimer != null){
    		if(mTimerTask != null){
    			mTimerTask.cancel();
    			mTimerTask = null;
    		}
    	}
    	
    	if(mTimer == null){
    		mTimer = new Timer(); 
    	}
    	if(mTimerTask == null){
    		mTimerTask = new HeartbeatTimerTask();
    	}
    	
    	if(mTimer != null && mTimerTask != null){
    		mTimer.schedule(mTimerTask, Constant.HEAT_ITME * 1000, Constant.HEAT_ITME * 1000);
    	}
    	
    }
    
    private class HeartbeatTimerTask extends TimerTask{  
    	@Override  
    	public void run() {  
    		if(checkConnected()){
    			Thread current = Thread.currentThread();  
    			long threadId = current.getId();
    			Log.d(TAG, "threadId = "+threadId);
    			sendHeartBeat();
    		}else{
    			Log.d(TAG, "app not connected");
    		}
    	}  
    }
    
	public void sendHeartBeat() {
		EventPacket st = new EventPacket(CommandResource.EVENT_CLIENT_HEART_BEAT);
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
        Log.d(TAG, ",sendHeartBeat:"+Arrays.toString(mm));
        st.setBody(mm);
        
        CommandSend.send(st);
	}
   
	@Override
	public void processEventPacket(EventPacket message) {
		short cmdType = message.getCmdType();
		int dataLen = message.getDataLength();
		byte[] data = message.getBody(); 
		Log.d(TAG, "processReplyPacket message="+message.toString());
		Log.d(TAG, "processReplyPacket cmdType="+cmdType+"dataLen="+dataLen);
		Log.d(TAG, "processReplyPacket data="+StringUtil.printHexString(data, data.length));
		switch (cmdType) {
		case CommandResource.EVENT_WIFI_HEART_BEAT:
			Log.d(TAG, "processReplyPacket EVENT_WIFI_HEART_BEAT");
			break;
		
		default:
			break;
		}
	}

	@Override
	public void processReplyPacket(ReplyPacket message) {
		short cmdType = message.getCmdType();
		int dataLen = message.getDataLength();
		byte[] data = message.getBody(); 
		byte status = data[0];
		Log.d(TAG, "processReplyPacket message="+message.toString());
		Log.d(TAG, "processReplyPacket cmdType="+cmdType+"dataLen="+dataLen+"status="+status);
		switch (cmdType) {
		case CommandResource.CLIENT_BIND_SERVER:

			break;
		case CommandResource.SYS_GET_SYSTEM_VOLUME:
			int volume = data[1];
			Log.d(TAG, "processReplyPacket SYS_GET_SYSTEM_VOLUME volume ="+volume);
			break;
			
		default:
			break;
		}
	}
}

package com.protruly.minaclient;

import android.util.Log;

import com.communication.client.constant.Constant;
import com.communication.client.impl.CommandManger;
import com.communication.client.model.EventPacket;
import com.communication.client.model.SentPacket;
import com.communication.client.session.SessionManager;

public class CommandSend {
	private static final String TAG = CommandManger.TAG;

	public static void send(SentPacket st){
    	// encode
		if(SessionManager.getInstance().getSession(Constant.CIM_SERVER_PORT_SEND)  != null){
			Log.d(TAG, " send CIM_SERVER_PORT_SEND");
			SessionManager.getInstance().getSession(Constant.CIM_SERVER_PORT_SEND).write(st);
		}
    }
	
	public static void send(EventPacket st){
    	// encode
		if(SessionManager.getInstance().getSession(Constant.CIM_SERVER_PORT_EVENT)  != null){
			Log.d(TAG, "send CIM_SERVER_PORT_EVENT");
			SessionManager.getInstance().getSession(Constant.CIM_SERVER_PORT_EVENT).write(st);
		}
    }
    
}

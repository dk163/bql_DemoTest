package com.communication.client.handler;

import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.communication.client.constant.Constant;
import com.communication.client.impl.CommandManger;
import com.communication.client.model.EventPacket;
import com.communication.client.model.ReplyPacket;
import com.communication.client.session.CSession;
import com.communication.client.session.SessionManager;

import android.util.Log;

/**
 * 服务端反馈信息的入口，所有反馈信息都首先经过它分发处理
 */
public class CIoHandler extends IoHandlerAdapter {

	public void sessionCreated(IoSession session) throws Exception {
		int port = ((InetSocketAddress)session.getLocalAddress()).getPort();
		Log.d(CommandManger.TAG, "sessionCreated()... from id:" + session.getId() + ",port:" + port);
		CSession wrapperSession = new CSession(session);
		SessionManager.getInstance().addSession(port, wrapperSession);
	}

	public void sessionOpened(IoSession session) throws Exception {
		int port = ((InetSocketAddress)session.getLocalAddress()).getPort();
		Log.d(CommandManger.TAG, "sessionOpened()... from id:" + session.getId() + ",port:" + port);
		CSession cs = SessionManager.getInstance().getSession(Constant.CIM_SERVER_PORT_EVENT);
		if (cs != null && session.getId() == cs.getId()) {
			CommandManger.getInstance().startHeartBeat();
		}
	}

	public void messageReceived(IoSession ios, Object message) throws Exception {
		Log.d(CommandManger.TAG,"messageReceived: " + ios.getId() + ";" + message);
		Log.d(CommandManger.TAG,"messageReceived " + SessionManager.getInstance().getSession(Constant.CIM_SERVER_PORT_EVENT));
		
		if (null != SessionManager.getInstance().getSession(Constant.CIM_SERVER_PORT_EVENT) &&
				ios.getId() == SessionManager.getInstance().getSession(Constant.CIM_SERVER_PORT_EVENT).getId()) {
			CommandManger.getInstance().processEventPacket((EventPacket)message);
		} else {
			CommandManger.getInstance().processReplyPacket((ReplyPacket)message);
		}
	}

	public void sessionClosed(IoSession session) throws Exception {
		Log.d(CommandManger.TAG, "sessionClosed() from " + session.getRemoteAddress() + ";id: " + session.getId());
		int port = ((InetSocketAddress)session.getLocalAddress()).getPort();
		SessionManager.getInstance().closeSession(port);
	}

	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		Log.d(CommandManger.TAG,"sessionIdle: ");
		if (session.getId() == SessionManager.getInstance().getSession(Constant.CIM_SERVER_PORT_EVENT).getId()) {
			//CommandManger.getInstance().sendHeartBeat();
		}
	}

	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		Log.e(CommandManger.TAG, "exceptionCaught() from " + session.getRemoteAddress() + ";id: " + session.getId(), cause);
	}

	public void messageSent(IoSession session, Object message) throws Exception {
		Log.e(CommandManger.TAG, "messageSent() from " + session.getRemoteAddress() + ";id: " + session.getId());
	}
}
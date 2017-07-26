package com.communication.client.filter;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import android.util.Log;

import com.communication.client.constant.Constant;
import com.communication.client.impl.CommandManger;
import com.communication.client.impl.ParserCommand;
import com.communication.client.impl.ParserEventCommand;
import com.communication.client.session.SessionManager;


/**
 *  客户端接收消息解码，可在此解密消息
 */
public class ClientMessageDecoder extends ProtocolDecoderAdapter {	
	private ParserCommand mReplyPacketParser = new ParserCommand();
	private ParserEventCommand mEventPacketParser = new ParserEventCommand();
	
	@Override
	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		if (null != SessionManager.getInstance().getSession(Constant.CIM_SERVER_PORT_SEND) &&
			session.getId() == SessionManager.getInstance().getSession(Constant.CIM_SERVER_PORT_SEND).getId()) {
			Log.d(CommandManger.TAG, "send decode Hex dump:" + in.getHexDump());
			mReplyPacketParser.parser(in, out);
		}else if (null != SessionManager.getInstance().getSession(Constant.CIM_SERVER_PORT_EVENT) &&
			session.getId() == SessionManager.getInstance().getSession(Constant.CIM_SERVER_PORT_EVENT).getId()) {
			Log.d(CommandManger.TAG, "event decode Hex dump:" + in.getHexDump());
			mEventPacketParser.parser(in, out);
		}
	}
}

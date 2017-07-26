package com.communication.client.model;

import org.apache.mina.core.buffer.IoBuffer;

import com.communication.client.constant.Constant;

/**
 * 请求应答对象
 *
 */
public class ReplyPacket extends Packet {
	private static final long serialVersionUID = 3813995891591627286L;

	public ReplyPacket() {
		headerLength = Constant.REPLY_PACKET_HEADER_LENGTH;
		packageIdentify = Constant.REPLAY_PACKEY_IDENTIFY;
	}
	
	@Override
	public void parserHeader(IoBuffer buf) {
		// 一定要先调用父类的
		super.parserHeader(buf);
		//errorCode = buf.get();
	}
	
	@Override
	public void setBody(byte[] body) {
		super.setBody(body);
		dataLength = body.length;
	}
}

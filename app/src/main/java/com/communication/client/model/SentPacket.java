package com.communication.client.model;

import com.communication.client.constant.Constant;
import com.communication.client.utils.AtomicIntegerUtil;

/**
 * 请求结构
 */
public class SentPacket extends Packet {
	private static final long serialVersionUID = 5623570907146242430L;

	public SentPacket() {
		headerLength = Constant.SENT_PACKET_HEADER_LENGTH;
		packageIdentify = Constant.SEND_PACKEY_IDENTIFY;
	}
	
	public SentPacket(short cmdType) {
		headerLength = Constant.SENT_PACKET_HEADER_LENGTH;
		packageIdentify = Constant.SEND_PACKEY_IDENTIFY;
		this.cmdType = cmdType;
		seqNum = (short)AtomicIntegerUtil.getIncrementID();
	}
	
	@Override
	public void setBody(byte[] body) {
		super.setBody(body);
		dataLength = body.length;
	}
}

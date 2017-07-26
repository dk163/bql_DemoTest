package com.communication.client.model;

import com.communication.client.constant.Constant;
import com.communication.client.utils.AtomicIntegerUtil;

public class EventPacket extends Packet {
	private static final long serialVersionUID = -2374002940554866004L;

	public EventPacket() {
		headerLength = Constant.EVENT_PACKET_HEADER_LENGTH;
		packageIdentify = Constant.EVENT_PACKEY_IDENTIFY;
	}
	
	public EventPacket(short cmdType) {
		headerLength = Constant.EVENT_PACKET_HEADER_LENGTH;
		packageIdentify = Constant.EVENT_PACKEY_IDENTIFY;
		
		this.cmdType = cmdType;
		seqNum = (short)AtomicIntegerUtil.getIncrementID();
	}
	
	@Override
	public void setBody(byte[] body) {
		super.setBody(body);
		dataLength = body.length;
	}
}

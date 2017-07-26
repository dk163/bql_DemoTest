package com.communication.client.handler;

import com.communication.client.model.EventPacket;
import com.communication.client.model.ReplyPacket;

public interface CResponseHandler {
	public abstract void processEventPacket(EventPacket message);
	public abstract void processReplyPacket(ReplyPacket message);
}
package com.communication.client.filter;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.communication.client.impl.CommandManger;
import com.communication.client.model.Packet;
import com.communication.client.utils.StringUtil;

import android.util.Log;

/**
 * 服务端发送消息前编码，可在此加密消息
 */
public class ClientMessageEncoder extends ProtocolEncoderAdapter {
	
	@Override
	public void encode(IoSession iosession, Object message, ProtocolEncoderOutput out) throws Exception {
		Log.d(CommandManger.TAG,"ServerMessageEncoder iosession id:" + iosession.getId() + ";" + message);
		
		if (message instanceof Packet) {
			Packet rp = (Packet) message;
			
			IoBuffer buff = IoBuffer.allocate(rp.getTotalLength());
			buff.order(ByteOrder.LITTLE_ENDIAN);
			
			if (0 < rp.getHeaderLength()) {
				buff.put(rp.getPackageIdentify());
				buff.put(rp.getPackageEncrypt());
				buff.put(rp.getPackageVersion());
				buff.putShort(rp.getCmdType());
				buff.putUnsignedShort(rp.getDataLength());
				buff.putShort(rp.getSeqNum());
			}
			
			if (0 < rp.getDataLength()) {
				buff.put(rp.getBody());
			}
			if (0 < rp.getHeaderLength()) {
				byte crc = StringUtil.crc(rp.getCrcData());
				buff.put(crc);
			}
			
			buff.flip();
			out.write(buff);
			
			Log.d(CommandManger.TAG, "encoder Hex dump:" + buff.getHexDump());
		}
	}
}

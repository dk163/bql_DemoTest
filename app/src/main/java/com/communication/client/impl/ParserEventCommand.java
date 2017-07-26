package com.communication.client.impl;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import android.util.Log;

import com.communication.client.constant.Constant;
import com.communication.client.model.EventPacket;
import com.communication.client.model.Packet;
import com.communication.client.utils.StringUtil;

public class ParserEventCommand {
	// 临时缓存
	private IoBuffer mBuff = IoBuffer.allocate(1024).setAutoExpand(true);

	// 为false说明是不完整的包
	private boolean mIsPacketSplicing = false;

	// 文件总长度
	private int mTotalLength = 0;
	
	public synchronized void parser(IoBuffer ib, ProtocolDecoderOutput arg2) {
		boolean bHasFullPacket = parserInternal(ib);
		Log.d(CommandManger.TAG,"bHasFullPacket:" + bHasFullPacket);
		while(bHasFullPacket) {
			Packet pt = new EventPacket();
			pt.parserHeader(mBuff);
			pt.parserBody(mBuff);
			arg2.write(pt);
			
			// 检查还有没有完整包了
			bHasFullPacket = isHasFullPacket();
		}
	}

	private synchronized boolean parserInternal(IoBuffer ib) {
		if (null == ib || 0 >= ib.limit()) {
			return false;
		}
		
		// 先读取长度看一下这个是不是完整的包
		if (!mIsPacketSplicing) {
			// 指令类型 + 数据总长度
			if (8 > ib.limit()) {
				throw new IllegalArgumentException("data error");
			}
			
			ib.order(ByteOrder.LITTLE_ENDIAN);
			
			ib.mark();

			// 先跳过指令类型
			ib.skip(5);

			// 获取总长度
			
			byte[] dataLen = new byte[2];
			ib.get(dataLen, 0, 2);
			mTotalLength = StringUtil.bytesToInt(dataLen, 0) + Constant.EVENT_PACKET_HEADER_LENGTH + 1;
			//mTotalLength = ib.getInt();
			if (mTotalLength > ib.limit() - ib.position()) {
				mIsPacketSplicing = true;
			}
			
			// 恢复数据到标记位置
			ib.reset();
		}
		
		//LogUtils.d("buffer1:" + ib.toString());
		
		// 保存到缓存BUF中
		mBuff.put(ib);
		
		//LogUtils.d("buffer2:" + mBuff.toString());

		// 判断是否读完了
		if (!mIsPacketSplicing || mBuff.position() >= mTotalLength) {
			// 转成读的模式
			mBuff.flip();
			
			//LogUtils.d("buffer3:" + mBuff.toString());
			
			mIsPacketSplicing = false;
			mTotalLength = 0;
			return true;
		}
		return false;
	}
	
	public boolean isHasFullPacket() {
		//LogUtils.d("check full packet");
		
		// 如果缓存里还有数据要继续把完整的包解析出来
		if (mBuff.hasRemaining()) {
			//LogUtils.d("buffer6:" + mBuff.toString());
			
			// 先把剩下的数据临时保存下来
			IoBuffer ioBufferRemaining = mBuff.duplicate();
			
			// 把缓冲清掉，以方便存放上面的数据
			mBuff.clear();
			
			//LogUtils.d("ioBufferRemaining:" + ioBufferRemaining.toString());
			
			// 使用剩下的数据去解析
			return parserInternal(ioBufferRemaining);
		} else {
			reset();
		}
		
		return false;
	}
	
	public IoBuffer getBuf() {
		return mBuff;
	}
	
	public void reset() {
		//LogUtils.d("buffer4:" + mBuff.toString());
		
		// 解决粘包
		mBuff.compact();
		
		//LogUtils.d("buffer5:" + mBuff.toString());	
	}
}

package com.communication.client.model;

import java.io.Serializable;
import java.nio.ByteOrder;
import java.util.Arrays;

import org.apache.mina.core.buffer.IoBuffer;

import android.util.Log;

import com.communication.client.constant.Constant;
import com.communication.client.impl.CommandManger;
import com.communication.client.utils.StringUtil;

public abstract class Packet implements Serializable {
	private static final long serialVersionUID = 3622111332510104648L;

	/**
	 * Packet:
	 * 
	 * identiey:      1bytes
	 * flag:          2byte
	 * RequestID:     2bytes
	 * DataLength:    2bytes
	 * Sequence:      2bytes
	 * Data:
	 * crc:           1bytes
	 * 
	 */
	
	// 不同类型的包的文件头是不一样的
	protected int headerLength;
	
	// 包头起始字节
	protected byte packageIdentify;
	
	// 包头版本号
	protected byte packageVersion;
	
	// 是否加密
	protected byte packageEncrypt;
	
	// 指令类型 
	protected short cmdType;
	
	// 数据内容长度
	protected int dataLength;
	
	// 发送和接收相对应的序列号
	protected short seqNum;
	
	// CRC
	protected byte crc;
	
	// 错误码
	protected byte errorCode;
	
	// 数据内容
	private byte[] body;

	private byte[] uniqueId;
	
	public Packet() {
		// 用最小长度初始化一下
		headerLength = Constant.EVENT_PACKET_HEADER_LENGTH;
		packageVersion = Constant.PACKEY_VERSION;
		packageEncrypt = Constant.PACKEY_ENCRYPT;
		// 暂时默认为0
		crc = 0;
	}
	
	public void parserHeader(IoBuffer buf) {
		if (headerLength > buf.limit()) {
			throw new IllegalArgumentException("data error");
		}
		
		buf.order(ByteOrder.LITTLE_ENDIAN);
		packageIdentify = buf.get();
		packageEncrypt = buf.get();
		packageVersion = buf.get();
		cmdType = buf.getShort();
		dataLength = buf.getUnsignedShort();
		seqNum = buf.getShort();
	}
	
	public void parserBody(IoBuffer buf) {
		if (0 >= dataLength+1) {
			return;
		}
		
		body = new byte[dataLength];
		buf.get(body, 0, dataLength);
		crc = buf.get();
	}
	
	public byte getPackageIdentify() {
		return packageIdentify;
	}

	public void setPackageIdentify(byte packageIdentify) {
		this.packageIdentify = packageIdentify;
	}
	
	public byte getPackageVersion() {
		return packageVersion;
	}

	public void setPackageVersion(byte version) {
		packageVersion = version;
	}
	
	public byte getPackageEncrypt() {
		return packageEncrypt;
	}

	public void setPackageEncrypt(byte encrypt) {
		this.packageEncrypt = packageEncrypt;
	}
	
	public int getHeaderLength() {
		return headerLength;
	}

	public void setHeaderLength(int headerLength) {
		this.headerLength = headerLength;
	}
	
	public short getCmdType() {
		return cmdType;
	}

	public void setCmdType(short cmdType) {
		this.cmdType = cmdType;
	}

	public int getDataLength() {
		return dataLength;
	}

	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}

	public short getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(short seqNum) {
		this.seqNum = seqNum;
	}

	public byte getCRC() {
		return crc;
	}
	
	public void setCRC(byte crc) {
		this.crc = crc;
	}
	
	public byte getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(byte errorCode) {
		this.errorCode = errorCode;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}
	
	public int getTotalLength() {
		if (headerLength == 0) {
			return dataLength;
		}
		return headerLength + dataLength + 1;
	}
	
	public byte[] getCrcData() {
		IoBuffer buff = IoBuffer.allocate(100).setAutoExpand(true);
		buff.put(packageEncrypt);
		buff.put(packageVersion);
		buff.putShort(cmdType);
		buff.putUnsignedShort(dataLength);
		buff.putShort(seqNum);
		if (dataLength > 0) {
			buff.put(body);
		}
		
        buff.flip();
        
        byte[] packageDate = new byte[buff.limit()];
        
        buff.get(packageDate);
        buff.clear();
		
		return packageDate;
	}
	
	public byte[] getAllData() {
		
		IoBuffer buff = IoBuffer.allocate(100).setAutoExpand(true);
		buff.put(packageIdentify);
		buff.put(packageEncrypt);
		buff.put(packageVersion);
		buff.putShort(cmdType);
		buff.putUnsignedShort(dataLength);
		buff.putShort(seqNum);
		if (dataLength > 0) {
			buff.put(body);
		}
		
		buff.put(crc);
		
        buff.flip();
        
        byte[] packageDate = new byte[buff.limit()];
        
        buff.get(packageDate);
        buff.clear();
		
		return packageDate;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[Identify=" + packageIdentify + ",encrypt="+ packageEncrypt +",version=" + packageVersion 
				+ ", cmdType=" + cmdType + ",headerLength=" + headerLength + ", dataLength=" + dataLength + 
				", seqNum=" + seqNum + ", crc=" + crc + ", errorCode="
				+ errorCode + ", body=" + Arrays.toString(body) + "]";
	}
}

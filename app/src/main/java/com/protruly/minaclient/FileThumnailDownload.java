package com.protruly.minaclient;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import org.apache.mina.core.buffer.IoBuffer;

public class FileThumnailDownload {
	// 每个文件结构长度
	private static final int FILE_HANDLE_FIX_LEN = 9;
	
	// 保存文件
	private FileChannel mFileChannel = null;
	private File mSaveFile = null;
	
	private long mFileSrcLength = 0;
	
	private int WRITE_SIZE = 1024*1024;
	
	// 调试时打开
	//private long mCurrentLength = 0;
	
	// 用128K来存储应该够了
	
	// 下载文件同步，因为同时只能下载一个文件
	private Object mSyncLock = new Object();
	
	// 文件头有没有读取
	private boolean mAlreadyRead = false;
	
	private FileOutputStream fileOutputStream;
	
	private int curLen;
	private int totalLen;

	// 这个主要是用于调试作用，查看加了那些冗余数据后的总长度
	private long mCurrentLength = 0;

	// 480K包的索引
	private int m480KUnitIndex = 0;

	// 预先计算出一共有几个480K，最后一个包可能不是刚好480K
	private int mTotal480KUnitSize = 0;

	// 预先计算出最后一个包的长度
	private int mLast480KUnitLen = 0;

	// 先把32K单独先写
	private boolean mIs32KWritten = false;
	private static final int FIRST_32K_DATA_LEN = 32 * 1024;

	// 数据段的长度为480K
	private static final int FILE_480K_UNIT_LEN = 1024 * 1024;

	// 用两个缓冲区进行交换
	private IoBuffer mBuffer = IoBuffer.allocate(1024 * 1024).setAutoExpand(true);
	
	private boolean isFirstWrite = true;
	private boolean isFirstWriteFile = true;
	
	public FileThumnailDownload(String filename,int size){
		try {
			//mFileChannel = new FileOutputStream(filename,true).getChannel();
			mFileChannel = new FileOutputStream(filename).getChannel();
			mFileSrcLength = size;
			/*
			m480KUnitIndex = 0;
			mTotal480KUnitSize = (int) ((mFileSrcLength - FIRST_32K_DATA_LEN) / FILE_480K_UNIT_LEN);
			mLast480KUnitLen = (int) ((mFileSrcLength - FIRST_32K_DATA_LEN) % FILE_480K_UNIT_LEN);
			System.out.println("mTotal480KUnitSize:"+mTotal480KUnitSize);
			System.out.println("mLast480KUnitLen:"+mLast480KUnitLen);
			*/
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void write(IoBuffer buf) {
		mCurrentLength += buf.limit();
		mBuffer.put(buf);
		
		if (isFirstWrite) {
			mCurrentLength -= 43; // first package contaions 43 head data
			isFirstWrite = false;
		}
		
		System.out.println("mCurrentLength:"+mCurrentLength);
		if (mCurrentLength == mFileSrcLength) {
			System.out.println("wirte over");
			writeFile();
			return;
		}
		
		if (mBuffer.position() >= FILE_480K_UNIT_LEN) {
			writeFile();
		}
		
	}
	
	public void writeFile() {
		try {
			writeToFile();
		} catch (Exception e) {
			System.out.println("e.getMessage():"+e.getMessage());
		}
	}
	
	private void writeToFile() throws IOException {
		// 转成读的模式
		mBuffer.flip();
		
		if (isFirstWriteFile) {
			isFirstWriteFile = false;
			byte[] dst = new byte[43];
			mBuffer.get(dst, 0, 43);// first package contaions 43 head data
		}
		
		// 把数据写入
		int size = mFileChannel.write(mBuffer.buf());
		System.out.println("wirte size:" + size);
		mBuffer.clear();
		System.out.println("mCurrentLength:"+mCurrentLength);
	}
	/*
	public void write(IoBuffer buf) {
		System.out.println("limit:"+buf.limit());
		mBuffer.clear();
		// 写入数据
		mBuffer.put(buf);
		
		try {
			writeToFile();
		} catch (Exception e) {
			System.out.println("e.getMessage():"+e.getMessage());
		}
	}
	private void writeToFile() throws IOException {
		// 转成读的模式
		mBuffer.flip();
		
		// 把数据写入
		int size = mFileChannel.write(mBuffer.buf());
		System.out.println("wirte size:" + size);
	}
	*/
	
	/*
	public void write(IoBuffer buf) {
		
		mCurrentLength += buf.limit();
		System.out.println("mCurrentLength:"+mCurrentLength);
		// LogUtils.d("current length:" + mCurrentLength + ";source length:" + mFileSrcLength);

		// LogUtils.e("mBuffer1:" + mBuffer.toString());
		
		// 写入数据
		mBuffer.put(buf);

		// LogUtils.e("mBuffer2:" + mBuffer.toString());

		try {
			if (!mIs32KWritten) {
				System.out.println("mBuffer.position():"+mBuffer.position());
				if (mBuffer.position() >= FIRST_32K_DATA_LEN) {
					mIs32KWritten = true;
					System.out.println("mIs32KWritten:"+true);
					// 把32K写入
					writeToFile(FIRST_32K_DATA_LEN);
				}
			} else {
				System.out.println("mBuffer.position():"+mBuffer.position());
				// 相等说明是最后一个包，而且后面还有不到一个包的数据
				if (m480KUnitIndex == mTotal480KUnitSize) {
					// 一定要相等，不然数据数据就不对
					if (mBuffer.position() == mLast480KUnitLen) {
						// 把剩余数据写入
						writeToFile(mLast480KUnitLen);

						closeFile();
					}
				} else {
					if (mBuffer.position() >= FILE_480K_UNIT_LEN) {
						m480KUnitIndex++;

						// 把480K写入
						writeToFile(FILE_480K_UNIT_LEN);

						// 最后一个有可能会出现整包没有数据，或者有剩余数据在这个包中了
						if (m480KUnitIndex == mTotal480KUnitSize) {
							// 刚好没有剩余数据
							if (0 == mLast480KUnitLen) {
								closeFile();
							} else if (mBuffer.position() == mLast480KUnitLen) { // 有剩余数据
								// 把剩余数据写入
								writeToFile(mLast480KUnitLen);

								closeFile();
							}
						}
					}
				}
			}
		} catch (Exception e) {
		}
	}
*/
	private void writeToFile(int dataLen) throws IOException {
		// 转成读的模式
		mBuffer.flip();

		// LogUtils.e("mBuffer3:" + mBuffer.toString());

		// 把数据写入
		mFileChannel.write(ByteBuffer.wrap(mBuffer.array()), dataLen);
		
		mBuffer.skip(dataLen);

		// LogUtils.e("mBuffer4:" + mBuffer.toString());

		// 有一种特殊情况就是刚好读完没有剩余数据，但是文件还得继续读，
		// 可是这个时候如果加上buf.hasRemaining()来决断的话，POSTION是不能恢复的
		mBuffer.compact();

		// LogUtils.e("mBuffer5:" + mBuffer.toString());
	}
	
/*
	private void closeFile() throws IOException {
		mFos.flush();
		mFos.close();
		mBuffer.clear();

		// LogUtils.e("mBuffer6:" + mBuffer.toString());

		mFileDownloadHandle.finish(mSaveFile.getName());

		LogUtils.d("file last length:" + mSaveFile.length() + ";name:" + mSaveFile.getName());

		mLock.lock();
		try {
			mDownloadCondition.signalAll();
		} catch (Exception e) {
			LogUtils.e(e.getMessage());
		} finally {
			mLock.unlock();
		}
	}
	*/
	/*
	public void write(IoBuffer buf) {
		System.out.println("limit:"+buf.limit());
		
		curLen += buf.limit();
		//mBuffer.clear();
		// 写入数据
		mBuffer.put(buf);
		
		//if (512 * 1024 == mBuffer)
		

		try {
			
			if (mBuffer.position() >= WRITE_SIZE) {
				writeToFile(WRITE_SIZE);
			}
			else {
				
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void writeToFile(int dataLen) throws IOException {

		// 转成读的模式
		mBuffer.flip();

		// LogUtils.e("mBuffer3:" + mBuffer.toString());

		// 把数据写入
		mFileChannel.write(mBuffer.buf(), dataLen);
		//mFileChannel.write(mBuffer.array(), headerLen, dataLen);
		mBuffer.skip(dataLen);

		// LogUtils.e("mBuffer4:" + mBuffer.toString());

		// 有一种特殊情况就是刚好读完没有剩余数据，但是文件还得继续读，
		// 可是这个时候如果加上buf.hasRemaining()来决断的话，POSTION是不能恢复的
		mBuffer.compact();

		// LogUtils.e("mBuffer5:" + mBuffer.toString());
	}
	*/
	
	
	
	private void closeFile() throws IOException {
		mFileChannel.close();
		
		// 准备存储下个文件内容
		mBuffer.clear();
		
		synchronized (mSyncLock) {
			mSyncLock.notifyAll();	
		}
	}
}

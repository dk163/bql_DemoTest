package com.communication.client.handler;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import android.util.Log;
import com.communication.client.constant.Constant;
import com.communication.client.filter.ClientMessageCodecFactory;
import com.communication.client.impl.CommandManger;
import com.communication.client.session.CSession;
import com.communication.client.session.SessionManager;
import com.protruly.minaclient.ItemTest;
import com.protruly.minaclient.MulticastManager;

public class CNioSocketConnector {
	private static final String TAG = CommandManger.TAG;
    
    /** The connector */
    public IoConnector connector;
	private CIoHandler mClientHandler;
	
	public void connect(){
    	connector = new NioSocketConnector();
        connector.getSessionConfig().setReadBufferSize(1024);
 		//connector.getSessionConfig().setTcpNoDelay(true);
 		connector.getFilterChain().addLast("executor", new ExecutorFilter());
 		connector.getFilterChain().addLast("logger", new LoggingFilter());
 		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ClientMessageCodecFactory()));
 		// 消息核心处理器
 		mClientHandler = new CIoHandler();
 		connector.setHandler(mClientHandler);
 		// 设置链接超时时间
 		//connector.setConnectTimeoutCheckInterval(3000);
         
        try {
        	// send command
        	String serverIp = MulticastManager.getServerIp();
        	if(serverIp == null){
            	serverIp = "192.168.43.1";
        	}
        	Log.d(TAG,"TcpClient send serverIp = "+serverIp);
        	InetSocketAddress isa = new InetSocketAddress(serverIp, Constant.CIM_SERVER_PORT_SEND);
        	ConnectFuture cf = connector.connect(isa);
        	cf.awaitUninterruptibly();
        	SessionManager.getInstance().addSession(Constant.CIM_SERVER_PORT_SEND, new CSession(cf.getSession()));
        	Log.d(TAG,"send CIM_SERVER_PORT_SEND socket ok");
 	
        	/*// event command
        	isa = new InetSocketAddress(InetAddress.getByName(serverIp), Constant.CIM_SERVER_PORT_EVENT);
        	cf = connector.connect(isa);
        	cf.awaitUninterruptibly();
        	SessionManager.getInstance().addSession(Constant.CIM_SERVER_PORT_EVENT, new CSession(cf.getSession()));
        	Log.d(TAG,"event CIM_SERVER_PORT_EVENT socket ok");

        	// download file
        	isa = new InetSocketAddress(InetAddress.getByName(serverIp), Constant.CIM_SERVER_PORT_FILE);
        	cf = connector.connect(isa);
        	cf.awaitUninterruptibly();
        	SessionManager.getInstance().addSession(Constant.CIM_SERVER_PORT_FILE, new CSession(cf.getSession()));
        	Log.d(TAG,"download file CIM_SERVER_PORT_FILE socket ok");

        	// download file thumnail
        	isa = new InetSocketAddress(InetAddress.getByName(serverIp), Constant.CIM_SERVER_PORT_THUMNAIL);
        	cf = connector.connect(isa);
        	cf.awaitUninterruptibly();
        	SessionManager.getInstance().addSession(Constant.CIM_SERVER_PORT_THUMNAIL, new CSession(cf.getSession()));
        	Log.d(TAG,"download file thumnail CIM_SERVER_PORT_THUMNAIL socket ok");*/
 		
        }catch (Exception e){
        	Log.e(TAG,"Exception :", e); 
        }
        
        ItemTest.getInstance().bindServer();
    }
}

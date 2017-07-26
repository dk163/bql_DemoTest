package com.protruly.minaclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

public class MulticastManager {
	private final static String TAG = "MulticastManager";
	public final static int SERVER_MONITOR_PORT=7451;    
	//public final static int CLIENT_MONITOR_PORT = 8600;
    public final static String BROADCAST_IP="224.0.0.1";
    private final static int START_CONNECT=1;
    private Handler mMulticastHandler;  
	private static HashMap<String, String> mServiceMsgMap = new HashMap<String, String>();
	private MainActivity mAcitivty;
	private HandlerThread mHandlerThread;
	public MulticastManager(MainActivity acitivty){
		mAcitivty = acitivty;
		mHandlerThread = new HandlerThread("MulticastManager");  
		mHandlerThread.start();  
	}
	
	public MulticastManager(){
		mHandlerThread = new HandlerThread("MulticastManager");  
		mHandlerThread.start();  
	}
	
	public void startMulticast(){
		 mMulticastHandler =  new Handler(mHandlerThread.getLooper());  
	     mMulticastHandler.post(mRunnable); 
	}
	
	public static String getServerIp(){
		return mServiceMsgMap.get("deviceIp");
	}
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case START_CONNECT:
				mAcitivty.startConnectServer();
				break;

			default:
				break;
			}
		}
		
	};
	
	private Runnable mRunnable = new Runnable() {    
        
        public void run() {  
            Log.i(TAG, "run...");  
            try {  
                sendMultiBroadcast();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
  
        private String packageMultiSocketMsg(){
        	JSONObject param = new JSONObject();
    		try {
    			String ip = getHostIP();
				param.put("clientid", "test");
				param.put("devname", "k28");
				param.put("hostIp", ip);
			} catch (JSONException e) {
				e.printStackTrace();
			}
    	
    		return  param.toString();
    	}
        
        private void parseMultiSocketMsg(String msg){
        	JSONObject json;
			try {
				json = new JSONObject(msg);
        	   String name = json.optString("name");
        	   String value = json.optString("value");
        	   Log.d(TAG, "parseMultiSocketMsg name = "+name+"; value= "+value);
        	   mServiceMsgMap.put(name, value);
//				String clientId = json.optString("deviceid");
//				 mServiceMsgMap.put("deviceid", clientId);
//	           JSONArray array = json.getJSONArray("svclist");
//	           for(int i=0; i<array.length(); i++){
//	        	   JSONObject jsonObject = array.getJSONObject(i);
//	        	   String name = jsonObject.optString("name");
//	        	   String value = jsonObject.optString("value");
//	        	   Log.d(TAG, "parseMultiSocketMsg name = "+name+"; value= "+value);
//	        	   mServiceMsgMap.put(name, value);
//	           }
			} catch (JSONException e) {
				e.printStackTrace();
			} 
        }
        
        private void sendMultiBroadcast() throws IOException {  
            Log.i(TAG, "sendMultiBroadcast enter");  
            /* 
             * 实现多点广播时，MulticastSocket类是实现这一功能的关键，当MulticastSocket把一个DatagramPacket发送到多点广播IP地址， 
             * 该数据报将被自动广播到加入该地址的所有MulticastSocket。MulticastSocket类既可以将数据报发送到多点广播地址， 
             * 也可以接收其他主机的广播信息 
             */  
            MulticastSocket socket = new MulticastSocket(/*CLIENT_MONITOR_PORT*/SERVER_MONITOR_PORT);  
            //IP协议为多点广播提供了这批特殊的IP地址，这些IP地址的范围是224.0.0.0至239.255.255.255  
            InetAddress address = InetAddress.getByName(BROADCAST_IP);  
            Log.i(TAG, "sendMultiBroadcast address:" + String.valueOf(address.getAddress()));
            socket.setTimeToLive(1); //指定数据报发送到本地局域网
            /* 
             * 创建一个MulticastSocket对象后，还需要将该MulticastSocket加入到指定的多点广播地址， 
             * MulticastSocket使用jionGroup()方法来加入指定组；使用leaveGroup()方法脱离一个组。 
             */  
            socket.joinGroup(address);  
            socket.setLoopbackMode(true);
            /* 
             * 在某些系统中，可能有多个网络接口。这可能会对多点广播带来问题，这时候程序需要在一个指定的网络接口上监听， 
             * 通过调用setInterface可选择MulticastSocket所使用的网络接口； 
             * 也可以使用getInterface方法查询MulticastSocket监听的网络接口。 
             */  
            //socket.setInterface(address);  
              
            DatagramPacket packet;  
            //发送数据包  
            Log.i(TAG, "sendMultiBroadcast send packet"); 
            String sendMsg = packageMultiSocketMsg();
            Log.i(TAG, "sendMultiBroadcast  sendMsg= "+sendMsg); 
            byte[] buf = sendMsg.getBytes();  
            packet = new DatagramPacket(buf, buf.length, address, SERVER_MONITOR_PORT);
            socket.send(packet);  
              
            //接收数据  
            Log.i(TAG, "sendMultiBroadcast receiver packet");  
            byte[] rev = new byte[1024];  
            packet = new DatagramPacket(rev, rev.length/*, address, CLIENT_MONITOR_PORT*/);  
            socket.receive(packet); 
            String receiveMsg = new String(packet.getData()).trim();
            Log.i(TAG, "sendMultiBroadcast get data = " +receiveMsg);    //不加trim，则会打印出512个byte，后面是乱码  
            parseMultiSocketMsg(receiveMsg);
            //mHandler.sendEmptyMessage(START_CONNECT);
            //退出组播  
            socket.leaveGroup(address);  
            socket.close();  
        }    
            
    };    
    
    /** 
     * 获取ip地址 
     * @return 
     */  
    public static String getHostIP() {  
      
        String hostIp = null;  
        try {  
            Enumeration nis = NetworkInterface.getNetworkInterfaces();  
            InetAddress ia = null;  
            while (nis.hasMoreElements()) {  
                NetworkInterface ni = (NetworkInterface) nis.nextElement();  
                Enumeration<InetAddress> ias = ni.getInetAddresses();  
                while (ias.hasMoreElements()) {  
                    ia = ias.nextElement();  
                    if (ia instanceof Inet6Address) {  
                        continue;// skip ipv6  
                    }  
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {  
                        hostIp = ia.getHostAddress();  
                        break;  
                    }  
                }  
            }  
        } catch (SocketException e) {  
            Log.i(TAG, "SocketException");  
            e.printStackTrace();  
        }  
        return hostIp;  
      
    } 
}

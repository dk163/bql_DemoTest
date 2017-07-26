package com.protruly.minaclient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.communication.client.handler.CNioSocketConnector;
import com.communication.client.impl.CommandManger;
import com.communication.client.impl.CommandResource;
import com.communication.client.model.EventPacket;
import com.protruly.minaclient.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.app.Activity;

public class MainActivity extends Activity  implements Runnable, OnClickListener {
	
    private static String TAG = CommandManger.TAG;
    
    private TextView tv;
    private TextView eventtv;
	private ListView mTestListView;	

	private ItemTest ItemTestHandler;
	private Looper mLooper;
	private MulticastManager mMulticastManager;
	
	public String getTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");       
		Date curDate = new Date(System.currentTimeMillis());
		return formatter.format(curDate);       
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tv = (TextView)findViewById(R.id.id_tv);
        eventtv = (TextView)findViewById(R.id.id_tv_event); 
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTestListView = (ListView)findViewById(R.id.test_item_view);
        mTestListView.setOnItemClickListener(itemListener);

        List<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
        for(int i=0;i<Item.mTestItemList.length;i++) {
          HashMap<String, String> map = new HashMap<String, String>();
          map.put("itemTitle", Item.mTestItemList[i]);
          mylist.add(map);
        }

        //配置适配器
        SimpleAdapter adapter = new SimpleAdapter(this, 
         mylist,//数据源 
         R.layout.item_layout,//显示布局
         new String[] {"itemTitle"}, //数据源的属性字段
         new int[] {R.id.test_title}); //布局里的控件id
         mTestListView.setAdapter(adapter);
         
        //知道server端的ip时，进行通信
        //Thread thread = new Thread(this);  
        //thread.start();
         
        //当不知道server端的ip时，采用udp多点广播方式获取server 端ip，再进行通信
//        mMulticastManager = new MulticastManager(this);
//        mMulticastManager.startMulticast();
//       
		ItemTestHandler = ItemTest.getInstance();
		ItemTestHandler.setmAcitivty(MainActivity.this);
		//startConnectServer();
    }
    
    private OnItemClickListener itemListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Log.d(TAG, "position = "+position);
			ItemTestHandler.sendItemTestForIndex(position);
		}
	};
    
    public void startConnectServer(){
    	Log.d(TAG, "startConnectServer enter");
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				Log.d(TAG, "startConnectServer TcpClient connect");
				CNioSocketConnector connector = new CNioSocketConnector();
				connector.connect();
				//关闭客户端连接
		       // connector.connector.dispose(true); 
			}
		}).start();
    }
    
    @Override  
    public void run()  
    {  
    	//CNioSocketConnector connector = new CNioSocketConnector();
		//connector.connect();
		//关闭客户端连接
       // connector.connector.dispose(true); 
       
    }
	
	Handler mHandler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 1:
				tv.setText("COMMAND:" + (String)msg.obj);
				break;
			case 2:
				eventtv.setText("EVENT:"+(String)msg.obj);
				break;
			case 3:
				Log.d(TAG, "EventPacket");
				EventPacket st = new EventPacket(CommandResource.EVENT_CLIENT_HEART_BEAT);
				//send(st);
				mHandler.sendEmptyMessageDelayed(3, 5*1000);
				break;
			default:
				break;
			}
		};
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}
}

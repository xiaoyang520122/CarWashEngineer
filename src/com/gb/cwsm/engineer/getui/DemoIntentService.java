package com.gb.cwsm.engineer.getui;

import org.apache.http.message.BasicNameValuePair;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.Log;

import com.gb.cwsm.engineer.AppApplication;
import com.gb.cwsm.engineer.service.Music;
import com.gb.cwsm.engineer.utils.JsonHttpUtils;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTTransmitMessage;

/**
 * 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调, 如果注册了该服务, 则务必要在 AndroidManifest中声明, 否则无法接受消息<br>
 * onReceiveMessageData 处理透传消息<br>
 * onReceiveClientId 接收 cid <br>
 * onReceiveOnlineState cid 离线上线通知 <br>
 * onReceiveCommandResult 各种事件处理回执 <br>
 */
public class DemoIntentService extends GTIntentService {
	
	/**
     * 为了观察透传数据变化.
     */
    private static int cnt;
	
    public DemoIntentService() {

    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
    	String appid = msg.getAppid();
        String taskid = msg.getTaskId();
        String messageid = msg.getMessageId();
        byte[] payload = msg.getPayload();
        String pkg = msg.getPkgName();
        String cid = msg.getClientId();

        // 第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
        boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
        Log.d(TAG, "call sendFeedbackMessage = " + (result ? "success" : "failed"));

        Log.d(TAG, "onReceiveMessageData -> " + "appid = " + appid + "\ntaskid = " + taskid + "\nmessageid = " + messageid + "\npkg = " + pkg
                + "\ncid = " + cid);

        if (payload == null) {
            Log.e(TAG, "receiver payload = null");
        } else {
        	startService(new Intent(getApplicationContext(), Music.class));
            String data = new String(payload);
            sendevent(data);
            Log.d(TAG, "receiver payload = " + data);
            

            // 测试消息为了观察数据变化
            if (data.equals("收到一条透传测试消息")) {
                data = data + "-" + cnt;
                cnt++;
            }
            sendMessage(data, 0);
        }

        Log.d(TAG, "----------------------------------------------------------------------------------------------");
    }
    
    private void sendevent(String data) {
		try {
			JSONObject jo=new JSONObject(data);
			int code=Integer.valueOf(jo.getString("title"));
			switch (code) {
			case JsonHttpUtils.NEW_ORDER_PAYLOAD:
				postevent(code,jo.optString("content"));
				break;

			default:
				break;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void postevent(int code,String data) {
		EventBus.getDefault().post(new BasicNameValuePair(code+"", data));
	}

	@Override
    public void onReceiveClientId(Context context, String clientid) {
		EventBus.getDefault().register(this);
    	Log.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);
        sendMessage(clientid, 1);
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
    }
    
    private void sendMessage(String data, int what) {
    	Log.e(TAG, "onReceiveClientId -> " + "clientid = " + data);
    	Log.e("LONGING", "进入sendMessage = " +data);
        Message msg = Message.obtain();
        msg.what = what;
        msg.obj = data;
        AppApplication.sendMessage(msg);
    }
    
}
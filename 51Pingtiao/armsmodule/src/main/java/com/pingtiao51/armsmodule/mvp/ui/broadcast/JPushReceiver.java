package com.pingtiao51.armsmodule.mvp.ui.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.ActivityUtils;
import com.google.gson.Gson;
import com.jess.arms.utils.ArmsUtils;
import com.pingtiao51.armsmodule.mvp.model.entity.response.JpushPingtiaoInfo;
import com.pingtiao51.armsmodule.mvp.ui.activity.DianziJietiaoXiangqingActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.DianziShoutiaoXiangqingActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.ZhizhiJietiaoXiangqingActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.ZhizhiShoutiaoXiangqingActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

import static com.pingtiao51.armsmodule.mvp.ui.activity.DianziJietiaoXiangqingActivity.PING_TIAO_XIANG_QING;


/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class JPushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPushReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
//            Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...
                //获取RegistrationID唯一标识
                String rid = JPushInterface.getRegistrationID(ActivityUtils.getTopActivity());
                Log.d("Jpush", "rid = " + rid);

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                processCustomMessage(context, bundle);

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
                if (!TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    try {
                        JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                        Iterator<String> it = json.keys();
                        while (it.hasNext()) {
                            String myKey = it.next();
                            String value = json.optString(myKey);
                            if ("jsonStr".equals(myKey)) {
                                Gson gson = ArmsUtils.obtainAppComponentFromContext(ActivityUtils.getTopActivity()).gson();
                                JpushPingtiaoInfo info = gson.fromJson(value, JpushPingtiaoInfo.class);
//                                info.getNoteId();
//                                info.getNoteType();
                                gotoDianziJietiaoXiangqing(context,info.getNoteType(),info.getNoteId());
                            }
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "Get message extra JSON error!");
                    }
                }


                //打开自定义的Activity
/*                Intent i = new Intent(context, TestActivity.class);
                i.putExtras(bundle);
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                context.startActivity(i);*/

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {

        }

    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) throws JSONException {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.get(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {

    }


    private void gotoDianziJietiaoXiangqing(Context context,String type,int id) {
        Intent i = null;
        Bundle bundle = new Bundle();
        bundle.putInt(PING_TIAO_XIANG_QING,id);
        switch (type) {
            case "OWE_NOTE":
                 i = new Intent(context, DianziJietiaoXiangqingActivity.class);
                break;
            case "PAPER_OWE_NOTE":
                i = new Intent(context, ZhizhiJietiaoXiangqingActivity.class);
                break;
            case "PAPER_RECEIPT_NOTE":
                i = new Intent(context, ZhizhiShoutiaoXiangqingActivity.class);
                break;
            case "RECEIPT_NOTE":
                i = new Intent(context, DianziShoutiaoXiangqingActivity.class);
                break;
            default:
                break;
        }
        if(i == null){
            return; // 空保护
        }else {
            i.putExtras(bundle);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);
        }
    }
}
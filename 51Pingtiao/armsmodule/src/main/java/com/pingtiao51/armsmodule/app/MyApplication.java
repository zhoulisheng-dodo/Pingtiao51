package com.pingtiao51.armsmodule.app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.jess.arms.base.BaseApplication;
import com.jess.arms.utils.ArmsUtils;
import com.meituan.android.walle.WalleChannelReader;
import com.pingtiao51.armsmodule.BuildConfig;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.model.api.Api;
import com.pingtiao51.armsmodule.mvp.ui.helper.DynamicTimeFormat;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshInitializer;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import cn.jpush.android.api.JPushInterface;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

public class MyApplication extends BaseApplication {

    public final static String UMENG_APPKEY = "5ca4306861f564ab2a000a1e";

    static {
        //启用矢量图兼容
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        //设置全局默认配置（优先级最低，会被其他设置覆盖）
        SmartRefreshLayout.setDefaultRefreshInitializer(new DefaultRefreshInitializer() {
            @Override
            public void initialize(@NonNull Context context, @NonNull RefreshLayout layout) {
                //全局设置（优先级最低）
                layout.setEnableAutoLoadMore(true);
                layout.setEnableOverScrollDrag(false);
                layout.setEnableOverScrollBounce(true);
                layout.setEnableLoadMoreWhenContentNotFull(true);
                layout.setEnableScrollContentWhenRefreshed(true);
            }
        });
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                //全局设置主题颜色（优先级第二低，可以覆盖 DefaultRefreshInitializer 的配置，与下面的ClassicsHeader绑定）
                layout.setPrimaryColorsId(R.color.white, R.color.black_color_494949);

                return new ClassicsHeader(context).setTimeFormat(new DynamicTimeFormat("更新于 %s"));
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppCrashHandler.getInstance().init(this);
        initUmeng();
        initWeChat();
        initJpush();
        initBaseUrls();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    /**
     * 初始化common库
     * 参数1:上下文，不能为空
     * 参数2:【友盟+】 AppKey
     * 参数3:【友盟+】 Channel
     * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
     * 参数5:Push推送业务的secret
     */
    public static String sChannel;

    private void initUmeng() {
        //UMConfigure.setLogEnabled(true);
        String channel = BuildConfig.DEBUG ? "DEBUG" : WalleChannelReader.getChannel(this);
        sChannel = channel;
        UMConfigure.init(this, UMENG_APPKEY, channel, UMConfigure.DEVICE_TYPE_PHONE, null);
        // UMConfigure.setLogEnabled(true);
        // 选用LEGACY_AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.LEGACY_MANUAL);
        // 支持在子进程中统计自定义事件
        UMConfigure.setProcessEvent(true);
    }

    /**
     * 注册微信分享
     */

    private void initWeChat() {
        // 三个参数分别是上下文、应用的appId、是否检查签名（默认为false）
        IWXAPI mWxApi = WXAPIFactory.createWXAPI(this, "wxcdf3efdd7570a44a", true);
        // 注册
        mWxApi.registerApp(Api.WECHAT_APPKEY);
    }

    /**
     * 注册极光推送
     */
    private void initJpush() {
        JPushInterface.setDebugMode(!BuildConfig.DEBUG);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
    }

    /**
     * 配置baseUrl
     */
    public static final String BASE_URL2 = "BaseUrl2";
    public static void initBaseUrls(){
        RetrofitUrlManager.getInstance().putDomain(BASE_URL2,BuildConfig.BASE_URL2);
    }
}

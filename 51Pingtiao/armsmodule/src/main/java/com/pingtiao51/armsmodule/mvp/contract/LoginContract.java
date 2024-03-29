package com.pingtiao51.armsmodule.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.model.entity.response.LoginResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.WxLoginResponse;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/14/2019 10:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface LoginContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
            void sendCodeSuc(Object object);
            void loginSuc(LoginResponse loginResponse,String phoneNum);
            void changePswSuc(Object object);
            void registerSuc(LoginResponse loginResponse);
            void wxLoginSuc(WxLoginResponse response);

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         * 发送短信验证码
         */
        Observable<BaseJson<Object>> sendPhoneCode(String phoneNum,String type);

        /**
         * 验证码注册
         */
        Observable<BaseJson<LoginResponse>> vcodeRegister();

        /**
         * 验证码登录或者密码登录
         */
        Observable<BaseJson<LoginResponse>> codeOrPswLogin(boolean isCodeLogin,String psw,long phoneNum);

        /**
         * 重置密码
         */
        Observable<BaseJson<Object>> resetLoginPassword(long phoneNum,String code,String psw);

        /**
         * 微信登录
         */
        Observable<BaseJson<WxLoginResponse>> wxLogin(String code);
    }
}

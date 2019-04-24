package com.pingtiao51.armsmodule.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingtiaoXiangqingResponse;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/26/2019 15:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface ZhizhiShoutiaoXiangqingContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        public void onSucJietiaoXq(PingtiaoXiangqingResponse rep);
        public void onSucModifyPingtiao();
        public void onSucDownload(String savePath);
        public void onSucDelete();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseJson<PingtiaoXiangqingResponse>> getPingtiaoById(long id);
        Observable<BaseJson<Object>> modifyPingtiao(long noteid,List<String> addUrls);
        Observable<ResponseBody> downloadFile(String url);
        Observable<BaseJson<Object>> closeElectronicNote(long id);
    }
}

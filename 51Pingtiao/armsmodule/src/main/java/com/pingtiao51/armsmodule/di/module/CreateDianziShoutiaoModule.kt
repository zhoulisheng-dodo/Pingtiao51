package com.pingtiao51.armsmodule.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.pingtiao51.armsmodule.mvp.contract.CreateDianziShoutiaoContract
import com.pingtiao51.armsmodule.mvp.model.CreateDianziShoutiaoModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/16/2019 13:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建CreateDianziShoutiaoModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class CreateDianziShoutiaoModule(private val view: CreateDianziShoutiaoContract.View) {
    @ActivityScope
    @Provides
    fun provideCreateDianziShoutiaoView(): CreateDianziShoutiaoContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideCreateDianziShoutiaoModel(model: CreateDianziShoutiaoModel): CreateDianziShoutiaoContract.Model {
        return model
    }
}

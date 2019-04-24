package com.pingtiao51.armsmodule.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.pingtiao51.armsmodule.di.module.CreateJietiaoModule

import com.jess.arms.di.scope.ActivityScope
import com.pingtiao51.armsmodule.mvp.ui.activity.CreateJietiaoActivity


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/16/2019 13:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = arrayOf(CreateJietiaoModule::class), dependencies = arrayOf(AppComponent::class))
interface CreateJietiaoComponent {
    fun inject(activity: CreateJietiaoActivity)
}

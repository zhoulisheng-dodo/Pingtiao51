package com.pingtiao51.armsmodule.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.pingtiao51.armsmodule.di.module.MyModule;
import com.pingtiao51.armsmodule.mvp.contract.MyContract;

import com.jess.arms.di.scope.FragmentScope;
import com.pingtiao51.armsmodule.mvp.ui.fragment.MyFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/11/2019 15:15
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = MyModule.class, dependencies = AppComponent.class)
public interface MyComponent {
    void inject(MyFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyComponent.Builder view(MyContract.View view);

        MyComponent.Builder appComponent(AppComponent appComponent);

        MyComponent build();
    }
}
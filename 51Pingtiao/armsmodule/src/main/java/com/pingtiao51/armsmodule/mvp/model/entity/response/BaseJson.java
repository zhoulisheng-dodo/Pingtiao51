/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pingtiao51.armsmodule.mvp.model.entity.response;

import com.pingtiao51.armsmodule.mvp.model.api.Api;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * ================================================
 * 如果你服务器返回的数据格式固定为这种方式(这里只提供思想,服务器返回的数据格式可能不一致,可根据自家服务器返回的格式作更改)
 * 替换范型即可重用 {@link BaseJson}
 * <p>
 * Created by JessYan on 26/09/2016 15:19
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class BaseJson<T>  implements Serializable {
    private String code;
    private String message;
    protected  T data;
    public T getData() {
        return  data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BaseJson{" +
                "data=" + data +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    /**
     * 请求是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        if (Api.RequestSuccess.equals(code)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 是否token失效
     *
     * @return
     */
    public boolean isTokenValid() {
        if (Api.tokenValid.equals(code)) {
            return true;
        } else {
            return false;
        }
    }
}

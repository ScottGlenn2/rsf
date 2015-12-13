/*
 * Copyright 2008-2009 the original 赵永春(zyc@hasor.net).
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
package net.hasor.rsf.rpc.caller.remote;
import java.lang.reflect.Method;
import net.hasor.rsf.RsfBindInfo;
import net.hasor.rsf.RsfContext;
import net.hasor.rsf.RsfRequest;
import net.hasor.rsf.transform.protocol.RequestInfo;
/**
 * RSF请求
 * @version : 2014年10月25日
 * @author 赵永春(zyc@hasor.net)
 */
class RsfRequestFormRemote implements RsfRequest {
    private final RequestInfo     requestInfo;
    private final RemoteRsfCaller rsfCaller;
    private final RsfBindInfo<?>  bindInfo;
    private final Method          targetMethod;
    private final Class<?>[]      parameterTypes;
    private final Object[]        parameterObjects;
    //
    public RsfRequestFormRemote(RequestInfo requestInfo, RsfBindInfo<?> bindInfo, Method targetMethod, Object[] parameterObjects, RemoteRsfCaller rsfCaller) {
        this.requestInfo = requestInfo;
        this.bindInfo = bindInfo;
        this.targetMethod = targetMethod;
        this.parameterTypes = targetMethod.getParameterTypes();
        this.parameterObjects = parameterObjects;
        this.rsfCaller = rsfCaller;
    }
    @Override
    public String toString() {
        return "requestID:" + this.getRequestID() + " from Remote," + this.bindInfo.toString();
    }
    @Override
    public boolean isLocal() {
        return false;
    }
    @Override
    public Method getMethod() {
        return this.targetMethod;
    }
    @Override
    public Class<?>[] getParameterTypes() {
        return this.parameterTypes.clone();
    }
    @Override
    public Object[] getParameterObject() {
        return this.parameterObjects.clone();
    }
    @Override
    public RsfContext getContext() {
        return this.rsfCaller.getContext();
    }
    @Override
    public RsfBindInfo<?> getBindInfo() {
        return this.bindInfo;
    }
    @Override
    public long getRequestID() {
        return this.requestInfo.getRequestID();
    }
    @Override
    public String getSerializeType() {
        return this.requestInfo.getSerializeType();
    }
    @Override
    public long getReceiveTime() {
        return this.requestInfo.getReceiveTime();
    }
    @Override
    public int getTimeout() {
        int timeOut1 = requestInfo.getClientTimeout();
        int timeOut2 = this.bindInfo.getClientTimeout();
        return timeOut1 < timeOut2 ? timeOut1 : timeOut2;
    }
    @Override
    public String[] getOptionKeys() {
        return this.requestInfo.getOptionKeys();
    }
    @Override
    public String getOption(String key) {
        return this.requestInfo.getOption(key);
    }
    @Override
    public void addOption(String key, String value) {
        this.requestInfo.addOption(key, value);
    }
    @Override
    public void removeOption(String key) {
        this.requestInfo.removeOption(key);
    }
}
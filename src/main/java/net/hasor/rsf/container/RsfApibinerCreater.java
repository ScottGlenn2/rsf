/*
 * Copyright 2008-2009 the original author or authors.
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
package net.hasor.rsf.container;
import net.hasor.core.ApiBinder;
import net.hasor.core.BindInfo;
import net.hasor.core.binder.ApiBinderCreater;
import net.hasor.rsf.RsfApiBinder;
import net.hasor.rsf.RsfEnvironment;
import net.hasor.rsf.rpc.context.DefaultRsfEnvironment;

import java.io.IOException;
import java.util.List;
/**
 *
 * @version : 2016-12-16
 * @author 赵永春 (zyc@hasor.net)
 */
public class RsfApibinerCreater implements ApiBinderCreater {
    @Override
    public RsfApiBinder createBinder(ApiBinder apiBinder) throws IOException {
        RsfEnvironment rsfEnvironment = initAntGetEnvironment(apiBinder);
        return new InnerRsfApiBinder(apiBinder, rsfEnvironment);
    }
    private RsfEnvironment initAntGetEnvironment(ApiBinder apiBinder) throws IOException {
        List<BindInfo<RsfEnvironment>> rsfEnvList = apiBinder.findBindingRegister(RsfEnvironment.class);
        RsfEnvironment rsfEnv = null;
        if (rsfEnvList != null && !rsfEnvList.isEmpty()) {
            for (BindInfo<RsfEnvironment> info : rsfEnvList) {
                rsfEnv = (RsfEnvironment) info.getMetaData(RsfEnvironment.class.getName());
                if (rsfEnv != null) {
                    return rsfEnv;
                }
            }
        }
        rsfEnv = new DefaultRsfEnvironment(apiBinder.getEnvironment());
        BindInfo<RsfEnvironment> info = apiBinder.bindType(RsfEnvironment.class).toInstance(rsfEnv).toInfo();
        info.setMetaData(RsfEnvironment.class.getName(), rsfEnv);
        return rsfEnv;
    }
}
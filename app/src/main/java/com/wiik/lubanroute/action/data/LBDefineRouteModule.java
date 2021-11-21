package com.wiik.lubanroute.action.data;

import com.wiik.lubanroute.action.rourte.LBBaseRouteModuleDefault;
import com.wiik.lubanroute.action.util.LBRouterDataManager;

public class LBDefineRouteModule extends LBBaseRouteModuleDefault {

    private static volatile LBDefineRouteModule module;


    public LBDefineRouteModule() {
        module = this;

    }

    public static synchronized LBDefineRouteModule getInstance() {
        synchronized (LBDefineRouteModule.class) {
            return module;
        }
    }

    @Override
    public void regist(String modId, LBBaseRouteModule module) {
        if (!map.containsKey(modId)) {
            module.mContext = LBRouterDataManager.getIstance().getContext();
            map.put(modId, module);
        }
    }

}

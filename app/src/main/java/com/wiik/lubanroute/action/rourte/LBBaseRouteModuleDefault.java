package com.wiik.lubanroute.action.rourte;

import com.wiik.lubanroute.action.data.LBBaseRouteModule;

import java.util.HashMap;
import java.util.Map;

public abstract class LBBaseRouteModuleDefault {


    public Map<String, LBBaseRouteModule> map=new HashMap<>();

    public abstract void regist(String modId,LBBaseRouteModule module);


}

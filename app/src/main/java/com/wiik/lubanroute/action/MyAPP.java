package com.wiik.lubanroute.action;

import android.app.Application;

import com.wiik.lubanroute.action.util.LBRouterDataManager;

public class MyAPP extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LBRouterDataManager manager=new LBRouterDataManager(getBaseContext(),"routeCode");
        manager.initRoute();
    }
}

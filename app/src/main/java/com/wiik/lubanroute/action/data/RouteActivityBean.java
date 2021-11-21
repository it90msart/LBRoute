package com.wiik.lubanroute.action.data;

import android.app.Activity;
import android.content.Intent;

public class RouteActivityBean {
    private String routeCode;
    private String activityName;
    private int flags=-1;


    public RouteActivityBean(String routeCode, String activityName, int flags) {
        this.routeCode = routeCode;
        this.activityName = activityName;
        this.flags = flags;
    }


    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

}

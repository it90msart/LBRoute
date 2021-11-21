package com.wiik.lubanroute.action.data;

import android.content.Context;
import android.content.Intent;

public abstract class LBBaseRouteModule {

    public Context mContext;
    public abstract void route(String code, Intent intent);


}

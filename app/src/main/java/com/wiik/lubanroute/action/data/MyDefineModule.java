package com.wiik.lubanroute.action.data;

import android.content.Intent;
import android.text.TextUtils;

import com.wiik.lubanroute.action.TestActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MyDefineModule extends LBBaseRouteModule {
    @Override
    public void route(String code, Intent intent) {

        switch (Integer.parseInt(code)) {
            case 123:
                intent.setClass(mContext, TestActivity.class);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                break;
        }


    }
}

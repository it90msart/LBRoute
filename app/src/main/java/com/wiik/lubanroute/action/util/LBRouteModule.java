package com.wiik.lubanroute.action.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.wiik.lubanroute.action.data.LBBaseRouteModule;
import com.wiik.lubanroute.action.data.LBDefineRouteModule;
import com.wiik.lubanroute.action.data.RouteActivityBean;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class LBRouteModule {


    public static void routeDefineActivity(String code, Bundle bundle) {
        if (LBDefineRouteModule.getInstance() == null || LBDefineRouteModule.getInstance().map.isEmpty()) {
            new IllegalAccessException("手动维护模块没有注册，暂不支持该业务");
            return;
        }
        for (String modid : LBDefineRouteModule.getInstance().map.keySet()) {
            LBBaseRouteModule module = LBDefineRouteModule.getInstance().map.get(modid);
            if (module != null) {
                Intent intent = new Intent();
                intent.putExtras(bundle);
                module.route(code, intent);
            }
        }

    }

    /**
     * 通过自定义模块跳转
     */
    public static void routeDefineUrlActivity(String routeUrl) {
        routeUrl(routeUrl, true);

    }

    @SuppressLint("LongLogTag")
    public static void routeCodeActivity(String code, Bundle bundle) {
        if (LBRouterDataManager.getIstance().getContext() == null || LBRouterDataManager.getIstance().dataIsEmpty()) {
            Log.e("LBRouteModule--routeDetail", "context is null or routedata list is empty");
            return;
        }

        List<RouteActivityBean> routeActivityBeans = LBRouterDataManager.getIstance().getRouteActivityData();
        Context contex = LBRouterDataManager.getIstance().getContext();

        for (RouteActivityBean bean : routeActivityBeans) {
            if (TextUtils.equals(code, bean.getRouteCode())) {
                //找到路由key，进行跳转
                Intent intent = new Intent();
                intent.setClassName(contex.getPackageName(), bean.getActivityName());
                intent.putExtras(bundle);
                if (bean.getFlags() != -1) {
                    intent.setFlags(FLAG_ACTIVITY_NEW_TASK | bean.getFlags());
                } else {
                    intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                }
                contex.startActivity(intent);
                break;
            }
        }
    }

    /**
     * @param mimeClzz        :当前页面
     * @param routeMethodName 需要通过路由传递过去的数据的方法名
     * @param tagCode         目标页路由,只支持activity
     */
    @SuppressLint("LongLogTag")
    public static void routeCodeActivity(Class mimeClzz, String tagCode, @NonNull String routeMethodName) {
        if (LBRouterDataManager.getIstance().getContext() == null || LBRouterDataManager.getIstance().dataIsEmpty()) {
            Log.e("LBRouteModule--routeDetail", "context is null or routedata list is empty");
            return;
        }

        List<RouteActivityBean> routeActivityBeans = LBRouterDataManager.getIstance().getRouteActivityData();
        Context contex = LBRouterDataManager.getIstance().getContext();

        for (RouteActivityBean bean : routeActivityBeans) {
            if (TextUtils.equals(tagCode, bean.getRouteCode())) {
                //找到路由key，进行跳转
                Bundle bundle = null;
                try {
                    bundle = LBRouterDataManager.getIstance().annotationMethod(mimeClzz, routeMethodName);
                } catch (Exception e) {

                }
                Intent intent = new Intent();
                intent.setClassName(contex.getPackageName(), bean.getActivityName());
                intent.putExtras(bundle);
                if (bean.getFlags() != -1) {
                    intent.setFlags(FLAG_ACTIVITY_NEW_TASK | bean.getFlags());
                } else {
                    intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                }
                contex.startActivity(intent);
                break;
            }
        }
    }

    public static void routeUrlActivity(String routeUrl) {
        routeUrl(routeUrl, false);

    }

    private static void routeUrl(String routeUrl, boolean isMaster) {
        //routeCode是html中routeKye的key

        String routeKey = LBRouterDataManager.getIstance().getRouteKey();
        if (routeUrl.contains(routeKey) && routeUrl.contains("?")) {

            String[] text = routeUrl.split("\\?");
            String[] keyValue = text[1].split("&");
            Bundle bundle = new Bundle();
            String routeCode = "";//获取路由编号
            for (String value : keyValue) {
                if (TextUtils.isEmpty(value)) {
                    continue;
                }
                String[] kv = value.split("=");
                String key = "";
                String keyByValue = "";
                if (kv.length == 1) {
                    key = kv[0];

                } else if (kv.length == 2) {
                    key = kv[0];
                    keyByValue = kv[1];
                }
                if (TextUtils.isEmpty(key)) {
                    continue;
                }
                if (TextUtils.equals(routeKey, key)) {
                    //key
                    routeCode = keyByValue;
                } else {
                    bundle.putString(key, keyByValue);
                }
            }
            if (!isMaster) {
                routeCodeActivity(routeCode, bundle);//转换成原生，访问原生页面
            } else {
                routeDefineActivity(routeCode, bundle);
            }


        } else {
            //默认走内置h5,的页面,自己配置，如果没有路由标识，业务自行处理
            Intent intent = new Intent();
            Context context = LBRouterDataManager.getIstance().getContext();
            intent.setClass(context, LBRouterDataManager.getIstance().getWebActivity().getClass());
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

    }


}

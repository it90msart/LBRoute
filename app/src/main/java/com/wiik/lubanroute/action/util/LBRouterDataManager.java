package com.wiik.lubanroute.action.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.wiik.lubanroute.action.data.LBDefineRouteModule;
import com.wiik.lubanroute.action.data.RouteActivityBean;
import com.wiik.lubanroute.action.rourte.LBRouteMethod;
import com.wiik.lubanroute.action.rourte.LBRouteView;

import org.apache.commons.lang3a.reflect.MethodUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LBRouterDataManager {
    private List<RouteActivityBean> routeActivityBeans = new ArrayList<>();

    private static LBRouterDataManager manager;
    private Context mContext;
    private String routeKey = "";

    private Activity webActivity;


    public Activity getWebActivity() {
        return webActivity;
    }

    /**
     * @webActivity if url routecode unsupport,next will jump to webviewActivity
     * @webviewActivity
     */
    public void setWebActivity(Activity webActivity) {
        this.webActivity = webActivity;

    }

    /**
     * @routeKey 路由的关键字key
     */
    public LBRouterDataManager(Context mContext, String routeKey) {
        this.mContext = mContext;
        this.routeKey = routeKey;
        manager = this;
        LBDefineRouteModule  defineRouteModule=new LBDefineRouteModule();
    }

    public Context getContext() {
        return mContext;
    }


    public static synchronized LBRouterDataManager getIstance() {

        synchronized (LBRouterDataManager.class) {
            return manager;
        }
    }


    public void initRoute() {

        initData();
    }


    private void initData() {

        routeActivityBeans.clear();

        PackageManager manager = mContext.getPackageManager();
        try {
            PackageInfo packageInfo = manager.getPackageInfo(
                    mContext.getPackageName(), PackageManager.GET_ACTIVITIES);

            if (packageInfo != null) {
                for (ActivityInfo info : packageInfo.activities) {
                    String className = info.name;
                    Class<?> cls = Class.forName(className);
                    LBRouteView route = cls.getAnnotation(LBRouteView.class);
                    if (route != null && !TextUtils.isEmpty(route.className())) {
                        RouteActivityBean bean = new RouteActivityBean(route.routeCode(), route.className(),info.flags);
                        routeActivityBeans.add(bean);

                    }


                }
            }

        } catch (Exception e) {

        }

    }


    public List<RouteActivityBean> getRouteActivityData() {
        return routeActivityBeans;

    }

    public boolean dataIsEmpty() {
        if (routeActivityBeans == null || routeActivityBeans.isEmpty())
            return true;
        return false;
    }


    public String getRouteKey() {
        return routeKey;
    }


    public  Bundle annotationMethod(Class clazz, @NonNull String routeMethodName) {

        Bundle bundle = null;
//		 Class clazz = Class.forName("com.zbz.annotation.pattern1.WorkService");
        List<Method> methods = MethodUtils.getMethodsListWithAnnotation(clazz, LBRouteMethod.class);
        for (Method method : methods) {
            for (Annotation annotation : method.getDeclaredAnnotations()) {
                if (annotation instanceof LBRouteMethod) {
                    Class cls = ((LBRouteMethod) annotation).cls();
                    String methodName = ((LBRouteMethod) annotation).routeMethodName();
                    if (cls.getName().equals(clazz.getName()) && TextUtils.equals(routeMethodName, methodName)) {
                        try {
                            Object obj = method.invoke(clazz.newInstance());
                            if (obj instanceof Bundle) {
                                bundle = (Bundle) obj;
                            }
                        } catch (Exception e) {

                        }
                        break;
                    }
                }
            }
        }
        return bundle;

    }





}

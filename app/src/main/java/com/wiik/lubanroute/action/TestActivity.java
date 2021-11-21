package com.wiik.lubanroute.action;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wiik.lubanroute.action.rourte.LBRouteMethod;
import com.wiik.lubanroute.action.rourte.LBRouteView;
import com.wiik.lubanroute.action.util.LBRouteModule;

@LBRouteView(routeCode = "123",className = "com.wiik.lubanroute.action.TestActivity")
public class TestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);
        findViewById(R.id.click_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LBRouteModule.routeCodeActivity(TestActivity.this.getClass(),"123","getRouteData");
            }
        });

        findViewById(R.id.click_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("key","bundle手动传值");
                LBRouteModule.routeCodeActivity("123",bundle);
            }
        });
        findViewById(R.id.click_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="www.aaa.com?routeCode=123&key=routeCodevalue";
                LBRouteModule.routeUrlActivity(url);
            }
        });
        findViewById(R.id.click_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("key","bundle 自定义手动传值");
                LBRouteModule.routeDefineActivity("123",bundle);
            }
        });


        findViewById(R.id.click_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="www.aaa.com?routeCode=123&key=routeCodevaluedefine";
                LBRouteModule.routeDefineUrlActivity(url);
            }
        });

        TextView text = findViewById(R.id.text_result);
        Intent intent = getIntent();
        if (intent != null&&intent.getExtras()!=null) {
            Bundle bundle = intent.getExtras();
            if (bundle.containsKey("key")) {
                text.setText(bundle.getString("key"));
            } else {
                text.setText("暂无数据");
            }
        }
    }



    @LBRouteMethod(cls = TestActivity.class,routeMethodName = "getRouteData")
    public Bundle getRouteData() {
        Bundle bun=new Bundle();
        bun.putString("key","注解传值");
        return bun;
    }
    @LBRouteMethod(cls = TestActivity1.class, routeMethodName = "getRouteData1")
    public Bundle getRouteData1() {
        Bundle bun = new Bundle();
        bun.putString("key1", "value1");
        return bun;
    }



}

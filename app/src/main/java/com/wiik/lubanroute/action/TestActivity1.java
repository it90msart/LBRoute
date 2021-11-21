package com.wiik.lubanroute.action;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wiik.lubanroute.action.rourte.LBRouteMethod;
import com.wiik.lubanroute.action.rourte.LBRouteView;
import com.wiik.lubanroute.action.util.LBRouteModule;

@LBRouteView(routeCode = "1001", className = "com.wiik.lubanroute.action.TestActivity1")
public class TestActivity1 extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);
        TextView text = findViewById(R.id.text_result);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle.containsKey("key")) {
                text.setText(bundle.getString("key"));
            } else {
                text.setText("暂无数据");
            }
        }


    }


    @LBRouteMethod(cls = TestActivity1.class, routeMethodName = "getRouteData")
    public Bundle getRouteData() {
        Bundle bun = new Bundle();
        bun.putString("key", "value");
        return bun;
    }



}

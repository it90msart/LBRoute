# LBRoute说明文档

1.使用说明:  
LBRoute路由是一款android路由跳转管理系统。支持两种方式  
第一种:注解防方式  
第二种:用户自定义  

背景：LB是鲁班的简称luban,后期我会提供一系列lb库，解决开过过程存在的各种问题，一站式解决大家所有的问题，可以提供个性化基础库，能够让大多数人通过鲁班系列的依赖库，快速搭建自己的项目，提高开发速度,降低开发成本。     
# 使用说明:

# 1.初始化，注解部分
在application出初始化：

     LBRouterDataManager manager=new LBRouterDataManager(getBaseContext(),"routeCode");
        manager.initRoute();
manager.setWebViewActivity(Activity webview);
        
        
初始化需要传两个参数一个是路由key，这个key用来标识路由编号，在后期的使用过程中，通过这个key来取值，匹配到指定的Activity。
Context：是上下文，用来跳转

setWebViewActivity(Activity):是支持h5跳转，如果你的h5连接需要访问原生，需要在这边设定一个h5默认跳转的内部加载页，如果没有命中路由页面，将会跳转到你指定的h5页面。
       
       
       
# 2.如果想自定义和注解路由都在用，那需要定义自己的路由模块。
定义一个自己的路由模块，

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

#  在application注册好自己的模块

中新增LBDefineRouteModule.getInstance().regist("mymodi",new MyDefineModule());  
由于LBDefineRouteModule在LBRouterDataManager已初始化，不需要再次初始化了



# 3.使用：LBRouteModule 为路跳转管理核心方法提供

3.1LBRouteView是对Activity类的注解

@LBRouteView(routeCode = "123",className = "com.wiik.lubanroute.action.TestActivity")  
public class TestActivity extends AppCompatActivity{  
}

routeCode:路由编号  
className:类的报名+类名

3.2LBRouteMethod方法注解：用于跳转过程中，参数获取携带参数的方法  

    @LBRouteMethod(cls = TestActivity.class,routeMethodName = "getRouteData")
    public Bundle getRouteData() {
        Bundle bun=new Bundle();
        bun.putString("key","注解传值");
        return bun;
    }
    
   
cls：指向当前Activity  
routeMethodName:该方法名  
配合路由使用，可以支持多方法  



#  使用分为两种:

# 3.1注解：注解使用包括两种，注解和h5路由跳转

# 1.原生通过页面跳转的，获取参数也是通过注解方法

LBRouteModule.routeCodeActivity(TestActivity.this.getClass(),"123","getRouteData");

arg1:当前类的class，

arg2:页面路由编号

arg3:参数跳转的参数方法名称


# 2.原生页面通过手动绑定参数

     Bundle bundle=new Bundle();
     bundle.putString("key","bundle手动传值");
     LBRouteModule.routeCodeActivity("123",bundle);
     
     arg1:页面路由编码
     
     arg2:参数的参数
     
     
# 3.H5跳转路由
       String url="www.aaa.com?routeCode=123&key=routeCodevalue";
       LBRouteModule.routeUrlActivity(url);



# 4.H5跳转到自定义路由

 String url="www.aaa.com?routeCode=123&key=routeCodevaluedefine";
                LBRouteModule.routeDefineUrlActivity(url);
                
                
# 5.原生跳转到自定义
           Bundle bundle=new Bundle();
           bundle.putString("key","bundle 自定义手动传值");
           LBRouteModule.routeDefineActivity("123",bundle);
           
           这边逻辑在自定义MyDefineModule方法中处理
                   switch (Integer.parseInt(code)) {
            case 123:
                intent.setClass(mContext, TestActivity.class);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                break;
        }
     
     
     
# 注意：如果你需要使用h5混合使用，就必须设置webViewActivity，否则会报异常,使用可以参考DEMO提供

# 接入
1.集成：依赖库implementation 'com.github.it90msart:LBRoute:1.0.0'

如果你的项目需要混淆:

混淆-keep class com.wiik.lubanroute.action.**{*;}
























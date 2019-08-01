# KtModuleMvpApp
1.该项目是基于Kotlin+Retrofit2+Rxjava2+Mvp+组件化的基础框架；
2.内部对于网络框架做了封装，逻辑清晰，其中包括:
    (1)增加日志拦截器，在调试模式下，可以在控制台查看各位请求参数及响应数据；
    (2)对GET/POST请求参数的处理，以尽量少的代码实现功能；
3.组件间的跳转和数据交互使用Arouter实现；
4.项目各界面实现了沉浸式，部分界面对状态栏做了相应处理；
5.内部有一些常见界面示例；包括顶部状态栏悬停和多种列表，图片加载选用Glide/Fresco；
6.BaseRecyclerViewAdapterHelper支持添加 header/footer，分页加载，多类型列表等等；
7.对greenDao的使用做了一些范例，通过该范例，可以了解到greenDao使用的大部分主功能：
    (1)通过连表查询相关联数据表的数据；
    (2)数据库升级，数据迁移
8.采用的androidx库文件，轻松获取友盟多渠道统计所需要的渠道信息；
9.自定义的拦截器在控制台输出了Http/Https请求和响应的数据，很大幅度的提高网络请求相关工作效率。
https://github.com/BuilderPattern/KtModuleMvpApp
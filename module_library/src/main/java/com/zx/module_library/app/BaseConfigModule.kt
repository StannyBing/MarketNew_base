package com.zx.module_library.app

import android.app.Application
import android.content.Context
import com.frame.zxmvp.di.module.GlobalConfigModule
import com.frame.zxmvp.http.AppDelegate
import com.frame.zxmvp.http.GlobalHttpHandler
import com.frame.zxmvp.integration.ConfigModule
import com.frame.zxmvp.integration.IRepositoryManager
import com.zx.zxutils.util.ZXLogUtil
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created by Xiangb on 2019/2/26.
 * 功能：
 */
open class BaseConfigModule(val apiService: Class<*>, var moduleName: String) : ConfigModule {

    enum class APP_TYPE(val versionCode: Int, val versionName: String, val realseUrl: String, val debugUrl: String, val areaParentId : String) {
        BASE(1, "1.0.0", "http://rc.jx968969.com/", "http://scjg.zxgeo.com/", "360481"),
        DX_APP(1, "1.0.0", "http://223.83.172.79:90/", "http://192.168.11.248:8106/", "360481"),
        RC_APP(1, "1.0.0", "http://rc.jx968969.com/", "http://192.168.11.248:8101/", "360481");
    }

    companion object {
        const val APP_HEAD = ""//头部(用于区分不同的应用)
        val appInfo = when (APP_HEAD) {
            "dx" -> APP_TYPE.DX_APP
            "rc" -> APP_TYPE.RC_APP
            else -> APP_TYPE.BASE
        }
        val ISRELEASE = false// 是否正式环境
        val BASE_IP = if (ISRELEASE) appInfo.realseUrl else appInfo.debugUrl
        var TOKEN = ""
    }


    override fun applyOptions(context: Context, builder: GlobalConfigModule.Builder) {
        builder.baseurl(BASE_IP)
                //使用builder可以为框架配置一些配置信息
                .globalHttpHandler(object : GlobalHttpHandler {
                    // 这里可以提供一个全局处理Http请求和响应结果的处理类,
                    // 这里可以比客户端提前一步拿到服务器返回的结果,可以做一些操作,比如token超时,重新获取
                    override fun onHttpResultResponse(httpResult: String, chain: Interceptor.Chain, response: Response): Response {
                        /* 这里可以先客户端一步拿到每一次http请求的结果,可以解析成json,做一些操作,如检测到token过期后
                           重新请求token,并重新执行请求 */
                        var url = chain.request().url().toString()
                        url = url.substring(0, url.indexOf(".do")) + ".do"
                        ZXLogUtil.loge("Response $moduleName: ${url.substring(url.lastIndexOf("/") + 1)} : $httpResult")
                        return response
                    }

                    // 这里可以在请求服务器之前可以拿到request,做一些操作比如给request统一添加token或者header以及参数加密等操作
                    override fun onHttpRequestBefore(chain: Interceptor.Chain, request: Request): Request {
                        /* 如果需要再请求服务器之前做一些操作,则重新返回一个做过操作的的requeat如增加header,不做操作则直接返回request参数
                           return chain.request().newBuilder().header("token", tokenId)
                                  .build(); */
                        ZXLogUtil.loge("Request $moduleName: ${request.url()}")
                        return chain.request().newBuilder().header("Authorization", TOKEN)
                                .build()
                        //                        return chain.request().newBuilder().header("Accept", "application/json").build();
                    }
                })
                .responseErroListener { context1, e ->
                    /* 用来提供处理所有错误的监听
               rxjava必要要使用ErrorHandleSubscriber(默认实现Subscriber的onError方法),此监听才生效 */
                }
    }

    override fun registerComponents(context: Context, repositoryManager: IRepositoryManager) {
        repositoryManager.injectRetrofitService(apiService)
    }

    override fun injectAppLifecycle(context: Context?, lifecycles: MutableList<AppDelegate.Lifecycle>) {
        // AppDelegate.Lifecycle 的所有方法都会在基类Application对应的生命周期中被调用,所以在对应的方法中可以扩展一些自己需要的逻辑
        lifecycles.add(object : AppDelegate.Lifecycle {
            override fun onCreate(application: Application) {

            }

            override fun onTerminate(application: Application) {

            }
        })
    }
}
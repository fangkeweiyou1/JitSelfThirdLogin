package com.zhang.jitselfthirdlogin

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.webkit.WebSettings


/**
 * Created by zhangyuncai on 2019/6/27.
 * 应用图标/名称/包名工具类
 */
object AppUtil {

    /**
     * 获取应用名称,如果应用没有设置appname,那么就会闪退
     */
    fun getAppName(context: Context): String {
        val packageManager = context.getPackageManager()
        val packageInfo = packageManager.getPackageInfo(
                context.getPackageName(), 0
        )
        val labelRes = packageInfo.applicationInfo.labelRes
        packageInfo.applicationInfo.labelRes
        return context.getResources().getString(labelRes)
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @return 当前应用的版本名称
     */
    fun getVersionName(context: Context): String {
        val packageManager = context.getPackageManager()
        val packageInfo = packageManager.getPackageInfo(
                context.getPackageName(), 0
        )
        return packageInfo.versionName
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @return 当前应用的版本名称
     */
    fun getVersionCode(context: Context): Long {
        val packageManager = context.getPackageManager()
        val packageInfo = packageManager.getPackageInfo(
                context.getPackageName(), 0
        )
        if (Build.VERSION.SDK_INT >= 28) {
            return packageInfo.longVersionCode
        }
        return packageInfo.versionCode.toLong()
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    fun getPackageName(context: Context): String {
        val packageManager = context.getPackageManager()
        val packageInfo = packageManager.getPackageInfo(
                context.getPackageName(), 0
        )
        return packageInfo.packageName
    }

    /**
     * 获取包名中最后一个字符串
     */
    fun getPackageNameLastChar(context: Context): String {
        val packageManager = context.getPackageManager()
        val packageInfo = packageManager.getPackageInfo(
                context.getPackageName(), 0
        )
        val charList = packageInfo.packageName.split(".")
        return charList.last()
    }

    fun isAppInstall(context: Context, appName: String?): Boolean {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = context.getPackageManager().getPackageInfo(appName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return packageInfo != null
    }

    fun downloadApp(context: Context, appName: String) {
        val GOOGLE_PLAY = "com.android.vending" //这里对应的是谷歌商店，跳转别的商店改成对应的即可
        if (!isAppInstall(context, GOOGLE_PLAY)) {
            return
        }

        try {
            if (TextUtils.isEmpty(appName)) return
            val uri = Uri.parse("market://details?id=$appName")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage(GOOGLE_PLAY)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: Exception) {
            //Toast.makeText(context, "", Toast.LENGTH_SHORT).show();   //跳转失败的处理
        }
    }

    //修改了okhttp请求头和标准请求不一致的错误
    public fun getUserAgent(context: Context): String {
        var userAgent = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(context)
            } catch (e: Exception) {
                userAgent = System.getProperty("http.agent")
            }
        } else {
            userAgent = System.getProperty("http.agent")
        }
        //调整编码，防止中文出错
        val sb = StringBuffer()
        var i = 0
        val length = userAgent.length
        while (i < length) {
            val c = userAgent[i]
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", c.toInt()))
            } else {
                sb.append(c)
            }
            i++
        }
        return sb.toString()
    }

}
package com.zhang.jitselfthirdlogin;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Map;

import static java.lang.System.out;

public class MainActivity extends AppCompatActivity {
    private LinearLayout ll_main_gen;
    private AppCompatActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;

        ll_main_gen = findViewById(R.id.ll_main_gen);
        ll_main_gen.removeAllViews();

        addButton("用2020Api吊起WhatsappAPP", v -> {
            String appPackage = "com.whatsapp";
            if (AppUtil.INSTANCE.isAppInstall(mActivity, appPackage)) {
                //smsto:60109080255
                Uri uri = Uri.parse("smsto:60109080255");    //指定打开phoneNumber发送信息页面
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
                sendIntent.setPackage(appPackage);//区别别的应用包名
                startActivity(sendIntent);
            } else {
                showToast("应用未安装");
            }
        });
        addButton("用2018Api吊起WhatsappAPP", v -> {
            //todo 这个比较稳妥一点
            String appPackage = "com.whatsapp";
            if (AppUtil.INSTANCE.isAppInstall(mActivity, appPackage)) {
                //2018 api
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://api.whatsapp.com/send?phone=60109080255"));
                startActivity(intent);
            } else {
                showToast("应用未安装");
            }
        });
        addButton("用2020Api调起MessagerAPP", v -> {
            //todo 可能出现选择其他APP的界面
            String appPackage = "com.facebook.orca";
            if (AppUtil.INSTANCE.isAppInstall(mActivity, appPackage)) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://messaging/1722485538043321"));
                startActivity(i);
            } else {
                showToast("应用未安装");
            }
        });
        addButton("用2018Api调起MessagerAPP", v -> {
            //todo 这个比较稳妥一点,明确指向了messager
            String appPackage = "com.facebook.orca";
            if (AppUtil.INSTANCE.isAppInstall(mActivity, appPackage)) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("fb-messenger://user/1722485538043321"));
                startActivity(i);
            } else {
                showToast("应用未安装");
            }
        });
        addButton("吊起FacebookAPP", v -> {
            String appPackage = "com.facebook.katana";
            if (AppUtil.INSTANCE.isAppInstall(mActivity, appPackage)) {
                Uri uri = Uri.parse("fb://profile/1722485538043321");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            } else {
                showToast("应用未安装");
            }
        });
        addButton("吊起网页版Facebook", v -> {
            Uri uri = Uri.parse("https://www.facebook.com/profile.php?id=1722485538043321");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
        addButton("吊起GoolePlay并制定下载APP", v -> {
            String download_appPackage = "com.wushi.lenovo.asia5b";
            String GOOGLE_PLAY_appPackage = "com.android.vending";
            try {
                if (TextUtils.isEmpty(download_appPackage))
                    return;
                Uri uri = Uri.parse("market://details?id=" + download_appPackage);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage(GOOGLE_PLAY_appPackage);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } catch (Exception e) {
                //Toast.makeText(context, "", Toast.LENGTH_SHORT).show();   //跳转失败的处理
            }
        });
        addButton("吊起新代购并登陆", v -> {
            String appPackage = "com.wushi.lenovo.asia5b";
            if (AppUtil.INSTANCE.isAppInstall(mActivity, appPackage)) {
                Uri uri = Uri.parse("asia5bnew://profile/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            } else {
                showToast("应用未安装");
            }
        });
        addButton("吊起商城并登陆", v -> {
            String appPackage = "com.asia5b.a5bmall";
            if (AppUtil.INSTANCE.isAppInstall(mActivity, appPackage)) {
                Uri uri = Uri.parse("a5bmall://profile/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivityForResult(intent, 81);
            } else {
                showToast("应用未安装");
            }
        });
    }

    void addButton(String text, View.OnClickListener clickListener) {
        Button button = new Button(this);
        button.setText(text);
        button.setOnClickListener(clickListener);
        ll_main_gen.addView(button);
    }

    void showToast(String content) {
        Toast.makeText(mActivity, content, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 81) {
                if (data.hasExtra("profile")) {
                    Map<String,Object> profile = (Map<String, Object>) data.getSerializableExtra("profile");
                    showToast("登录成功,资料:" + profile);
                    out.println("---<<<>>>---profile:" + profile);
                }
            }
        }

    }
}

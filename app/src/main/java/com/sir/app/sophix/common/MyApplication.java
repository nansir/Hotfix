package com.sir.app.sophix.common;

import android.util.Log;

import com.sir.app.base.BaseApplication;
import com.sir.app.sophix.BuildConfig;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

/**
 * Created by zhuyinan on 2017/8/5.
 * Contact by 445181052@qq.com
 * code 具体说明
 * https://help.aliyun.com/document_detail/53240.html?spm=5176.doc53287.2.3.TXS3f2#h2-1-4-
 */
public class MyApplication extends BaseApplication {

    private String TAG = "Sophix";

    @Override
    public void onCreate() {
        super.onCreate();
        initSophix();
    }

    private void initSophix() {
        // initialize最好放在attachBaseContext最前面，初始化直接在Application类里面，切勿封装到其他类
        SophixManager.getInstance().setContext(this)
                .setAppVersion(BuildConfig.VERSION_NAME)
                .setAesKey(null)
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        Log.i(TAG, "mode:" + mode + ";code:" + code + ";info:" + info);
                        // 补丁加载回调通知
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            // 表明补丁加载成功
                            Log.i(TAG, "表明补丁加载成功");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后调用killProcessSafely自杀，以此加快应用补丁，详见1.3.2.3
                            Log.i(TAG, "内部引擎异常, 推荐此时清空本地补丁, 防止失败补丁重复加载");
                        } else if (code == PatchStatus.CODE_REQ_NOUPDATE) {
                            Log.i(TAG, "没有更新");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后应用自杀
                            Log.i(TAG, "用户可以监听进入后台事件, 然后应用自杀");
                        } else {
                            // 其它错误信息, 查看PatchStatus类说明
                            Log.i(TAG, " 其它错误信息, 查看PatchStatus类说明");
                        }
                    }
                }).initialize();
        // queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，建议放在后面任意时刻，如onCreate中
        SophixManager.getInstance().queryAndLoadNewPatch();

    }
}

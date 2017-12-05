package com.sir.app.sophix;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sir.app.base.BaseActivity;

import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by zhuyinan on 2017/8/5.
 * Contact by 445181052@qq.com
 */
public class MainActivity extends BaseActivity {

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        ((TextView) findViewById(R.id.version_name)).setText(String.valueOf(BuildConfig.VERSION_NAME));
    }

    @Override
    public void doBusiness(Context mContext) {

        String[] perms = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (!EasyPermissions.hasPermissions(MainActivity.this, perms)) {
            EasyPermissions.requestPermissions(MainActivity.this, "需要访问手机存储权限！", 100, perms);
        }
    }


    @OnClick(R.id.btn_hotfix)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_hotfix:
                Toast.makeText(this, "测试热修复补丁之后", Toast.LENGTH_LONG).show();
                break;
        }
    }
}

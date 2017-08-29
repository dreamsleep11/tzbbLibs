package com.dhcc.workbench.ReactNativeHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactInstanceManagerBuilder;
import com.facebook.react.ReactPackage;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.PermissionListener;
import com.facebook.react.shell.MainReactPackage;

import java.util.List;

import javax.annotation.Nullable;

/**
 * Created by dreamsleep on 2017/8/18.
 */

public abstract class RNMainActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler {


    class MReactActivityDelegate extends ReactActivityDelegate {

        public MReactActivityDelegate(Activity activity, @Nullable String mainComponentName) {
            super(activity, mainComponentName);
        }

        public MReactActivityDelegate(FragmentActivity fragmentActivity, @Nullable String mainComponentName) {
            super(fragmentActivity, mainComponentName);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        protected void onPause() {
            super.onPause();
        }

        @Override
        protected void onResume() {
            super.onResume();
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
        }
    }

    private MReactActivityDelegate mDelegate;
    private ReactRootView mReactRootView;
    private ReactInstanceManager mReactInstanceManager;
    private MSetting mSetting;
    private String name;

    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        AttrGet.setContext(this);
        mDelegate = new MReactActivityDelegate(this, name);
        this.mDelegate.onCreate(savedInstanceState);
//        mainCreate();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mainCreate();
//        AttrGet.setContext(this);
    }

    protected abstract List<ReactPackage> getPackages();

    public void mainCreate() {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        name = bundle.getString("name");
        String ip = bundle.getString("ip");
        String port = bundle.getString("port");
        String file = bundle.getString("file");

        mSetting = MSetting.get(this);
        mSetting.setDebugServerHost(ip + ":" + port);
//        mSetting.pushData("BranchID",AttrGet.getMetaData("BranchID"));
        mReactRootView = new ReactRootView(this);
        ReactInstanceManagerBuilder builder = ReactInstanceManager.builder();
        builder.setApplication(getApplication());
        builder.setCurrentActivity(this);
        builder.setBundleAssetName("index.android.bundle"); //可远程地址
        builder.setJSMainModuleName(file);//根目录下index.android.js文件
        builder.addPackage(new MainReactPackage());
        builder.addPackage(new RNModulePackage());

        for (ReactPackage reactPackage : getPackages()) {
            System.out.println("for");
            System.out.println("for");
            System.out.println("for");
            System.out.println("for");
            System.out.println("for");
            builder.addPackage(reactPackage);
        }
        builder.setUseDeveloperSupport(true)
                .setInitialLifecycleState(LifecycleState.RESUMED);
        mReactInstanceManager = builder.build();
//        System.out.println("ccc");
//        mReactInstanceManager.getDevSupportManager().addCustomDevOption("Exit", new DevOptionHandler() {
//            @Override
//            public void onOptionSelected() {
//                RNMainActivity.this.finish();
//            }
//        });

//        mReactInstanceManager.getCurrentReactContext().getCurrentActivity()

        ((RNMainApplication) getApplication()).setReactInstanceManager(mReactInstanceManager);
        mReactRootView.startReactApplication(mReactInstanceManager, name, null);
        setContentView(mReactRootView);
    }
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mDelegate.onDestroy();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return this.mDelegate.onKeyUp(keyCode, event) || super.onKeyUp(keyCode, event);
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.mDelegate.onActivityResult(requestCode, resultCode, data);
    }

    public void onBackPressed() {
        if (!this.mDelegate.onBackPressed()) {
            super.onBackPressed();
        }
    }

    public void requestPermissions(String[] permissions, int requestCode, PermissionListener listener) {
        this.mDelegate.requestPermissions(permissions, requestCode, listener);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        this.mDelegate.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

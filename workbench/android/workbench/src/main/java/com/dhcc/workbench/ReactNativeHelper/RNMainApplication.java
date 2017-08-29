package com.dhcc.workbench.ReactNativeHelper;

import android.app.Application;

import com.dhcc.workbench.kernel.AttrGet;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.soloader.SoLoader;

import java.util.List;

/**
 * Created by dreamsleep on 2017/8/21.
 */

public class RNMainApplication extends Application implements ReactApplication {
    private ReactInstanceManager reactInstanceManager;
    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {

        @Override
        public boolean getUseDeveloperSupport() {
            return AttrGet.isDebug();
        }
        @Override
        protected List<ReactPackage> getPackages() {
            return null;
        }

        @Override
        public ReactInstanceManager getReactInstanceManager() {
            return reactInstanceManager;
        }

        @Override
        public boolean hasInstance() {
            return true;
        }

        @Override
        public void clear() {
            if(reactInstanceManager!=null){
                reactInstanceManager.destroy();
                reactInstanceManager=null;
            }
        }
    };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SoLoader.init(this, /* native exopackage */ false);
    }


    public void setReactInstanceManager(ReactInstanceManager reactInstanceManager){
        this.reactInstanceManager=reactInstanceManager;
    }

}
package com.dhcc.workbench.ReactNativeHelper;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 */

public class RNModulePackage implements ReactPackage {
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
//        modules.add(new LocalPersistence(reactContext));
//        modules.add(new Serializer(reactContext));
//        modules.add(new Ajax(reactContext));
//        modules.add(new HardwareDrivers(reactContext));
//        modules.add(new ScanCode(reactContext));
//        modules.add(new Phone(reactContext));
        return modules;
    }

    @Override
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}

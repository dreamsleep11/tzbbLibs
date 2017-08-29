package com.dhcc.workbench.ReactNativeHelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.facebook.react.common.annotations.VisibleForTesting;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by 张都 on 2017/1/18.
 */
@VisibleForTesting
public class MSetting implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String PREFS_FPS_DEBUG_KEY = "fps_debug";
    private static final String PREFS_JS_DEV_MODE_DEBUG_KEY = "js_dev_mode_debug";
    private static final String PREFS_JS_MINIFY_DEBUG_KEY = "js_minify_debug";
    private static final String PREFS_DEBUG_SERVER_HOST_KEY = "debug_http_host";
    private static final String PREFS_ANIMATIONS_DEBUG_KEY = "animations_debug";
    private static final String PREFS_RELOAD_ON_JS_CHANGE_KEY = "reload_on_js_change";
    private static final String PREFS_INSPECTOR_DEBUG_KEY = "inspector_debug";
    private static final String PREFS_HOT_MODULE_REPLACEMENT_KEY = "hot_module_replacement";
    private static final String PREFS_REMOTE_JS_DEBUG_KEY = "remote_js_debug";
    private static final String PREFS_VERSION="version";


    private final SharedPreferences mPreferences;

    private static MSetting instance;

    public static MSetting get(Context application){
        if(instance==null){
            instance=new MSetting(application);
        }
        return instance;
    }

    public static MSetting get(){
        return instance;
    }

    private MSetting(
            Context applicationContext) {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        mPreferences.registerOnSharedPreferenceChangeListener(this);
    }
    public void setPrefsVersion(String version){
        mPreferences.edit().putString(PREFS_VERSION, version).apply();
    }
    public String getPrefsVersion(){
        return  mPreferences.getString(PREFS_VERSION,"0");
    }
    public void setFpsDebugEnabled(boolean enabled) {
        mPreferences.edit().putBoolean(PREFS_FPS_DEBUG_KEY, enabled).apply();
    }

    public void setAnimationFpsDebugEnabled(boolean value) {
        mPreferences.edit().putBoolean(PREFS_ANIMATIONS_DEBUG_KEY, value).apply();
    }

    public void setJSDevModeEnabled(boolean value) {
        mPreferences.edit().putBoolean(PREFS_JS_DEV_MODE_DEBUG_KEY, value).apply();
    }

    public void setJSMinifyEnabled(boolean value) {
        mPreferences.edit().putBoolean(PREFS_JS_MINIFY_DEBUG_KEY, value).apply();
    }

    public void setDebugServerHost(String host) {
        mPreferences.edit().putString(PREFS_DEBUG_SERVER_HOST_KEY, host).apply();
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (PREFS_FPS_DEBUG_KEY.equals(key) ||
                PREFS_RELOAD_ON_JS_CHANGE_KEY.equals(key) ||
                PREFS_JS_DEV_MODE_DEBUG_KEY.equals(key) ||
                PREFS_JS_MINIFY_DEBUG_KEY.equals(key)) {
        }
    }

    public boolean isHotModuleReplacementEnabled() {
        return mPreferences.getBoolean(PREFS_HOT_MODULE_REPLACEMENT_KEY, false);
    }

    public void setHotModuleReplacementEnabled(boolean enabled) {
        mPreferences.edit().putBoolean(PREFS_HOT_MODULE_REPLACEMENT_KEY, enabled).apply();
    }

    public boolean isReloadOnJSChangeEnabled() {
        return mPreferences.getBoolean(PREFS_RELOAD_ON_JS_CHANGE_KEY, false);
    }

    public void setReloadOnJSChangeEnabled(boolean enabled) {
        mPreferences.edit().putBoolean(PREFS_RELOAD_ON_JS_CHANGE_KEY, enabled).apply();
    }

    public boolean isElementInspectorEnabled() {
        return mPreferences.getBoolean(PREFS_INSPECTOR_DEBUG_KEY, false);
    }

    public void setElementInspectorEnabled(boolean enabled) {
        mPreferences.edit().putBoolean(PREFS_INSPECTOR_DEBUG_KEY, enabled).apply();
    }

    public boolean isRemoteJSDebugEnabled() {
        return mPreferences.getBoolean(PREFS_REMOTE_JS_DEBUG_KEY, false);
    }

    public void setRemoteJSDebugEnabled(boolean remoteJSDebugEnabled) {
        mPreferences.edit().putBoolean(PREFS_REMOTE_JS_DEBUG_KEY, remoteJSDebugEnabled).apply();
    }

    /**
     * 获取各项配置参数
     * @return
     */
    public Map<String, String> getPreferences(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", mPreferences.getString("name", ""));
        params.put("eName", mPreferences.getString("eName", ""));
        params.put("ip", mPreferences.getString("ip", ""));
        params.put("port", mPreferences.getString("port", ""));
        params.put("file", mPreferences.getString("file", ""));
        params.put("describe", mPreferences.getString("describe", ""));
        return params;
    }

    public  void pushData(String key,String  value){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public  String  getData(String key){
        return mPreferences.getString(key, "");
    }

    public void clearData(){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public void removeData(String key){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.remove(key);
        editor.commit();
    }
}
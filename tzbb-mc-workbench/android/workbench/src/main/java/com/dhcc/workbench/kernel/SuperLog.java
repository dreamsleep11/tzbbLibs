package com.dhcc.workbench.kernel;

import android.os.Looper;
import android.util.Log;

/**
 * 日志输出帮助类
 * Created by 张立伟 on 2016/7/12.
 */
public class SuperLog {

    /**
     * 修改关闭或打开输出
     */
    private static String TAG = "blpapp";
    private static int LOG_LEVEL = 1;
    private static final String[] levels = new String[]{"debug", "info", "warning", "error"};

    private enum LogLevel {
        LOG_ERROR(1),
        LOG_DEBUG(2),
        LOG_INFO(3),
        LOG_WARN(4);

        private int value;

        LogLevel(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    static {
        Log.e("Looper==mainLo0per", (Looper.myLooper() == Looper.getMainLooper()) + "");
        try {
            Class s = Class.forName(AttrGet.getPackageName() + ".BuildConfig");
            if (s.getField("LOG_TAG") != null) {
                TAG = String.valueOf(s.getField("LOG_TAG").get(null));
            }
            if (s.getField("LOG_LEVEL") != null) {
                String level = String.valueOf(s.getField("LOG_LEVEL").get(null));
                for (int i = 0; i < levels.length; i++) {
                    if (levels[i].equals(level)) {
                        LOG_LEVEL = i;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            StackHelper.printStack(e);
        }
    }


    /**
     * 调试日志输出 并附上当前方法调用者的前几个调用者
     *
     * @param logInfo  调试信息
     * @param upMethod 前几个调用者
     */
    public static void d(Object logInfo, int upMethod) {
        if (LOG_LEVEL == 0) {
            String logStr = getLineInfo(upMethod) + String.valueOf(logInfo) + "\n";
            logOut(logStr, LogLevel.LOG_DEBUG);
        }
    }

    /**
     * 输出日志
     *
     * @param logInfo
     */
    public static void e(Object logInfo) {
        if (LOG_LEVEL < 4) {
            simpleLog(LogLevel.LOG_ERROR, logInfo);
        }
    }

    /**
     * 输出格式化日志 减少字符串创建
     *
     * @param logInfo 例如 数字a={} 数字b={}
     * @param params  格式化字符串的参数
     */
    public static void e(String logInfo, final Object... params) {
        if (LOG_LEVEL < 4) {
            formaterLog(LogLevel.LOG_ERROR, logInfo, params);
        }
    }

    /**
     * 输出日志
     *
     * @param logInfo
     */
    public static void i(Object logInfo) {
        if (LOG_LEVEL < 2) {
            simpleLog(LogLevel.LOG_INFO, logInfo);
        }
    }

    /**
     * 输出格式化日志 减少字符串创建
     *
     * @param logInfo 例如 数字a={} 数字b={}
     * @param params  格式化字符串的参数
     */
    public static void i(String logInfo, final Object... params) {
        if (LOG_LEVEL < 2) {
            formaterLog(LogLevel.LOG_INFO, logInfo, params);
        }
    }

    /**
     * 调试日志输出
     *
     * @param logInfo
     */
    public static void d(Object logInfo) {
        if (LOG_LEVEL < 1) {
            simpleLog(LogLevel.LOG_DEBUG, logInfo);
        }
    }

    /**
     * 输出格式化日志 减少字符串创建
     *
     * @param logInfo 例如 数字a={} 数字b={}
     * @param params  格式化字符串的参数
     */
    public static void d(String logInfo, final Object... params) {
        if (LOG_LEVEL < 1) {
            formaterLog(LogLevel.LOG_DEBUG, logInfo, params);
        }
    }

    /**
     * 调试日志输出
     *
     * @param logInfo
     */
    public static void w(Object logInfo) {
        if (LOG_LEVEL < 3) {
            simpleLog(LogLevel.LOG_WARN, logInfo);
        }
    }

    /**
     * 输出格式化日志 减少字符串创建
     *
     * @param logInfo 例如 数字a={} 数字b={}
     * @param params  格式化字符串的参数
     */
    public static void w(String logInfo, final Object... params) {
        if (LOG_LEVEL < 3) {
            formaterLog(LogLevel.LOG_WARN, logInfo, params);
        }
    }

    /**
     * 输出调试信息 并附上当前方法调用者的前几个调用者
     *
     * @param logInfo  调试信息
     * @param upMethod 前几个调用者
     */
    public static void e(Object logInfo, int upMethod) {
        if (LOG_LEVEL < 4) {
            String logStr = getLineInfo(upMethod) + String.valueOf(logInfo) + "\n";
            Log.e(TAG, logStr);
//            FileLoggerHelper.get().appendSdCardFile(logStr);
        }
    }

    /**
     * 对格式化字符串的日志输出到对应的级别
     *
     * @param level   级别
     * @param logInfo 日志内容
     * @param params  日志参数
     */
    private static void formaterLog(LogLevel level, String logInfo, final Object... params) {
        String info = formatter(logInfo, params);
        String infos[] = info.split("\\n");
        logOut(getLineInfo(0) + infos[0] + "\n", level);
        for (int i = 1; i < infos.length; i++) {
            logOut("\t" + infos[i] + "\n", level);
        }
    }

    /**
     * 简单的输出日志内容
     *
     * @param level
     * @param logInfo
     */
    private static void simpleLog(LogLevel level, Object logInfo) {
        String info = String.valueOf(logInfo);
        String infos[] = info.split("\\n");
        logOut(getLineInfo(1) + infos[0] + "\n", level);
        for (int i = 1; i < infos.length; i++) {
            logOut("\t" + infos[i] + "\n", level);
        }
    }

    /**
     * 格式化字符串 {} 表示参数
     *
     * @param logInfo
     * @param params
     * @return
     */
    private static String formatter(String logInfo, final Object... params) {
        try {
            logInfo = RegExp.replace(logInfo, "\\{\\}", new RegExp.ReplaceFunc() {
                @Override
                public String func(String... args) throws ReplaceException {
                    if (index < params.length) {
                        return String.valueOf(params[index]);
                    }
                    return "";
                }
            });
        } catch (ReplaceException e) {
            StackHelper.printStack(e);
        }
        return logInfo;
    }


    /**
     * 输出对应等级的日志
     *
     * @param logStr 日志内容
     * @param level  等级
     */
    private static void logOut(String logStr, LogLevel level) {
        switch (level) {
            case LOG_ERROR:
                Log.e(TAG, logStr);
                break;
            case LOG_INFO:
                Log.i(TAG, logStr);
                break;
            case LOG_WARN:
                Log.w(TAG, logStr);
                break;
            case LOG_DEBUG:
                Log.d(TAG, logStr);
                break;
            default:
                Log.e(TAG, logStr);
        }
        //输出到文件
//        FileLoggerHelper.get().appendSdCardFile(logStr);
    }

    /**
     * 获取日志输出调用的文件和函数
     *
     * @return 返回文件日志输出的函数和位置 例:(xxx.java:12):
     */
    protected static String getLineInfo(int line) {
        StackTraceElement[] traces = new Throwable().getStackTrace();
        int maxLine = Math.min(2 + line, traces.length - 1);
        StackTraceElement ste = traces[maxLine];
        return "(" + ste.getFileName() + ":" + ste.getLineNumber() + "):";
    }
}

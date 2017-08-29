package com.dhcc.workbench.kernel;

import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 日志文件落地帮助类
 * Created by 张立伟 on 2017/5/8.
 */
public class FileLoggerHelper {

    private static FileLoggerHelper fileLoggerHelper=new FileLoggerHelper();

    public static FileLoggerHelper get(){
        return fileLoggerHelper;
    }

    private ReadWriteLock readWriteLock;

    private FileLoggerHelper(){
        readWriteLock=new ReentrantReadWriteLock();
    }

    /**
     * sd卡是否安装
     *
     * @return
     */
    private boolean sdCardIsMount() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 返回sdcard的文件
     *
     * @param path sdcard相对目录 不存在则创建
     * @return
     */
    private File getSdCardFile(String path) {
        if (sdCardIsMount()) {
            File file = new File(Environment.getExternalStorageDirectory(), path);
            if (!file.exists()) {
                createFile(file);
            }
            return file;
        }
        throw new RuntimeException("sdcard 未安装 无法写入");
    }

    /**
     * 日志文件追加内容
     *
     * @param text
     */
    public void appendSdCardFile(String text) {
        Lock lock=readWriteLock.writeLock();
        try {
            lock.lock();
            FileWriter fileWriter = new FileWriter(getSdCardFile(getCurrLogFile()),true);
            fileWriter.write(timeFormatter.format(new Date()));
            fileWriter.write(" ");
            fileWriter.write(text + "\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            StackHelper.printStack(e);
        }finally {
            lock.unlock();
        }
    }

    /**
     * 尝试创建文件
     * @param file
     */
    private void createFile(File file) {
        if (!file.exists()) {
            File folder = file.getParentFile();
            if (!folder.exists()) {
                if (!folder.mkdirs()) {
                    throw new RuntimeException("文件夹" + folder.getAbsolutePath() + "创建失败 可能无权限");
                }
            }
            try {
                if (!file.createNewFile()) {
                    throw new RuntimeException("文件" + file.getAbsolutePath() + "创建失败 可能无权限");
                }
            } catch (IOException e) {
                StackHelper.printStack(e);
                throw new RuntimeException("创建文件产生异常 ：" + e.getMessage());
            }
        }
    }


    private static final SimpleDateFormat nameFormatter=new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat timeFormatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final String LogPath="/padApplogs/";

    /**
     * 获取当前日志文件
     * @return
     */
    private String getCurrLogFile(){
        /**
         * 获取当前编辑文件
         */
//        String fileName=LocalStorageHelper.get().get("current_log_file");
//        //不存在就创建一个新的
//        if(fileName==null){
//            int index=LocalStorageHelper.get().getInteger("log_index")+1;
//            LocalStorageHelper.get().set("log_index",index);
//            fileName="log"+nameFormatter.format(new Date())+"_"+index+".log";
//            LocalStorageHelper.get().addSet("log_file_set",fileName).set("current_log_file",fileName);
//        }
//        //判断file大小 如果超过100k 则换一个新的文件
//        File file=getSdCardFile(LogPath+fileName);
//        double size=FileUtil.getFileSize(file.getAbsolutePath(),FileUtil.SIZE_TYPE_KB);
//        if(size>100){
//            int index=LocalStorageHelper.get().getInteger("log_index")+1;
//            LocalStorageHelper.get().set("log_index",index);
//            fileName="log"+nameFormatter.format(new Date())+"_"+index+".log";
//            LocalStorageHelper.get().addSet("log_file_set",fileName).set("current_log_file",fileName);
//        }
//        deleteLog(file.getParentFile());
//        return LogPath+fileName;
        return "";
    }

    /**
     * 尝试删除过期文件 不保证一定删除
     * @param folder
     */
    private void deleteLog(File folder){
        Calendar c=Calendar.getInstance();
        c.add(Calendar.DATE,-7);//获取七天前的日期
        String fileName="log"+nameFormatter.format(c.getTime())+"_0.log";
        for(File child:folder.listFiles()){
            /**
             * 如果超过日期则删除
             */
            if(child.getName().compareTo(fileName)<=0){
                child.delete();
            }
        }
    }
}

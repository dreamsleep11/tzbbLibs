//  BundleHelper.m
//  Pods
//
//  Created by Dreamsleep on 2017/5/31.
//
//  版权所有：Copyright by DHC Software co.
//  联系方式：liupeijiang@dhcc.com.cn
//  简介:
//  路由:
//  更改历史：
//  |更改人|更改时间|更改内容|代码位置编号|

#import <Foundation/Foundation.h>
#import "BundleHelper.h"

@implementation BundleHelper

+ (NSURL *)getBundlePathWithName:(NSString*)name{
    
    NSFileManager *fileManager = [NSFileManager defaultManager];
    
    //需要存放和读取的document路径
    
    //rootPath
    NSString *rootPath = [NSString stringWithFormat:@"%@/bundle", NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES)[0]];
    
    //jsbundle地址
    NSString *jsPath = [rootPath stringByAppendingPathComponent:[NSString stringWithFormat:@"%@.jsbundle", name]];
    //assets文件夹地址
    NSString *assetsPath = [rootPath stringByAppendingPathComponent:@"assets"];
    
    // JSBundle 不存在则去 main bundle 拷贝
    if(![fileManager fileExistsAtPath:jsPath])
    {
        NSString *jsBundlePath = [[NSBundle mainBundle] pathForResource:name ofType:@"jsbundle"];
        if (![fileManager fileExistsAtPath:rootPath]) {
            [fileManager createDirectoryAtPath:rootPath withIntermediateDirectories:YES attributes:nil error:nil];
        }
        [[NSFileManager defaultManager] copyItemAtPath:jsBundlePath toPath:jsPath error:nil];
        NSLog(@"js已拷贝至Document/bundle: %@",jsPath);
    }
    
    //判断assets是否存在
    BOOL assetsExist = [[NSFileManager defaultManager] fileExistsAtPath:assetsPath];
    //如果已存在
    if(assetsExist){
        NSLog(@"assets已存在: %@",assetsPath);
        //如果不存在
    }else{
        NSString *assetsBundlePath = [NSString stringWithFormat:@"%@/assets", [[NSBundle mainBundle] resourcePath]];
        if (assetsBundlePath) {
            [[NSFileManager defaultManager] copyItemAtPath:assetsBundlePath toPath:assetsPath error:nil];
            NSLog(@"assets已拷贝至Document: %@",assetsPath);
        }
    }
    return [NSURL URLWithString:jsPath];
}

@end

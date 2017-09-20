//
//  LocalPersistenceHelper.m
//  Pods
//
//  Created by Dreamsleep on 2017/9/6.
//
//

#import "LocalPersistenceBridges.h"

@implementation LocalPersistenceBridges
RCT_EXPORT_MODULE();

//存储数据到本地
RCT_EXPORT_METHOD(pushData:(NSString*)key value:(NSString*)value )
{
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    [userDefaults setObject:value forKey:key];
}

//获得本地化数据
RCT_EXPORT_METHOD(getData:(NSString*)key callback:(RCTResponseSenderBlock)callback)
{
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    if(callback){
        if( [userDefaults objectForKey:key]){
            callback(@[[NSNull null], [userDefaults objectForKey:key]]);}else{
                callback(@[[NSNull null], [NSNull null]]);
            }
    }
}

//清空数据
RCT_EXPORT_METHOD(clear)
{
    NSUserDefaults *defatluts = [NSUserDefaults standardUserDefaults];
    NSDictionary *dictionary = [defatluts dictionaryRepresentation];
    for(NSString *key in [dictionary allKeys]){
        [defatluts removeObjectForKey:key];
        [defatluts synchronize];
    }
}

//删除指定key数据
RCT_EXPORT_METHOD(remove:(NSString*)key){
    NSUserDefaults *defatluts = [NSUserDefaults standardUserDefaults];
    [defatluts removeObjectForKey:key];
}
@end

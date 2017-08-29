//
//  ReactPublicResources.h
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
#import <React/RCTBridge.h>
@interface ReactPublicResources : NSObject
@property (nonatomic,strong) NSString* name;
@property (nonatomic, strong)RCTBridge *bridge;
+(ReactPublicResources*)defaultManager;
+(id)allocWithZone:(struct _NSZone *)zone;
@end

//
//  ConfigManager.h
//  ReactLoader
//
//  Created by sxq on 2017/1/14.
//  Copyright © 2017年 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface ConfigItem : NSObject
@property (nonatomic, copy) NSString *project;
@property (nonatomic, copy) NSString *bundle;
@property (nonatomic, copy) NSString *url;
@property (nonatomic, copy) NSString *port;
@property (nonatomic, copy) NSString *file;
@property (nonatomic, copy) NSString *desc;
@property (nonatomic, copy) NSString *timeStep;

- (instancetype)initWithProject:(NSString*)project bundle:(NSString*)bundle url:(NSString*)url port:(NSString*)port file:(NSString*)file desc:(NSString*)desc;

- (NSDictionary*)dic;

- (NSString*)urlString;

- (NSString*)urlString:(NSString*)url port:(NSString*)port file:(NSString*)file;
@end

@interface ConfigManager : NSObject
@property (nonatomic, strong, readonly) NSMutableArray<ConfigItem*> *configList;
@property (nonatomic, strong, readonly) ConfigItem *defaultConfig;

+ (instancetype)manager;
- (void) addConfig:(ConfigItem*)config;
- (void) setAsDefaultConfig:(ConfigItem *)defaultConfig;
- (void) saveConfig:(ConfigItem*)config;
- (void) deleteConfig:(ConfigItem*)config;
@end

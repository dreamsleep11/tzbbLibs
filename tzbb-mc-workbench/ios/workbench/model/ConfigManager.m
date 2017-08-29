//
//  ConfigManager.m
//  ReactLoader
//
//  Created by sxq on 2017/1/14.
//  Copyright © 2017年 Facebook. All rights reserved.
//

#import "ConfigManager.h"
#import <objc/runtime.h>

#define DEFAUAT @"Defalut"
#define CONFIGS @"Configs"

@implementation ConfigItem

- (instancetype)initWithProject:(NSString*)project bundle:(NSString*)bundle url:(NSString*)url port:(NSString*)port file:(NSString*)file desc:(NSString*)desc{
  self = [super init];
  if (self) {
    self.project = project == nil ? @"" : project;
    self.url = url == nil ? @"" : url;
    self.port = port == nil ? @"" : port;
    self.file = file == nil ? @"" : file;
    self.desc = desc == nil ? @"" : desc;
    self.bundle = bundle == nil ? @"" :bundle;
    self.timeStep = [NSString stringWithFormat:@"%.0f", [[NSDate date] timeIntervalSince1970]];
  }
  return self;
}

- (instancetype)initWithDic:(NSDictionary*)dic{
  self = [super init];
  if (self) {
    [self setValuesForKeysWithDictionary:dic];
  }
  return self;
}

- (NSDictionary*)dic{
  NSMutableDictionary *dic = [NSMutableDictionary dictionary];
  unsigned int count;
  objc_property_t *properties = class_copyPropertyList([self class], &count);
  for (int i = 0; i < count; i++) {
    objc_property_t property = properties[i];
    NSString *name = [NSString stringWithUTF8String:property_getName(property)];
    [dic setObject:[self valueForKey:name] forKey:name];
  }
  return dic;
}
- (NSString*)urlString:(NSString*)url port:(NSString*)port file:(NSString*)file{
    return [NSString stringWithFormat:@"http://%@:%@/%@.bundle?platform=ios&dev=true&hot=true&minify=false",url, port, file];
}
- (NSString*)urlString{
    return [NSString stringWithFormat:@"http://%@:%@/%@.bundle?platform=ios&dev=true&hot=true&minify=false", self.url, self.port, self.file];
}

- (void)setValue:(id)value forUndefinedKey:(NSString *)key{
  // 容错
}

@end

@interface ConfigManager ()
@property (nonatomic, strong) NSDictionary *configs;
@property (nonatomic, strong) ConfigItem *defaultConfig;
@property (nonatomic, copy) NSString *filePath;
@end

@implementation ConfigManager

#pragma mark - Share Instance
+ (instancetype)manager {
  static ConfigManager *manager;
  static dispatch_once_t onceToken;
  dispatch_once(&onceToken, ^{
    manager = [[self alloc] init];
    NSString *fileName = @"Config.plist";
    NSString *path = [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) objectAtIndex:0];
    manager.filePath = [path stringByAppendingPathComponent:fileName];
  });
  return manager;
}

#pragma mark - Getter 

// 读取本地保存配置
- (NSDictionary *)configs {
  if (!_configs) {
    _configs = [NSDictionary dictionaryWithContentsOfFile:self.filePath];
    if (_configs==nil) {
        _configs = @{DEFAUAT:@"",
                     CONFIGS:@{
                             @"init":@{@"project":@"测试",
                                    @"bundle":@"Test",
                                    @"url":@"localhost",
                                    @"port":@"8081",
                                    @"file":@"index.ios",
                                    @"desc":@"This the first config",
                                    @"timeStep":@"init"}
                             }
                     }.mutableCopy;
      [_configs writeToFile:self.filePath atomically:YES];
    }
  }
  return _configs;
}

// 获取配置列表
- (NSMutableArray<ConfigItem *> *)configList{
    NSMutableArray *conArr = [NSMutableArray array];
    NSArray *sortArray = [[self.configs[CONFIGS] allKeys] sortedArrayUsingComparator:^NSComparisonResult(id  _Nonnull obj1, id  _Nonnull obj2) {
        return ((NSString*)obj1).integerValue > ((NSString*)obj2).integerValue;
    }];
    for (NSString *timeStep in sortArray) {
        NSDictionary *config = self.configs[CONFIGS][timeStep];
        ConfigItem *item = [[ConfigItem alloc] initWithDic:config];
        [conArr addObject:item];
    }
    return conArr;
}

// 获取当前配置
- (ConfigItem *)defaultConfig {
  if (!_defaultConfig) {
    id def = self.configs[DEFAUAT];
    if ([def isKindOfClass:[NSString class]]) {
      if ([self.configs[CONFIGS] allKeys].count > 0) {
          NSArray *sortArray = [[self.configs[CONFIGS] allKeys] sortedArrayUsingComparator:^NSComparisonResult(id  _Nonnull obj1, id  _Nonnull obj2) {
              return ((NSString*)obj1).integerValue > ((NSString*)obj2).integerValue;
          }];
          NSString *key = [sortArray objectAtIndex:0];
        def = self.configs[CONFIGS][key];
      } else {
        return nil;
      }
    }
      [self.configs setValue:def forKey:DEFAUAT];
    _defaultConfig = [[ConfigItem alloc] initWithDic:(NSDictionary *)def];
  }
  return _defaultConfig;
}

// 添加配置
- (void) addConfig:(ConfigItem*)config{
    NSDictionary *dic = [config dic];
    NSMutableDictionary *conDic = [self.configs[CONFIGS] mutableCopy];
    [conDic setObject:dic forKey:config.timeStep];
    [self.configs setValue:conDic forKey:CONFIGS];
    [self.configs writeToFile:self.filePath atomically:YES];
}


// 设为默认配置
- (void)setAsDefaultConfig:(ConfigItem *)config{
    self.defaultConfig = config;
    [self.configs setValue:[config dic] forKey:DEFAUAT];
    NSArray *keyMap = [self.configs[CONFIGS] allKeys];
    if (![keyMap containsObject:config.timeStep]) {
        [self addConfig:config];
    }
}

// 更改
- (void)saveConfig:(ConfigItem*)config{
    NSMutableDictionary *conDic = [self.configs[CONFIGS] mutableCopy];
    [conDic setValue:[config dic] forKey:config.timeStep];
    [self.configs setValue:conDic forKey:CONFIGS];
    [self.configs writeToFile:self.filePath atomically:YES];
}

// 删除
- (void)deleteConfig:(ConfigItem*)config{
    self.defaultConfig = nil;
    [self.configs setValue:@"" forKey:DEFAUAT];
    [self.configs[CONFIGS] removeObjectForKey:config.timeStep];
    [[NSNotificationCenter defaultCenter] postNotificationName:@"ConfigsHasChanged" object:nil];
    [self.configs writeToFile:self.filePath atomically:YES];
}

@end

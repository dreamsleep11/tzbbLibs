//
//  NetHelper.h
//  Pods
//
//  Created by Dreamsleep on 2017/7/14.
//
//

#import <Foundation/Foundation.h>

@interface NetHelper : NSObject

+(void)POST:(NSString*_Nonnull)url params:(NSDictionary*_Nullable)params uploadFiles:(NSArray*_Nullable)uploadFiles  progress:(nullable void (^)(NSProgress * _Nonnull))progress success:(void (^_Nonnull)(NSURLSessionDataTask * _Nullable task, id _Nullable responseObject))success
    failure:(void (^_Nonnull)(NSURLSessionDataTask * _Nullable task, NSError * _Nullable error))failure;

@end

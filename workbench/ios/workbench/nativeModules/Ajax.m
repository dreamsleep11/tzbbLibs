//
//  Ajax.m
//  Pods
//
//  Created by Dreamsleep on 2017/7/14.
//
//

#import "Ajax.h"
#import "NetHelper.h"
@implementation Ajax
RCT_EXPORT_MODULE(@"Ajax")

RCT_EXPORT_METHOD(POST:(NSString*)url params:(NSDictionary*)params uploadFiles:(NSArray*)uploadFiles  progress:(RCTResponseSenderBlock)progress success:(RCTResponseSenderBlock)success
                  failure:(RCTResponseSenderBlock)failure{
                      [NetHelper POST:url params:params  uploadFiles:uploadFiles progress:^(NSProgress * _Nonnull uploadProgress) {
                          if(progress)
                              progress(@[[NSNull null], [[NSString alloc] initWithFormat:@"%lld",uploadProgress.completedUnitCount],[[NSString alloc] initWithFormat:@"%lld",uploadProgress.totalUnitCount]]);
                      } success:^(NSURLSessionDataTask *task, id responseObject) {
                          NSString * result= [[NSString alloc] initWithData:responseObject encoding:(NSUTF8StringEncoding)];
                          NSLog(@"服务器返回数据：%@",result);
                        if(success)
                          success(@[[NSNull null],result]);
                      } failure:^(NSURLSessionDataTask *task, NSError *error) {
                          if(failure){
                              failure(@[[NSNull null],[[NSString alloc] initWithFormat:@"Ajax Error :%@",error]]);
                          }
                      }];
                  })
@end

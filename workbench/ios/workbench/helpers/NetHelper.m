//
//  NetHelper.m
//  Pods
//
//  Created by Dreamsleep on 2017/7/14.
//
//

#import "NetHelper.h"
#import "AFNetworking.h"
@implementation NetHelper
+(void)POST:(NSString*_Nonnull)url params:(NSDictionary*_Nullable)params uploadFiles:(NSArray*_Nullable)uploadFiles  progress:(nullable void (^)(NSProgress * _Nonnull))progress success:(void (^_Nonnull)(NSURLSessionDataTask * _Nullable task, id _Nullable responseObject))success
    failure:(void (^_Nonnull)(NSURLSessionDataTask * _Nullable task, NSError * _Nullable error))failure{
    NSLog(@"发送数据：%@",params);
    AFHTTPSessionManager *manager = [AFHTTPSessionManager manager];
    manager.responseSerializer = [AFHTTPResponseSerializer serializer];
    [manager POST:url parameters:params constructingBodyWithBlock:^(id<AFMultipartFormData>  _Nonnull formData) {
        //序列化上传文件
        NSLog(@"序列化上传文件");
        if(uploadFiles){
            for( int i=0;i<[uploadFiles count];i++){
                NSMutableDictionary * fileDic=[uploadFiles objectAtIndex:i];
                NSLog(@"文件信息：%@",fileDic);
                NSString*key=[fileDic objectForKey:@"name"];
                NSString * path=[fileDic objectForKey:@"path"];
                NSString *mime=[fileDic objectForKey:@"mime"];
                UIImage *fileFromUrl=[[UIImage alloc]initWithContentsOfFile:path];
                if ([fileFromUrl isKindOfClass:[UIImage class]]) {
                    NSData* fileData = [self shrinkImage:fileFromUrl size:100];
                    [formData appendPartWithFileData:fileData name:key fileName:path mimeType:mime];
                }
            }
        }
    } progress:^(NSProgress * _Nonnull uploadProgress) {
        //进度[文件上传时使用]
        if(progress){
            NSLog(@"%lf",1.0 *uploadProgress.completedUnitCount / uploadProgress.totalUnitCount);
            progress(uploadProgress);
        }
    } success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        if(success){
            NSLog(@"请求成功。");
            success(task,responseObject);
        }
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        if(failure){
            failure(task,error);
        }
    }];
}
/**
 *  压缩图片
 */
+ (NSData*)shrinkImage:(UIImage*)img size:(long)size
{
    NSData* imageData = [[NSData alloc] init];
    for (float compression = 1.0; compression >= 0.0; compression -= .1) {
        imageData = UIImageJPEGRepresentation(img, compression);
        NSInteger imageLength = imageData.length;
        //        NSLog(@"%l", [imageData length]);
        if (imageLength / 1024.000000000f < size) {
            break;
        }
    }
    return imageData;
}
@end

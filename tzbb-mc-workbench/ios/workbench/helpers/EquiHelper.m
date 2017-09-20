//
//  EquiHelper.m
//  Pods
//设备信息辅助类
//  Created by Dreamsleep on 2017/9/6.
//
//

#import "EquiHelper.h"

@implementation EquiHelper
/**
 *  获得设备唯一标识符
 *
 *  @return NSString
 */
+ (NSString*)getIdentifier
{
    NSString* identifierForVendor = [NSString stringWithFormat:@"%@-%@", [[UIDevice currentDevice].identifierForVendor UUIDString], [[NSBundle mainBundle] bundleIdentifier]];
    NSLog(@"identifierForVendor=%@", identifierForVendor);
    return identifierForVendor;
}
/**
 *  获得设备名称
 *
 *  @return <#return value description#>
 */
+ (NSString*)getDevName
{
    UIDevice* device = [UIDevice currentDevice];
    return device.name;
}

@end

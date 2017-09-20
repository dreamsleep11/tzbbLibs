//
//  HardwareBridges.m
//  Pods
//
//  Created by Dreamsleep on 2017/9/6.
//
//

#import "HardwareBridges.h"
#import "EquiHelper.h"
@implementation HardwareBridges

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(getDevToken:(RCTResponseSenderBlock)success{
                NSString * result=[EquiHelper getIdentifier];
                success(@[[NSNull null],result]);
            })

@end

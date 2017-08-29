//
//  workbench.m
//  workbench
//
//  Created by Dreamsleep on 2017/7/13.
//  Copyright © 2017年 tzbb. All rights reserved.
//

#import "Workbench.h"
#import "DevViewController.h"
#import "ReactMainViewController.h"
@implementation Workbench
-(void) startByReactNative:(UIWindow*)window launchOptions:(NSDictionary*)launchOptions{
        #if DEBUG
            DevViewController *dev=[[DevViewController alloc] init];
            window.rootViewController = dev;
        #else
            ReactMainViewController *mainView=[[ReactMainViewController alloc] init];
            window.rootViewController = mainView;
        #endif
}
@end

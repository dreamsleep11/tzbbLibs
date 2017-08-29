//  ReactMainViewController.m
//  Pods
//
//  Created by Dreamsleep on 2017/5/31.
//
//  版权所有：Copyright by DHC Software co.
//  联系方式：liupeijiang@dhcc.com.cn
//  简介:发布主React画布
//  路由:
//  更改历史：
//  |更改人|更改时间|更改内容|代码位置编号|
#import "RCTRootView.h"
#import "ReactMainViewController.h"
#import "BundleHelper.h"
#import "ReactPublicResources.h"
@interface ReactMainViewController ()
@property (nonatomic, strong)RCTBridge *bridge;
@end

@implementation ReactMainViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor=UIColor.whiteColor;
    [self createReactVIew];
    // Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


#pragma mark - 创建ReactView
- (void)createReactVIew {
    //启动已有资源
    NSLog(@"创建React主显示view");
    NSString *infoPath = [[NSBundle mainBundle] pathForResource:@"Info" ofType:@"plist"];
    NSDictionary *info = [NSDictionary dictionaryWithContentsOfFile:infoPath][@"PI"];
    NSURL *jsCodeLocation = [BundleHelper getBundlePathWithName:@"index.ios"];
    self.bridge = [[RCTBridge alloc] initWithBundleURL:jsCodeLocation moduleProvider:nil launchOptions:nil];
    RCTRootView *rootView = [[RCTRootView alloc] initWithBridge:self.bridge moduleName:info[@"BN"] initialProperties:nil];
    ReactPublicResources *defaultManagerSingleton =[ReactPublicResources defaultManager];
    defaultManagerSingleton.bridge =self.bridge;
    rootView.backgroundColor = [[UIColor alloc] initWithRed:1.0f green:1.0f blue:1.0f alpha:1];
    self.view = rootView;
    
    //检查更新【兼容性检测】
    //下载资源
    //下次启动 启动新版本 双存储切换
    //基座版本出现更新
    
    
}

#pragma mark - 检查资源包更新
-(Boolean) checkBundle{
    return YES;
}
#pragma mark - 更新资源包
-(void)updateBundle:(Boolean)JM{
    //静默更新
    
    //非静默更新
}
#pragma mark - 检查基座更新
-(Boolean) checkSubstrate{
    return YES;
}
#pragma mark - 更新基座
-(void)updateSubstrate{
    
}
/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end

//  DevViewController.m
//  Pods
//
//  Created by Dreamsleep on 2017/5/31.
//
//  版权所有：Copyright by DHC Software co.
//  联系方式：liupeijiang@dhcc.com.cn
//  简介: 测试主类
//  路由:
//  更改历史：
//  |更改人|更改时间|更改内容|代码位置编号|

#import "DevViewController.h"
#import "XLForm.h"
#import "XLFormButtonCell.h"
#import "RCTRootView.h"
#import "RCTDevMenu.h"
#import "ConfigManager.h"
#import "ReactPublicResources.h"
#import "RCTBridge.h"
@interface DevViewController ()
@property (nonatomic, strong) UIViewController *reactViewController;
@property (nonatomic, strong)  XLFormDescriptor * formDescriptor;
@end

NSString *const kTag = @"bundle";
NSString *const kFile = @"file";
NSString *const kIP= @"ip";
NSString *const kPort= @"port";
NSString *const kSub= @"sub";

@implementation DevViewController

-(id)initForm
{
     self.formDescriptor = [XLFormDescriptor formDescriptorWithTitle:@"测试基座"];
    XLFormSectionDescriptor * section;
    XLFormRowDescriptor * row;
    
    self.formDescriptor.assignFirstResponderOnShow = YES;
    
    // Basic Information - Section
    section = [XLFormSectionDescriptor formSectionWithTitle:@"应用信息配置"];
    section.footerTitle = @"输入相关字段，点击链接按钮";
    [self.formDescriptor addFormSection:section];
    
    // Name
    row = [XLFormRowDescriptor formRowDescriptorWithTag:kTag rowType:XLFormRowDescriptorTypeText title:@"标志"];
    row.required = YES;
    row.value=@"test";
    [section addFormRow:row];
    
    // Email
    row = [XLFormRowDescriptor formRowDescriptorWithTag:kIP rowType:XLFormRowDescriptorTypeText title:@"地址"];
    [row addValidator:[XLFormValidator emailValidator]];
    row.value=@"localhost";
     row.required = YES;
    [section addFormRow:row];
    
    row = [XLFormRowDescriptor formRowDescriptorWithTag:kFile rowType:XLFormRowDescriptorTypeText title:@"资源文件"];
    [row addValidator:[XLFormValidator emailValidator]];
    row.value=@"index.ios";
    row.required = YES;
    [section addFormRow:row];
    
    
    // Twitter
    row = [XLFormRowDescriptor formRowDescriptorWithTag:kPort rowType:XLFormRowDescriptorTypeTwitter title:@"端口"];
    row.value=@"8081";
     row.required = YES;
    [section addFormRow:row];
    [self.formDescriptor addFormSection:section];
    
    
    section = [[XLFormSectionDescriptor alloc] init];
    // Button
    XLFormRowDescriptor * buttonRow = [XLFormRowDescriptor formRowDescriptorWithTag:kSub rowType:XLFormRowDescriptorTypeButton title:@"连接"];
    buttonRow.action.formSelector = @selector(didTouchButton:);
     [section addFormRow:buttonRow];
    [self.formDescriptor addFormSection:section];
//
  

    return [super initWithForm:self.formDescriptor];
}

#pragma mark 连接按钮点击事件
-(void)didTouchButton:(XLFormRowDescriptor *)sender
{
    [self deselectFormRow:sender];
  
    NSURL *jsCodeLocation = [NSURL URLWithString:[[[ConfigManager manager] defaultConfig] urlString:[[self.formDescriptor formValues] objectForKey:kIP] port:[[self.formDescriptor formValues] objectForKey:kPort] file:[[self.formDescriptor formValues] objectForKey:kFile]]];
    RCTBridge *bridge = [[RCTBridge alloc] initWithBundleURL:jsCodeLocation
                                              moduleProvider:^NSArray<id<RCTBridgeModule>> *{
                                                  RCTDevMenu *menu = [[RCTDevMenu alloc] init];
                                                  [menu addItem:[RCTDevMenuItem buttonItemWithTitle:@"Exit" handler:^{
                                                      [self.reactViewController dismissViewControllerAnimated:YES completion:nil];
                                                  }]];
                                                  return @[menu];
                                              } launchOptions:nil];

    RCTRootView *rootView = [[RCTRootView alloc] initWithBridge:bridge moduleName:[[self.formDescriptor formValues] objectForKey:kTag] initialProperties:nil];
    ReactPublicResources *defaultManagerSingleton =[ReactPublicResources defaultManager];
    defaultManagerSingleton.bridge =bridge;
//    RCTRootView *rootView=[[RCTRootView alloc] initWithBundleURL:jsCodeLocation moduleName:[[self.formDescriptor formValues] objectForKey:kTag]  initialProperties:nil launchOptions:nil];
    rootView.backgroundColor = [[UIColor alloc] initWithRed:1.0f green:1.0f blue:1.0f alpha:1];
    self.reactViewController = [[UIViewController alloc] init];
    self.reactViewController.view = rootView;
    [self presentViewController:self.reactViewController animated:YES completion:nil];
}

-(void)viewDidLoad
{
    [super viewDidLoad];
    [self initForm];
//    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemSave target:self action:@selector(savePressed:)];
}


-(void)savePressed:(UIBarButtonItem * __unused)button
{
   
}

@end

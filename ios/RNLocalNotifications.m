//
//  RNLocalNotifications.m
//  LocalNotifications
//
//  Created by Stefano Pezzino on 5/31/20.
//  Copyright © 2020 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(RNLocalNotifications, NSObject)
    RCT_EXTERN_METHOD(presentLocalNotification:
    (RCTPromiseResolveBlock)resolve
    rejecter:(RCTPromiseRejectBlock)reject
)

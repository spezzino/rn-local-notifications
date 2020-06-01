//
//  RNLocalNotifications.m
//  LocalNotifications
//
//  Created by Stefano Pezzino on 5/31/20.
//  Copyright © 2020 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>

@interface RCT_EXTERN_MODULE(LocalNotifications, NSObject)

RCT_EXTERN_METHOD(presentLocalNotification:
                  (NSDictionary)params
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject
                  );

RCT_EXTERN_METHOD(requestAuthorization:
                  (NSStringArray)authOptions
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject
                  );

@end

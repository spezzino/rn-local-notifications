//
//  RNLocalNotifications.swift
//  LocalNotifications
//
//  Created by Stefano Pezzino on 5/31/20.
//  Copyright © 2020 Facebook. All rights reserved.
//

import Foundation

@objc(RNLocalNotifications)
class RNLocalNotifications: NSObject {
    
    @objc(presentLocalNotifications:resolver:rejecter:)
    func presentLocalNotifications(
        resolver resolve: RCTPromiseResolveBlock,
        rejecter reject:RCTPromiseRejectBlock) -> Void {
        resolve(true)
    }

    @objc(requestAuthorization:resolver:rejecter:)
    func requestAuthorization(
        options: StringA,
        resolver resolve: RCTPromiseResolveBlock,
        rejecter reject:RCTPromiseRejectBlock) -> Void {
        let notificationCenter = UNUserNotificationCenter.current()
        
        let authOptions = []
        if(options)
        
        notificationCenter.requestAuthorization(options: options) {
            (didAllow, error) in
            if error {
                reject("Error", "requestAuthorization", error)
            }
            resolve(didAllow)
        }
    }
}

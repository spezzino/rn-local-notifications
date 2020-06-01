//
//  RNLocalNotifications.swift
//  RNLocalNotifications
//
//  Created by Stefano Pezzino on 6/1/20.
//  Copyright © 2020 Facebook. All rights reserved.
//

import Foundation

@objc(LocalNotifications)
class RNLocalNotifications: RCTEventEmitter {
    public static var sharedInstance: RNLocalNotifications?
    let notificationCenter = UNUserNotificationCenter.current()
    
    override init() {
        super.init()
        RNLocalNotifications.sharedInstance = self
    }
    
    override func supportedEvents() -> [String]! {
        return ["onNotification"]
    }
    
    @objc(presentLocalNotification:resolver:rejecter:)
    func presentLocalNotification(
        _ params: Dictionary<String, Any>,
        resolver resolve: @escaping RCTPromiseResolveBlock,
        rejecter reject: @escaping RCTPromiseRejectBlock) -> Void {
        
        RNLocalNotifications.sharedInstance!.notificationCenter.getNotificationSettings { (settings) in
          if settings.authorizationStatus != .authorized {
            reject("presentLocalNotifications", "Notifications are not allowed. Did you call requestAuthorization?", nil)
            return
          }
        }
        
        let content = UNMutableNotificationContent()
        
        content.title = "Sample Title"
        content.body = "This is example how to create"
        content.sound = UNNotificationSound.default
        content.badge = 1
        content.userInfo = [:]
        
        let date = Date(timeIntervalSinceNow: 5)
        let triggerDate = Calendar.current.dateComponents([.year,.month,.day,.hour,.minute,.second,], from: date)
        let trigger = UNCalendarNotificationTrigger(dateMatching: triggerDate, repeats: false)
        
        let identifier = "Local Notification"
        let request = UNNotificationRequest(identifier: identifier, content: content, trigger: trigger)

        RNLocalNotifications.sharedInstance!.notificationCenter.add(request) { (error) in
            if let error = error {
                reject("presentLocalNotifications", "Error presenting local notification", error)
                return
            }
        }
        
        resolve(true)
    }

    @objc(requestAuthorization:resolver:rejecter:)
    func requestAuthorization(
        _ permissions: [String],
        resolver resolve: @escaping RCTPromiseResolveBlock,
        rejecter reject: @escaping RCTPromiseRejectBlock) -> Void {
        
        var options: UNAuthorizationOptions = []
        for permission in permissions {
            switch permission {
            case "sound":
                options.insert(.sound)
            case "badge":
                options.insert(.badge)
            case "alert":
                options.insert(.alert)
            default: break
            }
        }
        
        RNLocalNotifications.sharedInstance!.notificationCenter.requestAuthorization(options: options) {
            (didAllow, error) in
            if (error != nil) {
                reject("requestAuthorization", "error", error)
            }
            resolve(didAllow)
        }
    }
    
    func onNotification(_ identifier: String,
                        notificationReceived notification: UNNotificationContent) -> Void {
        self.sendEvent(withName: "onNotification", body: [
            "identifier": identifier,
            "title": notification.title,
            "subtitle": notification.subtitle,
            "body": notification.body,
            "badge": notification.badge ?? 0,
            "data": notification.userInfo
        ])
    }
}

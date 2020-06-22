//
//  RNLocalNotifications.swift
//  RNLocalNotifications
//
//  Created by Stefano Pezzino on 6/1/20.
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
          } else {
            let content = UNMutableNotificationContent()
            
            if let title = params["title"] as? String {
                content.title = title
            }
            if let body = params["body"] as? String {
                content.body = body
            }
            if let playSound = params["playSound"] {
                if(playSound as! Bool){
                    content.sound = UNNotificationSound.default
                }
            }
            if let badge = params["badge"] {
                content.badge = NSNumber(value: badge as! Int)
            }
            if let data = params["data"] {
                content.userInfo = data as! Dictionary
            }else{
                content.userInfo = [:]
            }
            
            var identifier = "\(Int.random(in: 1 ..< 10000000))"
            if let id = params["id"] {
                identifier = "\(NSNumber(value: id as! Int))"
            }
            if let category = params["category"] {
                content.categoryIdentifier = category as! String
            }
            
            let date = Date(timeIntervalSinceNow: 1)
            let triggerDate = Calendar.current.dateComponents([.year,.month,.day,.hour,.minute,.second,], from: date)
            let trigger = UNCalendarNotificationTrigger(dateMatching: triggerDate, repeats: false)
            
            let request = UNNotificationRequest(identifier: identifier, content: content, trigger: trigger)

            RNLocalNotifications.sharedInstance!.notificationCenter.add(request) { (error) in
                if let error = error {
                    reject("presentLocalNotifications", "Error presenting local notification", error)
                } else {
                    resolve(nil)
                }
            }
          }
        }
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
    
    @objc(removeAllDeliveredNotifications:rejecter:)
    func removeAllDeliveredNotifications(_ resolve: @escaping RCTPromiseResolveBlock,
    rejecter reject: @escaping RCTPromiseRejectBlock) -> Void {
        RNLocalNotifications.sharedInstance!.notificationCenter.removeAllDeliveredNotifications()
        resolve(nil)
    }
    
    func onNotification(_ identifier: String,
                        notificationReceived notification: UNNotificationContent) -> Void {
        self.sendEvent(withName: "onNotification", body: [
            "identifier": identifier,
            "title": notification.title,
//            "subtitle": notification.subtitle,
            "body": notification.body,
            "badge": notification.badge ?? 0,
            "data": notification.userInfo,
            "category": notification.categoryIdentifier
        ])
    }
}

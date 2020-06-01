//
//  RNLocalNotificationsAppDelegate.swift
//  RNLocalNotifications
//
//  Created by Stefano Pezzino on 6/1/20.
//  Copyright © 2020 Facebook. All rights reserved.
//

import Foundation
import UIKit
import UserNotifications

extension NSObject: UIApplicationDelegate, UNUserNotificationCenterDelegate{
    
    public func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        RNLocalNotifications.init()
        RNLocalNotifications.sharedInstance!.notificationCenter.delegate = self
        return true
    }
    
    public func userNotificationCenter(_ center: UNUserNotificationCenter, willPresent notification: UNNotification, withCompletionHandler completionHandler: @escaping (UNNotificationPresentationOptions) -> Void) {
        var options: UNNotificationPresentationOptions = []
        center.getNotificationSettings { settings in
            if settings.alertSetting == .enabled {
                options.insert(.alert)
            }
            if(settings.badgeSetting == .enabled) {
                options.insert(.badge)
            }
            if(settings.soundSetting == .enabled) {
                options.insert(.sound)
            }
            
            completionHandler(options)
        }
    }
    
    public func userNotificationCenter(_ center: UNUserNotificationCenter, didReceive response: UNNotificationResponse, withCompletionHandler completionHandler: @escaping () -> Void) {
        RNLocalNotifications.sharedInstance!.onNotification(response.notification.request.identifier, notificationReceived: response.notification.request.content)
        completionHandler()
    }
}

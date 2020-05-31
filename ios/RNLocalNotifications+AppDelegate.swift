//
//  RNLocalNotifications+AppDelegate.swift
//  LocalNotifications
//
//  Created by Stefano Pezzino on 5/31/20.
//  Copyright © 2020 Facebook. All rights reserved.
//

import UIKit

class RNLocalNotifications_AppDelegate: AppDelegate, UNUserNotificationCenterDelegate {
    let notifCenter = UNUserNotificationCenter.current()

    func requestAuthForLocalNotifications() {
        notifCenter.delegate = self
        notifCenter.requestAuthorization(options: [.alert, .sound]) { (granted, error) in
            if error != nil {
                // Something went wrong
            }
        }
    }

}

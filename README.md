# react-native-local-notifications

## Getting started

`$ npm install react-native-local-notifications --save`

### Mostly automatic installation

`$ react-native link react-native-local-notifications`

## Usage
```javascript
import LocalNotifications from 'react-native-local-notifications';

// TODO: What to do with the module?
LocalNotifications;
```

## Install ios
Open Xcode workspace located inside ios folder and add empty Swift file if you don't have at least one:
Select File/New/File...
Choose Swift file and click Next.
Name it however you want, select your application target and create it.
Accept to create Objective-C bridging header.

### TODO
- Documentation
- Scheduled notifications
- Cancel scheduled notifications
- Refactor android code
- Check param types (android & ios)
- Typescript
- Split android/ios js code, to throw unsupported method if not implemented natively
- Implement subtext, images (feature complete notifications for both platforms)
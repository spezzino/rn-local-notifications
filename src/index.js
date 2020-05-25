import { NativeModules, NativeEventEmitter } from 'react-native';

const { LocalNotifications } = NativeModules;

LocalNotifications.prototype.setOnNotificationListener = (callback) => {
	const eventEmitter = new NativeEventEmitter(LocalNotifications);
	eventEmitter.addListener('onNotification', (event) => {
	   callback(event);
	});
}

export default LocalNotifications;

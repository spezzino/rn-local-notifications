package xyz.paraguaydev.reactnative.localnotifications;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import xyz.paraguaydev.reactnative.localnotifications.utils.ReadableMapUtils;

public class LocalNotificationsModule extends ReactContextBaseJavaModule implements ActivityEventListener, Application.ActivityLifecycleCallbacks {

    private final ReactApplicationContext reactContext;
    private final Long DEFAULT_VIBRATION = 300L;

    public LocalNotificationsModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;

        reactContext.addActivityEventListener(this);

        Application applicationContext = (Application) reactContext.getApplicationContext();
        applicationContext.registerActivityLifecycleCallbacks(this);
        this.registerNotificationsReceiveNotificationActions();
    }

    @Override
    public String getName() {
        return "LocalNotifications";
    }

    private void registerNotificationsReceiveNotificationActions() {
        IntentFilter intentFilter = new IntentFilter();

        getReactApplicationContext().registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getBundleExtra("notification");

                // Notify the action.
                LocalNotificationsModule.this.onNotificationReceived(bundle);

                // Dismiss the notification popup.
                NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
                int notificationID = Integer.parseInt(bundle.getString("id"));
                manager.cancel(notificationID);
            }
        }, intentFilter);
    }

    private Bundle getBundleFromIntent(Intent intent) {
        Bundle bundle = null;
        if (intent.hasExtra("notification")) {
            bundle = intent.getBundleExtra("notification");
        }
        return bundle;
    }

    @Override
    public void onNewIntent(Intent intent) {
        Bundle bundle = this.getBundleFromIntent(intent);
        if (bundle != null) {
            bundle.putBoolean("foreground", false);
            intent.putExtra("notification", bundle);
            this.onNotificationReceived(bundle);
        }
    }

    private void sendEvent(String eventName,
                           @Nullable WritableMap params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }

    private void onNotificationReceived(Bundle bundle) {
        try {
            WritableMap params = ReadableMapUtils.convertJsonToMap(LocalNotificationAttributes.fromBundle(bundle).toJSONObject());
            sendEvent("onNotification", params);
        } catch (JSONException e) {
            // TODO promise reject
        }
    }

    @ReactMethod
    public void presentLocalNotification(ReadableMap args, Callback callback) {
        Resources res = reactContext.getResources();
        String packageName = reactContext.getPackageName();
        LocalNotificationAttributes attributes = new LocalNotificationAttributes();

        if (args.hasKey("notificationId")) {
            attributes.setNotificationId(args.getInt("notificationId"));
        } else {
            attributes.setNotificationId(new Random().nextInt());
        }

        if (args.hasKey("channelId")) {
            attributes.setChannelId(args.getString("channelId"));
        }

        if (args.hasKey("channelName")) {
            attributes.setChannelName(args.getString("channelName"));
        }

        if (args.hasKey("channelDescription")) {
            attributes.setChannelDescription(args.getString("channelDescription"));
        }

        if (args.hasKey("autoCancel")) {
            attributes.setAutoCancel(args.getBoolean("autoCancel"));
        }

        if (args.hasKey("category")) {
            attributes.setCategory(args.getString("category"));
        }

        if (args.hasKey("color")) {
            attributes.setHexColor(args.getString("color"));
        }

        if (args.hasKey("title")) {
            attributes.setTitle(args.getString("title"));
        }

        if (args.hasKey("text")) {
            attributes.setText(args.getString("text"));
        }

        if (args.hasKey("extraData")) {
            Bundle bundle = new Bundle();
            try {
                JSONObject extras = ReadableMapUtils.convertMapToJson(args.getMap("extraData"));
                bundle.putString("extraData", extras.toString());
                attributes.setExtraData(bundle);
            } catch (JSONException e) {
                // TODO reject the promise
            }
        }

        if (args.hasKey("group")) {
            attributes.setGroup(args.getString("group"));
        }

        int largeIconResId = res.getIdentifier("ic_launcher", "mipmap", packageName);
        attributes.setLargeIconString("ic_launcher");
        if (args.hasKey("largeIcon")) {
            String largeIcon = args.getString("largeIcon");
            attributes.setLargeIconString(largeIcon);

            if (largeIcon != null) {
                largeIconResId = res.getIdentifier(largeIcon, "mipmap", packageName);
                attributes.setHasLargeIcon(true);
            }
        }
        Bitmap largeIconBitmap = BitmapFactory.decodeResource(res, largeIconResId);

        if (largeIconResId != 0 && (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)) {
            attributes.setLargeIcon(largeIconBitmap);
        }


        if (args.hasKey("ongoing")) {
            attributes.setOngoing(args.getBoolean("ongoing"));
        }

        if (args.hasKey("priority")) {
            attributes.setPriority(args.getString("priority"));
        }

        if (args.hasKey("importance")) {
            attributes.setImportance(args.getString("importance"));
        }

        int smallIconResId = res.getIdentifier("ic_notification", "mipmap", packageName);
        attributes.setSmallIconString("ic_notification");
        if (args.hasKey("smallIcon")) {
            String smallIcon = args.getString("smallIcon");
            attributes.setSmallIconString(smallIcon);

            if (smallIcon != null) {
                smallIconResId = res.getIdentifier(smallIcon, "mipmap", packageName);
            }

            if (smallIconResId == 0) {
                smallIconResId = res.getIdentifier("ic_launcher", "mipmap", packageName);

                if (smallIconResId == 0) {
                    smallIconResId = android.R.drawable.ic_dialog_info;
                }
            }
        }
        attributes.setHasSmallIcon(true);
        attributes.setSmallIcon(smallIconResId);

        if (args.hasKey("playSound") && args.getBoolean("playSound")) {
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            attributes.setSound(soundUri);
            attributes.setPlaySound(true);
        }

        if (args.hasKey("vibrate") && args.getBoolean("vibrate")) {
            long[] vibratePattern = new long[]{0};

            long vibration = args.hasKey("vibration") ? (long) args.getDouble("vibration") : DEFAULT_VIBRATION;
            if (vibration == 0)
                vibration = DEFAULT_VIBRATION;

            vibratePattern = new long[]{0, vibration};

            attributes.setVibrate(true);
            attributes.setVibrationPattern(vibratePattern);
        }

        if (args.hasKey("visibility")) {
            attributes.setVisibility(args.getString("visibility"));
        }

        if (attributes.getChannelId().equals("Default")) {
            createDefaultNotificationChannel(attributes.getChannelId(), attributes.getChannelName(), attributes.getChannelDescription(), attributes.getImportance());
        } else {
            createChannelIfNeeded(attributes.getChannelId(), attributes.getChannelName(), attributes.getChannelDescription(), attributes.getSound(), attributes.getImportance(), attributes.getVibrationPattern());
        }

        this.postNotification(attributes);

//        callback.invoke("Received numberArgument: "+numberArgument +" stringArgument: "+stringArgument);
    }

    private void createDefaultNotificationChannel(String channelId, String channelName, String channelDescription, int importance) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = reactContext.getSystemService(NotificationManager.class);
            NotificationChannel defaultChannel = notificationManager.getNotificationChannel(channelId);
            if (defaultChannel == null) {
                NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
                channel.setDescription(channelDescription);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this

                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void createChannelIfNeeded(String channelId, String channelName, String channelDescription, Uri soundUri, int importance, long[] vibratePattern) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return;

        NotificationManager notificationManager = reactContext.getSystemService(NotificationManager.class);
        NotificationChannel channel = notificationManager.getNotificationChannel(channelId);

        if (channel == null) {
            channel = new NotificationChannel(channelId, channelName, importance);

            channel.setDescription(channelDescription);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setVibrationPattern(vibratePattern);

            if (soundUri != null) {
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .build();

                channel.setSound(soundUri, audioAttributes);
            } else {
                channel.setSound(null, null);
            }

            notificationManager.createNotificationChannel(channel);
        }
    }

    private void postNotification(LocalNotificationAttributes attributes) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(reactContext);

        Intent intent = new Intent(reactContext, getMainActivityClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Bundle bundle = new Bundle();
        bundle.putBoolean("userInteraction", true);
        bundle.putAll(attributes.toBundle());
        intent.putExtra("notification", bundle);

        PendingIntent pendingIntent = PendingIntent.getActivity(reactContext, attributes.getNotificationId(), intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(reactContext, attributes.getChannelId())
                .setAutoCancel(attributes.isAutoCancel())
                .setCategory(attributes.getCategory())
                .setColor(attributes.getColor())
                .setContentTitle(attributes.getTitle())
                .setContentText(attributes.getText())
                .setExtras(attributes.getExtraData())
                .setGroup(attributes.getGroup())
                .setOngoing(attributes.isOngoing())
                .setPriority(attributes.getPriority())
                .setVisibility(attributes.getVisibility());

        if (attributes.getSound() != null) {
            builder.setSound(attributes.getSound());
        } else if (attributes.getSound() == null || Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setSound(null);
        }
        if (attributes.isVibrate()) {
            builder.setVibrate(attributes.getVibrationPattern());
        }
        if (attributes.isHasSmallIcon()) {
            builder.setSmallIcon(attributes.getSmallIcon());
        }
        if (attributes.isHasLargeIcon()) {
            builder.setLargeIcon(attributes.getLargeIcon());
        }

        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        notification.defaults |= Notification.DEFAULT_LIGHTS;

        if (attributes.getTag() != null && attributes.getTag().isEmpty()) {
            notificationManager.notify(attributes.getNotificationId(), notification);
        } else {
            notificationManager.notify(attributes.getTag(), attributes.getNotificationId(), notification);
        }

    }

    public Class getMainActivityClass() {
        String packageName = reactContext.getPackageName();
        Intent launchIntent = reactContext.getPackageManager().getLaunchIntentForPackage(packageName);
        String className = launchIntent.getComponent().getClassName();
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        Log.d("tag", "onActivityResult");
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        Intent intent = activity.getIntent();
        Bundle bundle = this.getBundleFromIntent(intent);
        if (bundle != null) {
            bundle.putBoolean("foreground", false);
            intent.putExtra("notification", bundle);
            this.onNotificationReceived(bundle);
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}

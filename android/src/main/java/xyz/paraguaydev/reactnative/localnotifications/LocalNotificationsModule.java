package xyz.paraguaydev.reactnative.localnotifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import xyz.paraguaydev.reactnative.localnotifications.utils.ReadableMapUtils;

public class LocalNotificationsModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private final Long DEFAULT_VIBRATION = 300L;

    public LocalNotificationsModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "LocalNotifications";
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
            attributes.setColor(hexColorToInt(args.getString("color")));
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
        if (args.hasKey("largeIcon")) {
            String largeIcon = args.getString("largeIcon");

            if (largeIcon != null) {
                largeIconResId = res.getIdentifier(largeIcon, "mipmap", packageName);
            }
        }
        Bitmap largeIconBitmap = BitmapFactory.decodeResource(res, largeIconResId);

        if (largeIconResId != 0 && (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)) {
            attributes.setLargeIcon(largeIconBitmap);
            attributes.setHasLargeIcon(true);
        }


        if (args.hasKey("ongoing")) {
            attributes.setOngoing(args.getBoolean("ongoing"));
        }

        attributes.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        if (args.hasKey("priority")) {
            int priorityInt;
            String priority = args.getString("priority");
            switch (priority.toLowerCase()) {
                case "max":
                    priorityInt = NotificationCompat.PRIORITY_MAX;
                    break;
                case "high":
                    priorityInt = NotificationCompat.PRIORITY_HIGH;
                    break;
                case "low":
                    priorityInt = NotificationCompat.PRIORITY_LOW;
                    break;
                case "min":
                    priorityInt = NotificationCompat.PRIORITY_MIN;
                    break;
                default:
                    priorityInt = NotificationCompat.PRIORITY_DEFAULT;
            }

            attributes.setPriority(priorityInt);
        }

        attributes.setPriority(NotificationManagerCompat.IMPORTANCE_DEFAULT);
        if (args.hasKey("importance")) {
            int importanceInt;
            String importance = args.getString("importance");
            switch (importance.toLowerCase()) {
                case "max":
                    importanceInt = NotificationManagerCompat.IMPORTANCE_MAX;
                    break;
                case "high":
                    importanceInt = NotificationManagerCompat.IMPORTANCE_HIGH;
                    break;
                case "low":
                    importanceInt = NotificationManagerCompat.IMPORTANCE_LOW;
                    break;
                case "min":
                    importanceInt = NotificationManagerCompat.IMPORTANCE_MIN;
                    break;
                case "none":
                    importanceInt = NotificationManagerCompat.IMPORTANCE_NONE;
                    break;
                case "unspecified":
                    importanceInt = NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
                    break;
                default:
                    importanceInt = NotificationManagerCompat.IMPORTANCE_DEFAULT;
            }
            attributes.setImportance(importanceInt);
        }

        int smallIconResId = res.getIdentifier("ic_notification", "mipmap", packageName);
        if (args.hasKey("smallIcon")) {
            String smallIcon = args.getString("smallIcon");

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
        }

        if (args.hasKey("vibrate") && args.getBoolean("vibrate")) {
            long[] vibratePattern = new long[]{0};

            long vibration = args.hasKey("vibration") ? (long) args.getDouble("vibration") : DEFAULT_VIBRATION;
            if (vibration == 0)
                vibration = DEFAULT_VIBRATION;

            vibratePattern = new long[]{0, vibration};

            attributes.setVibrate(args.getBoolean("vibrate"));
            attributes.setVibrationPattern(vibratePattern);
        }

        attributes.setVisibility(NotificationCompat.VISIBILITY_PRIVATE);
        if (args.hasKey("visibility")) {
            int visibilityInt;
            String visibility = args.getString("visibility");
            switch (visibility.toLowerCase()) {
                case "private":
                    visibilityInt = NotificationCompat.VISIBILITY_PRIVATE;
                    break;
                case "public":
                    visibilityInt = NotificationCompat.VISIBILITY_PUBLIC;
                    break;
                case "secret":
                    visibilityInt = NotificationCompat.VISIBILITY_SECRET;
                    break;
                default:
                    visibilityInt = NotificationCompat.VISIBILITY_PRIVATE;
            }
            attributes.setVisibility(visibilityInt);
        }

//        callback.invoke("Received numberArgument: "+numberArgument +" stringArgument: "+stringArgument);
    }

    private int hexColorToInt(String hexColor) {
        String hex = hexColor.replace("#", "");
        return (Integer.parseInt(hex.substring(0, 2), 16) << 24) + Integer.parseInt(hex.substring(2), 16);
    }

    private void createDefaultNotificationChannel(String channelId, String channelName, String channelDescription, int importance) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(channelDescription);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = reactContext.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createChannelIfNeeded(NotificationManager manager, String channelId, String channelName, String channelDescription, Uri soundUri, int importance, long[] vibratePattern) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return;
        if (manager == null)
            return;

        NotificationChannel channel = manager.getNotificationChannel(channelId);

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

            manager.createNotificationChannel(channel);
        }
    }

    private void createNotification(LocalNotificationAttributes attributes) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(reactContext);

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

        Notification notification = builder.build();
        notification.defaults |= Notification.DEFAULT_LIGHTS;

        if (attributes.getTag().isEmpty()) {
            notificationManager.notify(attributes.getNotificationId(), notification);
        } else {
            notificationManager.notify(attributes.getTag(), attributes.getNotificationId(), notification);
        }

    }
}

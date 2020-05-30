package xyz.paraguaydev.reactnative.localnotifications;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Set;

public class LocalNotificationAttributes implements Serializable {
    private boolean autoCancel = true;
    private String category;
    private String hexColor;
    private int color;
    private String title;
    private String text;
    private HashMap data;
    private String group;
    private boolean hasLargeIcon = false;
    private Bitmap largeIcon;
    private String largeIconString;
    private boolean ongoing = false;
    private int priority = NotificationCompat.PRIORITY_DEFAULT;
    private String priorityString = "default";
    private int importance = NotificationManagerCompat.IMPORTANCE_DEFAULT;
    private String importanceString = "default";
    private boolean hasSmallIcon;
    private int smallIcon;
    private String smallIconString;
    private String channelId = "Default";
    private String channelName = "Notifications";
    private String channelDescription = "All Notifications";
    private boolean playSound = false;
    private Uri sound;
    private boolean vibrate = false;
    private long[] vibrationPattern;
    private String tag;
    private int visibility = NotificationCompat.VISIBILITY_PRIVATE;
    private String visibilityString = "private";
    private int notificationId;

    public boolean isAutoCancel() {
        return autoCancel;
    }

    public void setAutoCancel(boolean autoCancel) {
        this.autoCancel = autoCancel;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getColor() {
        return color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public HashMap getData() {
        return data;
    }

    public void setData(HashMap data) {
        this.data = data;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Bitmap getLargeIcon() {
        return largeIcon;
    }

    public void setLargeIcon(Bitmap largeIcon) {
        this.largeIcon = largeIcon;
    }

    public boolean isOngoing() {
        return ongoing;
    }

    public void setOngoing(boolean ongoing) {
        this.ongoing = ongoing;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.setPriorityString(priority);
        int priorityInt;
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

        this.priority = priorityInt;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.setImportanceString(importance);
        int importanceInt;
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
        this.importance = importanceInt;
    }

    public int getSmallIcon() {
        return smallIcon;
    }

    public void setSmallIcon(int smallIcon) {
        this.smallIcon = smallIcon;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelDescription() {
        return channelDescription;
    }

    public void setChannelDescription(String channelDescription) {
        this.channelDescription = channelDescription;
    }

    public Uri getSound() {
        return sound;
    }

    public void setSound(Uri sound) {
        this.sound = sound;
    }

    public boolean isVibrate() {
        return vibrate;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

    public long[] getVibrationPattern() {
        return vibrationPattern;
    }

    public void setVibrationPattern(long[] vibrationPattern) {
        this.vibrationPattern = vibrationPattern;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.setVisibilityString(visibility);
        int visibilityInt;
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
        this.visibility = visibilityInt;
    }

    public boolean isHasLargeIcon() {
        return hasLargeIcon;
    }

    public void setHasLargeIcon(boolean hasLargeIcon) {
        this.hasLargeIcon = hasLargeIcon;
    }

    public boolean isHasSmallIcon() {
        return hasSmallIcon;
    }

    public void setHasSmallIcon(boolean hasSmallIcon) {
        this.hasSmallIcon = hasSmallIcon;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getHexColor() {
        return hexColor;
    }

    public void setHexColor(String hexColor) {
        this.hexColor = hexColor;
        this.color = Color.parseColor(hexColor);
    }

    public String getLargeIconString() {
        return largeIconString;
    }

    public void setLargeIconString(String largeIconString) {
        this.largeIconString = largeIconString;
    }

    public String getSmallIconString() {
        return smallIconString;
    }

    public void setSmallIconString(String smallIconString) {
        this.smallIconString = smallIconString;
    }

    public String getPriorityString() {
        return priorityString;
    }

    public void setPriorityString(String priorityString) {
        this.priorityString = priorityString;
    }

    public String getImportanceString() {
        return importanceString;
    }

    public void setImportanceString(String importanceString) {
        this.importanceString = importanceString;
    }

    public boolean isPlaySound() {
        return playSound;
    }

    public void setPlaySound(boolean playSound) {
        this.playSound = playSound;
    }

    public String getVisibilityString() {
        return visibilityString;
    }

    public void setVisibilityString(String visibilityString) {
        this.visibilityString = visibilityString;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("autoCancel", autoCancel);
        bundle.putString("category", category);
        bundle.putString("color", hexColor);
        bundle.putString("title", title);
        bundle.putString("text", text);
        bundle.putSerializable("data", data);
        bundle.putString("group", group);
        bundle.putBoolean("hasLargeIcon", hasLargeIcon);
        bundle.putString("largeIcon", largeIconString);
        bundle.putBoolean("ongoing", ongoing);
        bundle.putString("priority", priorityString);
        bundle.putString("importance", importanceString);
        bundle.putBoolean("hasSmallIcon", hasSmallIcon);
        bundle.putString("smallIcon", smallIconString);
        bundle.putString("channelId", channelId);
        bundle.putString("channelName", channelName);
        bundle.putString("channelDescription", channelDescription);
        bundle.putBoolean("playSound", playSound);
        bundle.putBoolean("vibrate", vibrate);
        bundle.putLongArray("vibrationPattern", vibrationPattern);
        bundle.putString("tag", tag);
        bundle.putString("visibility", visibilityString);
        bundle.putInt("notificationId", notificationId);

        return bundle;
    }

    public static LocalNotificationAttributes fromBundle(Bundle bundle) {
        LocalNotificationAttributes attributes = new LocalNotificationAttributes();
        attributes.setAutoCancel(bundle.getBoolean("autoCancel"));
        attributes.setCategory(bundle.getString("category"));
        attributes.setHexColor(bundle.getString("color"));
        attributes.setTitle(bundle.getString("title"));
        attributes.setText(bundle.getString("text"));
        attributes.setData((HashMap) bundle.getSerializable("data"));
        attributes.setGroup(bundle.getString("group"));
        attributes.setHasLargeIcon(bundle.getBoolean("hasLargeIcon"));
        attributes.setLargeIconString(bundle.getString("largeIcon"));
        attributes.setOngoing(bundle.getBoolean("ongoing"));
        attributes.setPriority(bundle.getString("priority"));
        attributes.setImportance(bundle.getString("importance"));
        attributes.setHasSmallIcon(bundle.getBoolean("hasSmallIcon"));
        attributes.setSmallIconString(bundle.getString("smallIcon"));
        attributes.setChannelId(bundle.getString("channelId"));
        attributes.setChannelName(bundle.getString("channelName"));
        attributes.setChannelDescription(bundle.getString("channelDescription"));
        attributes.setPlaySound(bundle.getBoolean("playSound"));
        attributes.setVibrate(bundle.getBoolean("vibrate"));
        attributes.setVibrationPattern(bundle.getLongArray("vibrationPattern"));
        attributes.setTag(bundle.getString("tag"));
        attributes.setVisibility(bundle.getString("visibility"));
        attributes.setNotificationId(bundle.getInt("notificationId"));

        return attributes;
    }

    public JSONObject toJSONObject() {
        Bundle bundle = this.toBundle();
        return this.toJSONObject(bundle);
    }

    private JSONObject toJSONObject(Bundle bundle) {
        JSONObject jsonObject = new JSONObject();

        Set<String> keys = bundle.keySet();
        for (String key : keys) {
            try {
                Object value = bundle.get(key);
                jsonObject.put(key, JSONObject.wrap(value));
            } catch (JSONException e) {
                // Handle exception here
            }
        }

        return jsonObject;
    }
}

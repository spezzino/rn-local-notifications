package xyz.paraguaydev.reactnative.localnotifications;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

public class LocalNotificationAttributes {
    private boolean autoCancel = true;
    private String category;
    private int color;
    private String title;
    private String text;
    private Bundle extraData;
    private String group;
    private boolean hasLargeIcon = false;
    private Bitmap largeIcon;
    private boolean ongoing = false;
    private int priority;
    private int importance;
    private boolean hasSmallIcon;
    private int smallIcon;
    private String channelId = "Default";
    private String channelName = "Notifications";
    private String channelDescription = "All Notifications";
    private Uri sound;
    private boolean vibrate = false;
    private long[] vibrationPattern;
    private String tag;
    private int visibility;
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

    public void setColor(int color) {
        this.color = color;
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

    public Bundle getExtraData() {
        return extraData;
    }

    public void setExtraData(Bundle extraData) {
        this.extraData = extraData;
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

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
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

    public void setVisibility(int visibility) {
        this.visibility = visibility;
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
}

package com.example.naj_t.cookitv1;

import com.google.gson.annotations.SerializedName;

public class DeviceControl {
    @SerializedName("Light")
    String light;
    @SerializedName("Stove")
    String stove;
    @SerializedName("Key")
    String key;

    public DeviceControl() {
    }

    public DeviceControl(String light, String stove, String key) {
        this.light = light;
        this.stove = stove;
        this.key = key;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public String getStove() {
        return stove;
    }

    public void setStove(String stove) {
        this.stove = stove;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

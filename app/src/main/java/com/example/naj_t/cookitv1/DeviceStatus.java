package com.example.naj_t.cookitv1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeviceStatus {
    @SerializedName("Light")
    @Expose
    String temp;
    @SerializedName("Stove")
    @Expose
    String stoveStatus;
    private String timeStamp;


    public DeviceStatus() {
    }

    public DeviceStatus(String temp, String stoveStatus) {
        this.temp = temp;
        this.stoveStatus = stoveStatus;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getStoveStatus() {
        return stoveStatus;
    }

    public void setStoveStatus(String stoveStatus) {
        this.stoveStatus = stoveStatus;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}

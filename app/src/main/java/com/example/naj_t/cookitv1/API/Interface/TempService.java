package com.example.naj_t.cookitv1.API.Interface;

import com.example.naj_t.cookitv1.DeviceStatus;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TempService {
    @GET("{id}")
    Call<DeviceStatus> getDeviceStatus(@Path("id")String id);
}

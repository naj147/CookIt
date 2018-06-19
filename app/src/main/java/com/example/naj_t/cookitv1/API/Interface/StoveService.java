package com.example.naj_t.cookitv1.API.Interface;

import com.example.naj_t.cookitv1.DeviceControl;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface StoveService {
    @POST("{id}")
    Call<DeviceControl> turnOffStove(@Path("id") String id, @Body DeviceControl deviceControl);
}

package com.example.naj_t.cookitv1.API;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.naj_t.cookitv1.API.Interface.StoveService;
import com.example.naj_t.cookitv1.DeviceControl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StoveStatusController implements Callback<DeviceControl> {
    final String ID= "1";//VALUE FIXED BY THE BACKEND CODER FOR TEST PURPOSES
    final String KEY= "452af0ea3b1515c56f8f754cde7d2ea4";//VALUE FIXED BY THE BACKEND CODER FOR TEST PURPOSES
    Context context;
    final String TAG= "STOVESERVICE";
    public void start(Context context){
        this.context=context;
        Gson gson =
                new GsonBuilder()
                        .create();
        String baseUrl = "http://39.106.107.244:" +
                "" +
                "8080/springTest/led/v1?id=1&email=off";
        Retrofit retrofit= new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        StoveService service = retrofit.create(StoveService.class);
        DeviceControl deviceControl = new DeviceControl("on","off", KEY);
        Call<DeviceControl> call = service.turnOffStove(ID,deviceControl);
        call.enqueue(this);
    }
    @Override
    public void onResponse(Call<DeviceControl> call, Response<DeviceControl> response) {
        if(response.isSuccessful()) {
            Toast.makeText(context,"Turning The Stove OFF",Toast.LENGTH_SHORT).show();;
        }else{
            Log.v(TAG, call.request().url().toString());
            Log.v(TAG, "Error bitch");
        }

    }

    @Override
    public void onFailure(Call<DeviceControl> call, Throwable t) {
        Log.v(TAG, call.request().url().toString());
        Log.v(TAG, "Miserably failed " + t.getMessage());
    }
}

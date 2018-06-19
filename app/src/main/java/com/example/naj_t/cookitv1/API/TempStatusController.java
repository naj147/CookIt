package com.example.naj_t.cookitv1.API;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;

import com.example.naj_t.cookitv1.API.Interface.TempService;
import com.example.naj_t.cookitv1.DeviceControl;
import com.example.naj_t.cookitv1.DeviceStatus;
import com.example.naj_t.cookitv1.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TempStatusController implements Callback<DeviceStatus> {
final String ID= "3";//VALUE FIXED BY THE BACKEND CODER FOR TEST PURPOSES
    final String TAG= "TEMPSERVICE";
    PieChart pie;
    public void start(PieChart argPie){
        pie=argPie;
        Gson gson =
                new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        String baseUrl = "http://39.106.107.244:8080/springTest/user/id/";
        Retrofit retrofit= new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        TempService service = retrofit.create(TempService.class);
        Call<DeviceStatus> call = service.getDeviceStatus(ID);
        call.enqueue(this);
    }
 public DeviceStatus cleanUp(DeviceStatus d){
       String temp= d.getTemp();
       int index;
       if(temp.contains("-")){
           index =temp.indexOf("-");
           d.setTemp(temp.substring(0,index-1));
           d.setTimeStamp(temp.substring(index+1,temp.length()));
       }
       return d;
 }
    private SpannableString generateCenterText(String temp) {
        SpannableString s = new SpannableString(temp+"°C\nTemperature");
        s.setSpan(new RelativeSizeSpan(6f), 0, 4, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 4, s.length(), 0);
        return s;
    }
    private int tempStatus(String temp){
        float tempf=Float.valueOf(temp);
        int tempo= (int) tempf;
        if(tempo==0){
            return 0;
        }else{
            if(tempo<41)
                return 1;
            else{
                if(tempo<81)
                    return 2;
                else{
                    if(tempo<121)
                        return 3;
                    else{
                        if (tempo<199)
                            return 4;
                        else
                            return 5;
                    }
                }
            }
        }
    }
    private String tempStatusString(String tempo){
        int i = tempStatus(tempo);
        switch (i){
            case 0:
                return "Stove is off";
            case 1:
                return "Low Temp";
            case 2:
                return "Low to Medium Temp";
            case 3:
                return "Medium Temp";
            case 4 :
                return "Medium to High Temp";
            case 5:
                return "High Temp";
        }
        return "Stove is off";
    }
    int tempStatusColor(String tempo){
        int i = tempStatus(tempo);
        switch (i){
            case 0:
                return Color.LTGRAY;
            case 1:
                return Color.BLUE;
            case 2:
                return Color.GREEN;
            case 3:
                return Color.YELLOW;
            case 4 :
                return Color.rgb(255,165,0);
            case 5:
                return Color.RED;
        }
        return Color.LTGRAY;
    }
    protected PieData generatePieData(String temp) {

        int count = 1;

        ArrayList<PieEntry> entries1 = new ArrayList<PieEntry>();

        for(int i = 0; i < count; i++) {
            entries1.add(new PieEntry((float) ((Math.random() * 60) + 40), tempStatusString(temp)));
        }
        PieDataSet ds1 = new PieDataSet(entries1, temp+"°C");
//        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setColor(tempStatusColor(temp));
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.WHITE);
        ds1.setValueTextSize(0f);
        return new PieData(ds1);
    }
    @Override
    public void onResponse(@NonNull Call<DeviceStatus> call, @NonNull Response<DeviceStatus> response) {
        if(response.isSuccessful()) {
            Log.v(TAG, call.request().url().toString());
            DeviceStatus deviceStatus=cleanUp(response.body());
            pie.setCenterText(generateCenterText(deviceStatus.getTemp()));
            pie.setData(generatePieData(deviceStatus.getTemp()));
            pie.notifyDataSetChanged();
            pie.invalidate();
        }else {
            Log.v(TAG, call.request().url().toString());
            Log.v(TAG, "Error bitch");
        }
    }

    @Override
    public void onFailure(@NonNull Call<DeviceStatus> call, @NonNull Throwable t) {
        Log.v(TAG, call.request().url().toString());
        Log.v(TAG, "Miserably failed " + t.getMessage());
    }
}

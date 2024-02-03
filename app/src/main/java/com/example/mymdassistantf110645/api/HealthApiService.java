package com.example.mymdassistantf110645.api;

import com.example.mymdassistantf110645.model.HealthData;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HealthApiService {
    @GET("accessibility.json")
    Call<HealthData> getHealthData();
}

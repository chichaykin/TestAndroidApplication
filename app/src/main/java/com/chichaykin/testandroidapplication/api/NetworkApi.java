package com.chichaykin.testandroidapplication.api;

import retrofit2.http.POST;
import rx.Observable;

/**
 * Backend API
 */

public interface NetworkApi {

    @POST("/s/imr81trlfkhnixv/mapdata.json")
    Observable<Data> getData();
}

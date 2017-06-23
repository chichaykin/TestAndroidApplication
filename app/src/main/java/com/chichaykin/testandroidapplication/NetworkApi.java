package com.chichaykin.testandroidapplication;

import com.chichaykin.testandroidapplication.api.Data;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by chichaykin on 23/06/2017.
 */

public interface NetworkApi {

    @GET("/s/imr81trlfkhnixv/mapdata.json")
    Observable<Data> getData();
}

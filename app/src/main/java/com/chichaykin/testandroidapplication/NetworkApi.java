package com.chichaykin.testandroidapplication;

import com.chichaykin.testandroidapplication.api.Data;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by chichaykin on 23/06/2017.
 */

public interface NetworkApi {

    @GET("content_link/mWI4D5nSlu7aT6sSnLAVKPlCHS1V92gDIEadtu9ep07gsl7ZQluJ7Bg46JC98DHG/file")
    Observable<Data> getData();
}

package com.chichaykin.testandroidapplication;

import io.reactivex.Observable;

/**
 * Created by chichaykin on 23/06/2017.
 */

public interface MatrixJob {
    Result getData();

    Observable<Result> calculateMatrix();
}

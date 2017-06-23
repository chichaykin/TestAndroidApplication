package com.chichaykin.testandroidapplication;

/**
 * Created by chichaykin on 23/06/2017.
 */

public interface MatrixJob {

    Result getData();

    rx.Observable<Result> calculateMatrix();
}

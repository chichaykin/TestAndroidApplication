package com.chichaykin.testandroidapplication.service;

import android.support.annotation.Nullable;
import com.chichaykin.testandroidapplication.model.Result;

/**
 * Service API
 */

public interface MatrixJob {

    /**
     * @return calculated data, otherwise null
     */
    @Nullable
    Result getData();

    /**
     * Calculation data
     */
    rx.Observable<Result> calculateMatrix();
}

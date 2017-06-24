package com.chichaykin.testandroidapplication;

import com.chichaykin.testandroidapplication.api.Data;
import com.chichaykin.testandroidapplication.model.Result;
import com.test.Matrix;

public class CalculationProvider {

    public Result calculate(Data data) {
        int rows = data.data.size();
        int columns = rows != 0 ? data.data.get(0).size() : 0;
        int[][] array = new int[rows][columns];
        for(int i=0; i < rows; i++) {
            for(int j=0; j < columns; j++) {
                array[i][j] = data.data.get(i).get(j);
            }
        }

        int countries = Matrix.countries(array);
        return new Result(rows, columns, countries);
    }
}

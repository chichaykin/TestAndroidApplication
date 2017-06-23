package com.chichaykin.testandroidapplication;

/**
 * Created by chichaykin on 23/06/2017.
 */

public class Result {
    private int rows;
    private int columns;
    private int countries;

    public Result(int rows, int columns, int countries) {
        this.rows = rows;
        this.columns = columns;
        this.countries = countries;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getCountries() {
        return countries;
    }

    @Override
    public String toString() {
        return "Result{" +
                "rows=" + rows +
                ", columns=" + columns +
                ", countries=" + countries +
                '}';
    }
}

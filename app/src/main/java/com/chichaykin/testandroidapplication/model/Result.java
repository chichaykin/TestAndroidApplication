package com.chichaykin.testandroidapplication.model;

/**
 * Calculation result
 */

public class Result {
    private final int rows;
    private final int columns;
    private final int countries;

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

package com.visualization.example.visualizationexample.model;

import java.util.Date;

public class DataPoint {
    private double x;
    private double y;

    public DataPoint(Double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Getters and setters

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
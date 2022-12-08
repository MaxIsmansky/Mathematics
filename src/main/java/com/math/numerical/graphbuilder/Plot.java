package com.math.numerical.graphbuilder;

import java.util.Map;

/**
 * Класса графика функции
 */
public class Plot {

    private String name;

    private Map<Double, Double> functionPoints;

    private char marker;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Double, Double> getFunctionPoints() {
        return functionPoints;
    }

    public void setFunctionPoints(Map<Double, Double> functionPoints) {
        this.functionPoints = functionPoints;
    }

    public char getMarker() {
        return marker;
    }

    public void setMarker(char marker) {
        this.marker = marker;
    }
}

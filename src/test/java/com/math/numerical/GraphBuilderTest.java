package com.math.numerical;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphBuilderTest {

    @Test
    void createPlot() {
        int size = 20;
        new GraphBuilder().createPlot(30, 60, x -> (int) (3*x));
    }
}
package com.math.numerical;

import com.math.numerical.graphbuilder.PlotBuilder;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

class PlotBuilderTest {

    @Test
    void build1() throws ParseException {
        new PlotBuilder(2, -2, 2,-2,0.1,0.1).build(Math::cos);
    }

    @Test
    void build2() throws ParseException {
        new PlotBuilder(10, -10, 10,-10,1,1).build(x -> x*x);
    }

    @Test
    void build3() throws ParseException {
        new PlotBuilder(10, -10, 10,-10,0.1,0.1).build(Math::sin, x -> x);
    }

    @Test
    void buildFunctionAndDerivative() throws ParseException {
        new PlotBuilder(10, -10, 10,-10,0.1,0.1).buildFunctionAndDerivative(x -> Math.pow(x, 2));
    }
}
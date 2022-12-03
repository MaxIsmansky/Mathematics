package com.math.numerical;

import org.junit.jupiter.api.Test;

import java.text.ParseException;

class GraphBuilderTest {

    @Test
    void build1() throws ParseException {
        new GraphBuilder(10, -10, 10,-10,0.1,0.1).build(Math::sin);
    }

    @Test
    void build2() throws ParseException {
        new GraphBuilder(10, -10, 10,-10,1,1).build(x -> x*x);
    }
}
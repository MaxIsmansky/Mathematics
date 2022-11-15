package com.math.algorithms;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Вычисление факториала числа
 */
public class Factorial {
    public static void main(String[] args) {
        System.out.println(calculate(6));
    }

    public static BigDecimal calculate(int val) {
        BigDecimal value = BigDecimal.valueOf(val);
        BigDecimal current = BigDecimal.valueOf(2);
        BigDecimal result = BigDecimal.valueOf(1);
        while(current.compareTo(value) != 0) {
            result = result.multiply(current);
            current = current.add(BigDecimal.valueOf(1));
        }
        return result;
    }
}

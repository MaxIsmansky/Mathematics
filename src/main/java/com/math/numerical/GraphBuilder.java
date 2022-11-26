package com.math.numerical;

/**
 * Простой построитель графиков
 */
public class GraphBuilder {

    public void createPlot(int xMax, int yMax, Function function) {
        //Число точек для построения графика
        int dotsNum = 20;
        int[] xDots = new int[dotsNum];
        int[] yDots = new int[dotsNum];
        for (int i = 0; i < dotsNum; i++) {
            xDots[i] = i;
            yDots[i] = function.functionDefinition(i);
        }
        //Найдем точку с самой большой координатой, которая попадает в диапазон поля
        int maxElementIndex = yDots.length - 1;
        for (int i = 0; i < yDots.length; i++) {
            if (yDots[i] > yMax) {
                maxElementIndex = i - 2;
            }
        }
        int spaces = String.valueOf(yDots[yDots.length - 1]).length() + 1;
        boolean xPointsCreated = false;
        for (int y = yMax; y >= 0; y--) {
            for (int x = 0; x < xMax; x++) {
                if (x == 0) {
                    System.out.print(y);
                    //Строка ниже нужна только для выравнивания подписей оси y вдоль вертикальной линии
                    //Всего символов до начала поля = количество символов в числе самом большом числе + один пробел
                    for (int s = 0; s < spaces - String.valueOf(y).length(); s++) System.out.print(" ");
                }
                for (int k = 0; k < maxElementIndex; k++) {
                    if (x <= xDots[k] && yDots[x] == y) {
                        System.out.print("x  ");
                        xPointsCreated = true;
                        break;
                    }
                }
                if (!xPointsCreated) {
                    System.out.print(".  ");
                }
                xPointsCreated = false;
            }
            System.out.print("\n");
        }
    }

    public void createPlot(int size, Function function) {
        double[] xDots = new double[size];
        double[] yDots = new double[size];
        for (int i = 0; i < size; i++) {
            xDots[i] = i;
            yDots[i] = function.functionDefinition(i);
        }
        int xMax, yMax;
        xMax = yMax = size;
        for (int y = 0; y < yMax; y++) {
            for (int x = 0; x < xMax; x++) {
                System.out.print(".  ");
            }
            System.out.print("\n");
        }
    }

    @FunctionalInterface
    interface Function {
        int functionDefinition(int x);
    }

}

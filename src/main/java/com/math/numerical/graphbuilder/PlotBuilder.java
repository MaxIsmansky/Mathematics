package com.math.numerical.graphbuilder;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

/**
 * Консольный построитель графиков
 */
public class PlotBuilder {

    private double x_max;
    private double x_min;
    private double y_max;
    private double y_min;
    private double step_x;
    private double step_y;
    private DecimalFormat decimalFormatX;
    private DecimalFormat decimalFormatY;
    private int additionalSpace = 5;

    /**
     * @param x_max - Максимальное значение аргумента (масштаб oX+)
     * @param x_min - Минимальное значение аргумента (масштаб oX-)
     * @param y_max - Максимальное значение функции (масштаб oY+)
     * @param y_min - Минимальное значение функции (масштаб oY-)
     * @param step_x - Шаг вдоль оси X
     * @param step_y - Шаг вдоль оси Y
     */
    public PlotBuilder(double x_max, double x_min, double y_max, double y_min, double step_x, double step_y) {
        this.x_max = x_max;
        this.x_min = x_min;
        this.y_max = y_max;
        this.y_min = y_min;
        this.step_x = step_x;
        this.step_y = step_y;
        initFormats();
    }

    private void initFormats() {
        decimalFormatX = new DecimalFormat(getPatternForNumbers(step_x));
        decimalFormatY = new DecimalFormat(getPatternForNumbers(step_y));
    }

    /**
     * @param functions - Функции для построения
     * @throws ParseException
     */
    public void build(FunctionHolder... functions) throws ParseException {

        FunctionHolder functionHolder = functions[0];

        ArrayList<Plot> functionPlotList = new ArrayList<>();
        for (int i = 0; i < functions.length; i++) {
            Plot plot = new Plot();
            plot.setFunctionPoints(getFunctionPoints(functions[i]));
            plot.setName("Plot " + i);
            plot.setMarker((char) (i + 1 + '0'));
            functionPlotList.add(plot);
        }

        createPlot(functionPlotList);
    }

    /**
     * @param functionPlotList - Список объектов графиков функций
     * @throws ParseException
     */
    private void createPlot(List<Plot> functionPlotList) throws ParseException {
        final int xSpaceLength = getPatternForNumbers(step_x).length() + additionalSpace;
        for (double j = y_max; j > y_min; j = j - step_y) {
            final String valueY = decimalFormatY.format(j);
            j = NumberFormat.getInstance(Locale.FRANCE).parse(valueY).doubleValue();
            for (double i = x_min; i < x_max; i += step_x) {
                final String valueX = decimalFormatX.format(i);
                i = NumberFormat.getInstance(Locale.FRANCE).parse(valueX).doubleValue();
                //Выравнивание оси X вдоль оси Y. Построение оси Y
                if (j == 0) {
                    if (i == 0) {
                        System.out.print(" ");
                    }
                    System.out.print(i);
                    //Необходимо для выравнивания цифр и точек по оси x. Вычисляется следующим образом:
                    //Количество необходимых пробелов = количество пробелов которое ставится между точками - количество символов в числе-координате
                    for (int k = 0; k <= xSpaceLength - String.valueOf(i).length(); k++) {
                        System.out.print(" ");
                    }
                //Выравнивание оси Y вдоль оси X. Построение оси X
                } else if (i == 0) {
                    if (j >= 0) {
                        System.out.print(" ");
                    }
                    System.out.print(j);
                    int yLineAddition = Math.abs(((int) j) / 10) == 0 ? 0 : String.valueOf(Math.abs(((int) j) / 10)).length();
                    for (int k = 0; k < xSpaceLength - yLineAddition; k++) {
                        System.out.print(" ");
                    }
                //График строится в этом блоке
                } else {
                    boolean isPointCreated = false;
                    for (int f = 0; f < functionPlotList.size(); f++) {
                        if (!isPointCreated) {
                            if (functionPlotList.get(f).getFunctionPoints().get(i) == j) {
                                System.out.print(functionPlotList.get(f).getMarker());
                                isPointCreated = true;
                            } else if (f + 1 == functionPlotList.size()) {
                                System.out.print(".");
                            }
                        }
                    }
                    //Отделяем точки пробелами, чтобы цифры вместились и каждая цифра соответствовала своей точке
                    for (int k = 0; k < xSpaceLength; k++) {
                        System.out.print(" ");
                    }
                }
            }
            System.out.println();
        }
    }

    /**
     * @param function - Функция, график которой будет построен. На той же координатной плоскости будет построен график производной функции
     */
    public void buildFunctionAndDerivative(FunctionHolder function) {
        try {
            final Map<Double, Double> functionPoints = getFunctionPoints(function);
            Plot functionPlot = new Plot();
            functionPlot.setFunctionPoints(functionPoints);
            functionPlot.setMarker('f');
            Map<Double, Double> derivativeFunctionPoints = new HashMap<>();
            functionPoints.forEach((key, value) -> {
                //argument change
                double d_x = key + 0.01;
                //function change
                double d_y = function.function(d_x) - function.function(key);
                //function change
                double derivativePoint = d_y/0.01;
                try {
                    derivativeFunctionPoints.put(formatDoubleByDecimalNumbers(key), formatDoubleByDecimalNumbers(derivativePoint));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            });
            Plot derivativePlot = new Plot();
            derivativePlot.setFunctionPoints(derivativeFunctionPoints);
            derivativePlot.setMarker('d');
            List<Plot> functionAndDerivativeList = List.of(functionPlot, derivativePlot);
            createPlot(functionAndDerivativeList);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param num - Число с плавающей точкой
     * @return - Число с плавающей точкой, округленное в зависимости от количества цифр после запятой у шага
     */
    private String getPatternForNumbers(double num) {
        StringBuilder pattern = new StringBuilder("0");
        if (num % 10 < 1 & num > -1) {
            final int numsInDecimal = Double.toString(num).substring(2).length();
            pattern.append(".");
            for (int i = 0; i < numsInDecimal; i++) {
                pattern.append("0");
            }
            return pattern.toString();
        }
        return pattern.toString();
    }

    /**
     * @param functionHolder - Функция для построения графика
     * @return - Отображение значение аргумента -> значение функции
     * @throws ParseException
     */
    private Map<Double, Double> getFunctionPoints(FunctionHolder functionHolder) throws ParseException {
        Map<Double, Double> functionPointsMap = new HashMap<>();
        Map<Double, Double> roundedFunctionPointsMap = new HashMap<>();
        double xPoint = x_min, yPoint = 0;
        while (xPoint < x_max) {
            yPoint = functionHolder.function(xPoint);
            functionPointsMap.put(xPoint, yPoint);
            xPoint += step_x;
        }
        functionPointsMap.forEach((key, value) -> {
            try {
                roundedFunctionPointsMap.put(formatDoubleByDecimalNumbers(key), formatDoubleByDecimalNumbers(value));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
        return roundedFunctionPointsMap;
    }

    /**
     * @param value - Число с плавающей точкой
     *
     *
     * @return - Число с плавающей точкой округленное в соответствии с паттерном
     * @throws ParseException
     */
    private double formatDoubleByDecimalNumbers (double value) throws ParseException {
        return NumberFormat.getInstance(Locale.FRANCE).parse(decimalFormatX.format(value)).doubleValue();
    }

    /**
     * Интерфейс функции
     */
    @FunctionalInterface
    public interface FunctionHolder {
        /**
         * @param argument - Функция для построения
         * @return - Значение функции
         */
        double function(double argument);
    }
}

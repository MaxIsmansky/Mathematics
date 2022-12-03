package com.math.numerical;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Консольный построитель графиков
 */
public class GraphBuilder {

    private double x_max;
    private double x_min;
    private double y_max;
    private double y_min;
    private double step_x;
    private double step_y;
    private DecimalFormat decimalFormatX = new DecimalFormat(getPatternForNumbers(step_x));
    private DecimalFormat decimalFormatY = new DecimalFormat(getPatternForNumbers(step_y));
    private int additionalSpace = 5;

    /**
     * @param x_max - Максимальное значение аргумента (масштаб oX+)
     * @param x_min - Минимальное значение аргумента (масштаб oX-)
     * @param y_max - Максимальное значение функции (масштаб oY+)
     * @param y_min - Минимальное значение функции (масштаб oY-)
     * @param step_x - Шаг вдоль оси X
     * @param step_y - Шаг вдоль оси Y
     */
    public GraphBuilder(double x_max, double x_min, double y_max, double y_min, double step_x, double step_y) {
        this.x_max = x_max;
        this.x_min = x_min;
        this.y_max = y_max;
        this.y_min = y_min;
        this.step_x = step_x;
        this.step_y = step_y;
    }

    /**
     * @param functionHolder - Функция для построения
     * @throws ParseException
     */
    public void build(FunctionHolder functionHolder) throws ParseException {

        //Получим точки для построения графика
        final Map<Double, Double> functionPoints = getFunctionPoints(functionHolder);

        final int xSpaceLength = getPatternForNumbers(step_x).length() + additionalSpace;

        double lastDoubleY = y_max;
        double lastDoubleX = y_min;
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
                    if (functionPoints.get(i) == j) {
                        System.out.print("*");
                    } else {
                        System.out.print(".");
                    }
                    //Отделяем точки пробелами, чтобы цифры вместились и каждая цифра соответствовала своей точке
                    for (int k = 0; k < xSpaceLength; k++) {
                        System.out.print(" ");
                    }
                }
                lastDoubleX = i;
            }
            lastDoubleY = j;
            lastDoubleX = 0;
            System.out.println();
        }
    }

    /**
     * @param num - Число с плавающей точкой
     * @return - Число с плавающей точкой, округленное в зависимости от количество цифр после запятой у шага
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
        double xPoint = x_min, yPoint = 0;
        while (xPoint != x_max) {
            xPoint = formatDoubleByDecimalNumbers(xPoint);
            yPoint = functionHolder.function(xPoint);
            yPoint = formatDoubleByDecimalNumbers(yPoint);
            functionPointsMap.put(xPoint, yPoint);
            xPoint += step_x;
        }
        return functionPointsMap;
    }

    /**
     * @param value - Число с плавающей точкой
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

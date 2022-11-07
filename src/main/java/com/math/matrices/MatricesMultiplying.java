package com.math.matrices;

/**
 * Умножение матриц
 */
public class MatricesMultiplying {

    static int[][] matrix1 = new int[][]{
            new int[]{11, 23, 3},
            new int[]{1, 42, 3},
            new int[]{11, 2, 9}
    };

    static int[][] matrix2 = new int[][]{
            new int[]{35, 2},
            new int[]{3, 2},
            new int[]{39, 23}
    };

    public static void main(String[] args) throws Exception {
        final int[][] result = multiply(matrix1, matrix2);
        displayMatrix(result);
    }

    /**
     * @param matrix1 - Матрица 1
     * @param matrix2 - Матрица 2
     * @return - Матрица-результат произведения двух переданных в метод матриц
     * @throws Exception
     */
    public static int[][] multiply(int[][] matrix1, int[][] matrix2) throws Exception {
        checkMatrix(matrix1);
        checkMatrix(matrix2);
        final int[][] result = getResultMatrixTemplate(matrix1, matrix2);
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix2[0].length; j++) {
                //Умножение строки на столбец вынесено в отдельный метод для удобства
                result[i][j] = ijMultiply(matrix1[i], matrix2, j);
            }
        }
        return result;
    }

    /**
     * @param iStrk - Строка первой матрицы для умножения
     * @param matrix2 - Вторая матрица
     * @param j - Номер столбца второй матрицы
     * @return
     */
    private static int ijMultiply(int[] iStrk, int[][] matrix2, int j) {
        int res = 0;
        for (int k = 0; k < matrix2.length; k++) {
            //Выполняем скалярное произведение i-й строки первой матрицы на j-й столбец второй матрицы
            res += iStrk[k] * matrix2[k][j];
        }
        return res;
    }

    /**
     * Проверка является ли введенный двумерный массив матрицей
     * @param matrix - Матрица для проверки
     * @throws Exception
     */
    public static void checkMatrix(int[][] matrix) throws Exception {
        for (int i = 0; i < matrix.length - 1; i++) {
            if (matrix[i].length != matrix[i+1].length) {
                displayMatrix(matrix);
                throw new Exception("Представленный двумерный массив " + matrix + " не является матрицей");
            }
        }
        System.out.println("Проверка размерности матриц пройдена для матрицы: " + matrix);
    }

    /**
     * @param matrix1 - Матрица 1, размер MxN
     * @param matrix2 - Матрица 2, размер NxK
     * @return - Результирующая матрица размера MxK
     * @throws Exception
     */
    public static int[][] getResultMatrixTemplate(int[][] matrix1, int[][] matrix2) throws Exception {
        int j = matrix1[0].length;
        int i = matrix2.length;
        if (i != j) {
            throw new Exception("Матрицы не могут быть умножены!" +
                    " Количество столбцов первой матрицы: " + j + ", количество строк второй: " + i + " - не совпадают");
        }
        System.out.println("Матрицы могут быть умножены");
        return new int[matrix1.length][matrix2[0].length];
    }

    /**
     * Выполняет отображение матрицы в консоли
     * @param matrix - Матрица для отображения
     */
    public static void displayMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println("");
        }
    }
}

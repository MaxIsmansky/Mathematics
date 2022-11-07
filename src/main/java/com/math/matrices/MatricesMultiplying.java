package com.math.matrices;

public class MatricesMultiplying {

    public static void main(String[] args) throws Exception {
        int[][] matrix1 = new int[][]{
                new int[]{1, 2, 3},
                new int[]{1, 2, 3},
                new int[]{1, 2, 9}
        };
        int[][] matrix2 = new int[][]{
                new int[]{3, 2},
                new int[]{3, 2},
                new int[]{3, 2}
        };
        multiply(matrix1, matrix2);
    }

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
     * @param iStrk - строка первой матрицы для умножения
     * @param matrix2 - вторая матрица
     * @param j - номер столбца второй матрицы
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

    public static void checkMatrix(int[][] matrix) throws Exception {
        for (int i = 0; i < matrix.length - 1; i++) {
            if (matrix[i].length != matrix[i+1].length) {
                throw new Exception("Представленный двумерный массив " + matrix + " не является матрицей");
            }
        }
        System.out.println("Проверка размерности матриц пройдена для матрицы: " + matrix);
    }

    public static int[][] getResultMatrixTemplate(int[][] matrix1, int[][] matrix2) throws Exception {
        int j = matrix1[0].length;
        int i = matrix2.length;
        if (i != j) {
            throw new Exception("Матрицы не могут быть умножены! Количество столбцов первой матрицы: " + j + ", количество строк второй: " + i);
        }
        System.out.println("Матрицы могут быть умножены");
        return new int[matrix1.length][matrix2[0].length];
    }
}

package Matrix;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Currency;

public class MatrixGeneric<T> {
    T objToCheckClass;
    private T[][] MatrixElements;


    public MatrixGeneric(T[][] matrixElements) {
        MatrixElements = matrixElements;
    }
/*
    public void add(MatrixGeneric<T> tMatrixGeneric){
        for (int i=0; i<getMatrixSize()[0];i++){
            for (int j=0; j<getMatrixSize()[1];j++){
                setMatrixElementAt(i,j,MatrixElements[i][j] +tMatrixGeneric.getMatrixElement(i,j));
            }
        }
    }

    public T '+'()


 */
    public void setMatrixElementAt(T element, int row, int column) {
        MatrixElements[row][column] = element;
    }


    public void setMatrixElementAt(T element) {
        for (T[] MatrixVector : MatrixElements) {
            Arrays.fill(MatrixVector, element);
        }
    }

    public int[] getMatrixSize() {
        return new int[]{MatrixElements.length, MatrixElements[0].length};
    }

    public T getMatrixElement(int row, int column) {
        return MatrixElements[row][column];
    }

    public boolean equals(MatrixGeneric<T> anotherMatrix) {
        int[] mySizes = getMatrixSize();
        int[] anotherSizes = anotherMatrix.getMatrixSize();
        if (mySizes[0] != anotherSizes[0] || mySizes[1] != anotherSizes[1])
            return false;
        for (int i = 0; i < mySizes[0]; i++) {
            for (int j = 0; j < mySizes[1]; j++) {
                if (MatrixElements[i][j] != anotherMatrix.getMatrixElement(i, j))
                    return false;
            }
        }
        return true;
    }

    public String toString() {
        return getStringMatrix(MatrixElements);
    }

    private static double round(double value, int places) {
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    private String getStringMatrix(T[][] matrix) {
        StringBuilder StringMatrix = new StringBuilder("");
        for (T[] MatrixRow : matrix) {
            for (T number : MatrixRow) {
                if(objToCheckClass instanceof java.lang.Double)
                    StringMatrix.append(round((Double) number, 2)).append(", ");
                else
                    StringMatrix.append(number).append(", ");
            }
            StringMatrix.deleteCharAt(StringMatrix.length() - 1).deleteCharAt(StringMatrix.length() - 1);
            StringMatrix.append('\n');
        }
        return String.valueOf(StringMatrix);
    }

    private void showMatrix(T[][] Matrix){
        System.out.println(getStringMatrix(Matrix));
    }

}

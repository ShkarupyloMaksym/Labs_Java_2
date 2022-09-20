package Matrix;

import java.util.Arrays;

public final class MatrixImmutble {
    private double[][] MatrixElements;

    private void createNewMatrixElements(int rowSize, int columnSize) {
        MatrixElements = new double[rowSize][columnSize];
    }

    private void createNewMatrixElements(int[] sizes) {
        createNewMatrixElements(sizes[0], sizes[1]);
    }

    public MatrixImmutble() {
        createNewMatrixElements(0, 0);
    }

    public MatrixImmutble(int size) {
        createNewMatrixElements(size, size);
    }

    public MatrixImmutble(double[] Vector) {
        reinitMatrix_diagonalByVector(Vector);
    }

    public MatrixImmutble(double[][] matrixElements) {
        MatrixElements = getCopyOfMatrix(matrixElements);
    }

    public MatrixImmutble(int rowSize, int columnSize) {
        createNewMatrixElements(rowSize, columnSize);
    }

    public void reinitMatrix_diagonalByVector(double[] Vector) {
        createNewMatrixElements(Vector.length, Vector.length);
        for (int i = 0; i < Vector.length; i++) {
            MatrixElements[i][i] = Vector[i];
        }
    }

    @SuppressWarnings("CopyConstructorMissesField")
    public MatrixImmutble(MatrixImmutble oldMatrix) {
        int[] oldMatrixSizes = oldMatrix.getMatrixSize();
        createNewMatrixElements(oldMatrixSizes);
        for (int i = 0; i < oldMatrixSizes[0]; i++) {
            for (int j = 0; j < oldMatrixSizes[1]; j++)
                MatrixElements[i][j] = oldMatrix.getMatrixElement(i, j);
        }
    }

    @SuppressWarnings("CopyConstructorMissesField")
    public MatrixImmutble(Matrix oldMatrix) {
        int[] oldMatrixSizes = oldMatrix.getMatrixSize();
        createNewMatrixElements(oldMatrixSizes);
        for (int i = 0; i < oldMatrixSizes[0]; i++) {
            for (int j = 0; j < oldMatrixSizes[1]; j++)
                MatrixElements[i][j] = oldMatrix.getMatrixElement(i, j);
        }
    }



    public int[] getMatrixSize() {
        return new int[]{MatrixElements.length, MatrixElements[0].length};
    }

    public double getMatrixElement(int row, int column) {
        return MatrixElements[row][column];
    }

    public boolean equals(Matrix anotherMatrix) {
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

    public int hashCode() {
        int code = Arrays.deepHashCode(MatrixElements);
        int[] size = getMatrixSize();
        code += 42 * (size[0] + size[1]);
        return code;
    }

    private static double round(double value, int places) {
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public String toString() {
        return getStringMatrix(MatrixElements);
    }

    private String getStringMatrix(double[][] matrix) {
        StringBuilder StringMatrix = new StringBuilder("");
        for (double[] MatrixRow : matrix) {
            for (double number : MatrixRow) {
                StringMatrix.append(round(number, 2)).append(", ");
            }
            StringMatrix.deleteCharAt(StringMatrix.length() - 1).deleteCharAt(StringMatrix.length() - 1);
            StringMatrix.append('\n');
        }
        return String.valueOf(StringMatrix);
    }

    private void showMatrix(double[][] Matrix){
        System.out.println(getStringMatrix(Matrix));
    }

    private void setMatrixElementAt(double element, int row, int column) {
        MatrixElements[row][column] = element;
    }

    private void setMatrixElementAt(double element) {
        for (double[] MatrixVector : MatrixElements) {
            Arrays.fill(MatrixVector, element);
        }
    }

    public MatrixImmutble getInvertibleMatrix() throws Exception {
        int[] size = getMatrixSize();
        if (size[0] != size[1]) {
            throw new Exception("Matrix.Matrix can`t have Inverible (incorect size)");
        }
        MatrixImmutble Invertible = I(size[0]);
        createInvertible(Invertible);

        return Invertible;
    }

    private void createInvertible(MatrixImmutble I_toMakeInvertible) throws Exception {
        double[][] MatrixElements_forChange = getCopyOfMatrix(MatrixElements);

        for (int i = 0; i < MatrixElements_forChange.length - 1; i++) {
            if (MatrixElements_forChange[i][i] == 0) {
                int j = i + 1;
                while (j < MatrixElements_forChange.length) {
                    if (MatrixElements_forChange[j][i] != 0) {
                        I_toMakeInvertible.subtractRowFromRow(j, i, 1);
                        subtractRowFromRow(MatrixElements_forChange, j, i, 1);
                        break;
                    }
                    j++;
                }
                if (MatrixElements_forChange[i][i] == 0)
                    throw new Exception("Matrix.Matrix hasn't determinant");
            }
            for (int j = i + 1; j < MatrixElements_forChange.length; j++) {

                double factor = MatrixElements_forChange[j][i] / MatrixElements_forChange[i][i];
                I_toMakeInvertible.subtractRowFromRow(i, j, factor);
                subtractRowFromRow(MatrixElements_forChange, i, j, factor);
            }
        }

        for (int i = 0; i < MatrixElements_forChange.length; i++) {
            if (MatrixElements_forChange[i][i] == 0)
                throw new Exception("Matrix.Matrix hasn't determinant");
        }

        for (int i = MatrixElements_forChange.length - 1; i > 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                double factor = MatrixElements_forChange[j][i] / MatrixElements_forChange[i][i];
                I_toMakeInvertible.subtractRowFromRow(i, j, factor);
                subtractRowFromRow(MatrixElements_forChange, i, j, factor);
            }
        }

        for (int i = 0; i < MatrixElements_forChange.length; i++) {
            I_toMakeInvertible.divideRow(i, MatrixElements_forChange[i][i]);
        }

    }

    private void divideRow(int Row, double number) {
        for (int i = 0; i < getMatrixSize()[1]; i++) {
            setMatrixElementAt(getMatrixElement(Row, i) / number, Row, i);
        }
    }

    private void subtractRowFromRow(int startRow, int endRow, double number) {
        for (int i = 0; i < getMatrixSize()[1]; i++) {
            double newNumber = getMatrixElement(endRow, i) - getMatrixElement(startRow, i) * number;
            setMatrixElementAt(newNumber, endRow, i);
        }
    }

    private void subtractRowFromRow(double[][] MatrixForOperation, int startRow, int endRow, double number) {
        for (int i = 0; i < MatrixForOperation[startRow].length; i++) {
            MatrixForOperation[endRow][i] -= MatrixForOperation[startRow][i] * number;
        }
    }

    private double[][] getCopyOfMatrix(double[][] CopiedMatrix) {
        int[] sizes = new int[]{CopiedMatrix.length, CopiedMatrix[0].length};
        double[][] newMatrix = new double[sizes[0]][sizes[1]];
        for (int i = 0; i < sizes[0]; i++) {
            newMatrix[i] = Arrays.copyOf(CopiedMatrix[i], sizes[1]);
        }
        return newMatrix;
    }

    private MatrixImmutble I(int size) {
        double[] DiagonaleVector = new double[size];
        Arrays.fill(DiagonaleVector, 1);
        return new MatrixImmutble(DiagonaleVector);
    }
}

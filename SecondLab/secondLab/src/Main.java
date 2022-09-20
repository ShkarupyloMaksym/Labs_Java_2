import Matrix.Matrix;
import Matrix.MatrixGeneric;
import Matrix.MatrixImmutble;

import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception {

        Random random = new Random();

        //Matrix matrix1 = new Matrix(new double[][]{{0,2},{1,0}});
        /*
        Matrix matrix = new Matrix(3,3);
        for (int i=0;i<3;i++)
            for (int j=0;j<3;j++)
                matrix.setMatrixElementAt(random.nextDouble()*20-10,i,j);
        System.out.println(matrix);

        System.out.println(matrix.getInvertibleMatrix());
*/
        /*
        Matrix matrix = new Matrix(new double[][]{{1.0,2.0},{3,4}});
        System.out.println(matrix);
        System.out.println(matrix.getInvertibleMatrix());
*/

        MatrixGeneric<String> matrixGeneric= new MatrixGeneric<String>(new String[2][3]);
        //System.out.println(matrixGeneric);
        matrixGeneric.setMatrixElementAt("a");
        //matrixGeneric.setMatrixElementAt("Smth",1,1);
        System.out.println(matrixGeneric);


    }
}
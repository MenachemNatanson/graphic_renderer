package primitives;

import java.util.Arrays;

/**
 * A Matrix of 3x3 coordinates
 */
public class Matrix {

    private Coordinate[][] _matrix;

    /* ********* Constructors ***********/
    /**
     * A new Matrix
     * @param matrix 3x3 coordinates
     */
    public Matrix(Coordinate[][] matrix) {
        _matrix =new Coordinate[3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                _matrix[i][j] = new Coordinate(matrix[i][j]);
            }
        }

    }

    /**
     * A new Matrix
     * @param matrix 3x3 doubles
     */
    public Matrix(double[][] matrix) {
        _matrix =new Coordinate[3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                _matrix[i][j] = new Coordinate(matrix[i][j]);
            }
        }
    }

    /**
     * A new Matrix
     * @param matrix other matrix
     */
    public Matrix(Matrix matrix) {
        _matrix =new Coordinate[3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                _matrix[i][j] = new Coordinate(matrix._matrix[i][j]);
            }
        }

    }

    /* ************* Getters/Setters *******/
    /**
     * get Matrix coordinates
     * @return 3x3 coordinates
     */
    public Coordinate[][] getMatrix() {
        return _matrix;
    }

    /* ************** Admin *****************/
    @Override
    public String toString() {
        return "MT{" +
                Arrays.toString(_matrix) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix = (Matrix) o;
        return Arrays.equals(_matrix, matrix._matrix);
    }

    /* ************* Operations ***************/
    /**
     * multiply two matrix's
     * @param matrix2 matrix 2
     * @return [this]x[matrix2]
     */
    public Matrix multiply(Matrix matrix2){
        Coordinate[][] matrix = new Coordinate[3][3];
        for(int i =0 ;i<3;i++) {
            for (int j = 0; j < 3; j++) {
                matrix[i][j] = new Coordinate(0);
            }
        }
        for(int i =0 ;i<3;i++){
            for(int j=0;j<3;j++){
                for(int w=0;w<3;w++){
                    matrix[i][j] = matrix[i][j].add(_matrix[i][w].multiply(matrix2.getMatrix()[w][j]));
                }
            }
        }
        return new Matrix( matrix);
    }
}

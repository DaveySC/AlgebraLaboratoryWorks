import java.util.Arrays;

public class BinaryRelation {
    int[][] matrix;
    int row, column;

    public BinaryRelation(int row, int column) {
        this.row = row;
        this.column = column;
        this.matrix = new int[row][column];
    }


    public void defineBinaryRelationTypes() {
        print();
        isItReflexive();
        isItSymmetrical();
        isItAntisymmetric();
        isItTransitive();
    }


    private void isItReflexive() {
        int i = 0;
        while (i < row) {
            if (this.matrix[i][i] == 0) {
                System.out.println("Рефлексивность: Нет.");
                return;
            }
            i++;
        }
        System.out.println("Рефлексивность: Да.");
    }

    private void isItSymmetrical() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (this.matrix[i][j] != this.matrix[j][i]) {
                    System.out.println("Симметричность: Нет.");
                    return;
                }
            }
        }
        System.out.println("Симметричность: Да.");
    }

    private void isItAntisymmetric() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (this.matrix[i][j] == 1) {
                    if (this.matrix[i][j] == this.matrix[j][i] && i != j) {
                        System.out.println("Антисимметричность: Нет.");
                        return;
                    }
                }
            }
        }
        System.out.println("Антисимметричность: Да.");
    }


    private int[][] multiply(int[][] matrix, int[][] secondMatrix) {
        int[][] resMatrix = new int[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                for (int k = 0; k < row; k++) {
                    resMatrix[i][j] += matrix[i][k] * secondMatrix[k][j];
                }
            }
        }
        return resMatrix;
    }


    private void isItTransitive() {
        int[][] secondMatrix;
        secondMatrix = multiply(this.matrix, this.matrix);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (this.matrix[i][j] == 0 && secondMatrix[i][j] == 1) {
                    System.out.println("Транизитивность: Нет.");
                    return;
                }
            }
        }
        System.out.println("Транизитивность: Да.");
    }

    private boolean isItEqualMatrix(int[][] a, int[][] b) {
        if (a.length > b.length || a.length < b.length) {
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                if (a[i][j] != b[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setElem(int row, int column, int value) {
        this.matrix[row][column] = value;
    }

    public void doReflexiveClosure() {
        int[][] secondMatrix = new int[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (i == j) {
                    secondMatrix[i][j] = 1;
                }
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                this.matrix[i][j] += secondMatrix[i][j];
            }
        }
        setMatrixBinary();
    }

    public void doSymmetricalClosure() {
        int[][] secondMatrix = new int[row][column];
        //транспонирую матрицу
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                secondMatrix[j][i] = this.matrix[i][j];
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                this.matrix[i][j] += secondMatrix[i][j];
            }
        }
        setMatrixBinary();
    }


    public void doTransitiveClosure() {
        int[][] sumMatrix = Arrays.copyOf(this.matrix, this.matrix.length);
        int[][] secondMatrix = Arrays.copyOf(this.matrix, this.matrix.length);
        for (int z = 0; z < row; z++) {
            this.matrix = multiply(secondMatrix, this.matrix);
            setMatrixBinary();
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    if (sumMatrix[i][j] == 0) {
                        sumMatrix[i][j] += this.matrix[i][j];
                    }
                }
            }
        }
        this.matrix = sumMatrix;
    }


    public void doEquivalenceClosure() {
        doSymmetricalClosure();
        doTransitiveClosure();
        doReflexiveClosure();
    }



    private void setMatrixBinary() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (this.matrix[i][j] > 1) {
                    this.matrix[i][j] = 1;
                }
            }
        }
    }

    public void print() {
        for (int i = 0; i < row; i++) {
            StringBuilder helper = new StringBuilder();
            for (int j = 0; j < column; j++) {
                helper.append(this.matrix[i][j] + " ");
            }
            System.out.println(helper);
        }
    }
}

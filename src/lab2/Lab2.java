package lab2;
import org.w3c.dom.ls.LSOutput;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.*;
import java.util.stream.Collectors;


public class Lab2 {
    int[][] matrix;
    int[][] table;
    int[][] startMatrix;//don't change
    int row, column;
    ArrayList<String> gSet;
    ArrayList<String> mSet;

    public Lab2(int row, int column) {
        this.row = row;
        this.column = column;
        this.matrix = new int[row][column];
        this.startMatrix = new int[row][column];
        this.mSet = new ArrayList<>();
        this.gSet = new ArrayList<>();
        this.table = null;
    }


    public boolean defineBinaryRelationTypes() {
        return  isItReflexive() && isItSymmetrical() && isItTransitive();
    }


    private boolean isItReflexive() {
        int i = 0;
        while (i < row) {
            if (this.matrix[i][i] == 0) {
                return false;
            }
            i++;
        }

        return true;
    }

    private boolean isItSymmetrical() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (this.matrix[i][j] != this.matrix[j][i]) {
                    return false;

                }
            }
        }
        return true;
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


    private boolean isItTransitive() {
        int[][] secondMatrix;
        secondMatrix = multiply(this.matrix, this.matrix);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (this.matrix[i][j] == 0 && secondMatrix[i][j] == 1) {
                    return false;
                }
            }
        }
        return true;
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
        this.startMatrix[row][column] = value;
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
        doReflexiveClosure();
        doTransitiveClosure();
        doSymmetricalClosure();
    }


    public void solveFirst() {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.column; j++) {
                this.matrix[i][j] = scanner.nextInt();
            }
        }
        if (defineBinaryRelationTypes()) {
            System.out.println("Отношение эквивалентное");
        } else {
            System.out.println("Отношение не эквивалентное");
        }
        doEquivalenceClosure();
        print();
        if (defineBinaryRelationTypes()) {
            System.out.println("Отношение эквивалентное");
        } else {
            System.out.println("Отношение не эквивалентное");
        }
        doSetFactor();
    }

    public void solveSecond(int number) {
        doHasse(number);
    }

    public void solveThird() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите размер множества G и множества M");
        int gSize = scanner.nextInt();
        int mSize = scanner.nextInt();
        System.out.println("Введите Множество G");
        for (int i = 0; i < gSize; i++) {
            this.gSet.add(scanner.next());
        }
        System.out.println("Введите множество M");
        for (int i = 0; i < mSize; i++) {
            this.mSet.add(scanner.next());
        }
        this.table = new int[gSize][mSize];
        System.out.println("Введите таблицу");
        for (int i = 0; i < gSize ; i++) {
            for (int j = 0; j < mSize; j++) {
                this.table[i][j] = scanner.nextInt();
            }
        }
        doGClosure();
    }

    private void doGClosure(){
        HashSet<HashSet<String>> mainList = new HashSet<>();
        mainList.add(new HashSet<>(gSet));
        for (int i = 0; i < mSet.size(); i++) {
            HashSet<String> helper = new HashSet<>();
            for (int j = 0; j < gSet.size(); j++) {
                if (this.table[j][i] == 1) {
                    helper.add(gSet.get(j));
                }
            }
            ArrayList<HashSet<String>> mainListHelper = new ArrayList<>();

            for (HashSet<String> strings : mainList) {
                HashSet<String> intersection = new HashSet<String>(helper);
                intersection.retainAll(strings);
                mainListHelper.add(intersection);
            }
            mainList.addAll(mainListHelper);
        }

        for (HashSet<String> elem : mainList) {
            System.out.println(elem);
        }
        System.out.println("-------------------------------------");

        for (HashSet<String> elem : mainList) {
            boolean initStatus = true;
            HashSet<String> bigHashSet = new HashSet<>();
            for (String string : elem) {
                HashSet<String> miniHashSet = new HashSet<>();
                int pos = gSet.indexOf(string);
                for (int j = 0; j < gSet.size(); j++) {
                    if (this.table[pos][j] == 1) {
                        miniHashSet.add(mSet.get(j));
                    }
                }
                if (initStatus) {
                    bigHashSet.addAll(miniHashSet);
                    initStatus = false;
                } else {
                    bigHashSet.retainAll(miniHashSet);
                }
            }
            if (elem.isEmpty()) {
                System.out.println(elem + " " + gSet);
            } else {
                System.out.println(elem + " " + bigHashSet);
            }
        }
    }


        //clone need dodelay 0)0)0)
    private void diagramPrint( HashSet<HashSet<String>> mainList) {

        HashSet<HashSet<String>> superHelper = new HashSet<>(mainList);
        for (HashSet<String> elem : mainList) {
            HashSet<HashSet<String>> helper = new HashSet<>();
            if (elem.size() == 0) {
                System.out.println("[]");
                continue;
            }
            helper.add(elem);
            superHelper.remove(elem);
            for (HashSet<String> superHelperElem : superHelper) {
                boolean need = true;
                for (HashSet<String> helperHelper : helper) {
                    HashSet<String> lol = new HashSet<>(helperHelper);
                    lol.retainAll(superHelper);
                    if (lol.size() != 0) {
                        need = false;
                        break;
                    }
                }
                if (need) {
                    helper.add(superHelperElem);
                }
            }


        }

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

    public void goBack() {
        for (int i = 0; i < this.row ; i++) {
            for (int j = 0; j < this.column; j++) {
                this.matrix[i][j] = this.startMatrix[i][j];
            }
        }
    }

    public void doSetFactor() {
        int[] visited = new int[this.row];
        ArrayList<ArrayList<Integer>> factor = new ArrayList<>();
        for (int i = 0; i < this.row; i++) {
            factor.add(new ArrayList<>());
        }

        for (int i = 0; i < this.row ; i++) {
            for (int j = 0; j < this.column; j++) {
                if (this.matrix[i][j] == 1) {
                    if (visited[j] != 1) {
                        visited[j] = 1;
                        factor.get(i).add(j + 1);
                    }
                }
            }
        }


        StringBuilder factorAnswer = new StringBuilder();
        for (int i = 0; i < this.row ; i++) {
            String answer = "{";
            ArrayList<Integer> helper = factor.get(i);
            answer += helper.stream().map(Object::toString)
                    .collect(Collectors.joining(", "));
            answer += "}";
            if (helper.size() != 0) {
                factorAnswer.append(answer).append(" ");
            }
        }
        System.out.println("Фактор множество: " + factorAnswer);
        factorAnswer.setLength(0);
        for (int i = 0; i < this.row ; i++) {
            ArrayList<Integer> helper = factor.get(i);
            if (helper.size() != 0) {
                String answer = "{";
                answer += Collections.min(helper);
                answer += "}";
                factorAnswer.append(answer).append(" ");
            }
        }
        System.out.println("Система представителей: " + factorAnswer);
    }



    public void doHasse(int number) {
        TreeSet<Integer> dividers = findDividersWith1(number);
        printHasseDiagram(dividers, number);

        dividers = findDividersWithout1(number);
        dividers.add(number);
        printHasseDiagram(dividers, number);

    }

    private  TreeSet<Integer> findDividersWith1(int number) {
        TreeSet<Integer> dividers = new TreeSet<>();
        for (int i = 1; i * i <= number; i++) {
            if (number % i == 0) {
                dividers.add(i);
                dividers.add(number / i);
            }
        }
        return dividers;

    }

    private TreeSet<Integer> findDividersWithout1(int number) {
        TreeSet<Integer> dividers = new TreeSet<>();
            for (int i = 2; i * i <= number; i++) {
             if (number % i == 0) {
                    dividers.add(i);
                    dividers.add(number / i);
                 }
            }
            return dividers;
    }


    private void printHasseDiagram(TreeSet<Integer> dividers, int number) {
        ArrayList<ArrayList<Integer>> minimums = new ArrayList<>();
        int level = 0;
        while (!dividers.isEmpty()) {
            minimums.add(new ArrayList<Integer>());
            minimums.get(level).add(dividers.pollFirst());
            for (Integer elem : dividers) {
                boolean need = true;
                for (int j = 0; j < minimums.get(level).size(); j++) {
                    if (elem % minimums.get(level).get(j) == 0) {
                        need = false;
                        break;
                    }
                }
                if (need) {
                    minimums.get(level).add(elem);
                }
            }
            dividers.removeAll(minimums.get(level));
            level += 1;
        }
        for (ArrayList<Integer> elem : minimums) {
            StringBuilder ans = new StringBuilder("");
            for (Integer underElem : elem) {
                ans.append(underElem + " ");
            }
            System.out.println(ans);
        }
        System.out.println();
        printDependencies(minimums);
    }

    private void printDependencies( ArrayList<ArrayList<Integer>> minimums) {
        int level = minimums.size() - 1;
        while (level > 0) {
            StringBuilder answer = new StringBuilder("{");
            for (int i = 0; i < minimums.get(level).size(); i++) {
                answer.append("(").append(minimums.get(level).get(i)).append(" -> ");
                for (int j = 0; j < minimums.get(level - 1).size(); j++) {
                    if (minimums.get(level).get(i) % minimums.get(level - 1).get(j) == 0) {
                        answer.append(minimums.get(level - 1).get(j)).append(" ");
                    }
                }
                answer.append(")");
            }
            answer.append("}");
            System.out.println(answer);
            level -= 1;
        }
        System.out.println();


        if (minimums.get(0).size() == 1) {
            System.out.println("Наименьший элемент: " + minimums.get(0).get(0));
            System.out.println("Минимальные элементы: " + minimums.get(0).get(0));
        } else {
            System.out.println("Наименьший элемент: отсутствует" );
            StringBuilder ans = new StringBuilder("Минимальные элементы: ");
            for (Integer elem : minimums.get(0)) {
                ans.append(elem).append(" ");
            }
            System.out.println(ans);
        }

        if (minimums.get(minimums.size() - 1).size() == 1) {
            System.out.println("Наибольший элемент: " + minimums.get(minimums.size() - 1).get(0));
            System.out.println("Максимальные элементы: " + minimums.get(minimums.size() - 1).get(0));
        } else {
            System.out.println("Наибольший элемент: отсутствует" );
            StringBuilder ans = new StringBuilder("Максимальные элементы: ");
            for (Integer elem : minimums.get(minimums.size() - 1)) {
                ans.append(elem).append(" ");
            }
            System.out.println(ans);
        }
    }
}


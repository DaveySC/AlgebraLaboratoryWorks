import lab1.BinaryRelation;
import lab2.Lab2;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите размер матрицы бинарных отношений");
        int n = scanner.nextInt();
        Lab2 lab2 = new Lab2(n, n);
        lab2.solveFirst();
        lab2.solveSecond(60);
        lab2.solveThird();
    }

}

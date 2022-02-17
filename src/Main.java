import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        BinaryRelation binaryRelation = new BinaryRelation(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                binaryRelation.setElem(i,j,scanner.nextInt());
            }
        }
        binaryRelation.defineBinaryRelationTypes();
        binaryRelation.doEquivalenceClosure();
        binaryRelation.defineBinaryRelationTypes();
    }


}

package lab3;

public class lab3 {
    int[][] cayleyTable;//= new int[][];
    int n;

    public void checkAssociative() {

    }
    public void checkCommutative() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.cayleyTable[i][j] != this.cayleyTable[j][i]) {
                    System.out.println("Коммутативность: Нет.");
                    return;
                }
            }
        }
        System.out.println("Коммутативность: Да.");
    }
    public void checkIdempotence() {

    }
    public void checkInvertibility() {

    }
    public void checkDistributivity() {

    }
}

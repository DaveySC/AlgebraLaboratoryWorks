package Lab4;

import java.util.*;

public class Lab4 {
    private static final Scanner scanner = new Scanner(System.in);

    private static int rSize;

    public static void task1() {
        System.out.println();
        System.out.println("Введите число элементов полугруппы:");
        int semiGroupSize = scanner.nextInt();
        System.out.println("Введите полугруппу:");
        List<Integer> semiGroup = new ArrayList<>();
        for (int i = 0; i < semiGroupSize; i++) {
            semiGroup.add(scanner.nextInt());
        }
        System.out.println("Введите таблицу кэли:");
        List<List<Integer>> kayleeTable = new ArrayList<>();
        for (int i = 0; i < semiGroupSize; i++) {
            List<Integer> helper = new ArrayList<>();
            for (int j = 0; j < semiGroupSize; j++) {
                helper.add(scanner.nextInt());
            }
            kayleeTable.add(helper);
        }

        System.out.println("Введите число элементов порождающего множества:");
        int setSize = scanner.nextInt();
        System.out.println("Введите порождающие множество:");
        List<Integer> set = new ArrayList<>();
        for (int i = 0; i < setSize; i++) {
            set.add(scanner.nextInt());
        }

        List<Set<Integer>> answerSetList = new ArrayList<>();
        Set<Integer> helper = new HashSet<>(set);
        answerSetList.add(helper);
        for (int i = 0; i < 1_000; i++) {
            Set<Integer> sumHelper = new HashSet<>();
            for (Integer elem1 : helper) {
                for (Integer elem2 : set) {
                    sumHelper.add(kayleeTable.get(semiGroup.indexOf(elem1)).get(semiGroup.indexOf(elem2)));
                }
            }
            helper.addAll(sumHelper);
            answerSetList.add(helper);
        }

        Set<Integer> answerSet = new HashSet<>();
        for (Set<Integer> integers : answerSetList) {
            answerSet.addAll(integers);
        }
        System.out.println("Подполугрпуппа <X>:");
        for (Integer element : answerSet) {
            System.out.print(element + " ");
        }

    }
    public void task2() {

    }
    public static void task3() {
        System.out.println();
        System.out.println("Введите размер множества символов A:");
        int aSize = scanner.nextInt();
        System.out.println("Введите множество A: ");
        List<String> aSet = new ArrayList<>();
        for (int i = 0; i < aSize; i++) {
            aSet.add(scanner.next());
        }

        System.out.println("Введите размер множества определяющих соотношений R: ");
        rSize = scanner.nextInt();
        String sldawl = scanner.nextLine();
        System.out.println("Введите определяющие соотношения R:");
        String[][] rSet = new String[rSize][2];
        for (int i = 0; i < rSize; i++) {
            String str = scanner.nextLine();
            StringBuilder helper = new StringBuilder();
            for (int j = 0; j < str.length(); j++) {
                if (str.charAt(j) == '=') {
                    rSet[i][0] = helper.toString();
                    helper.setLength(0);
                } else {
                    helper.append(str.charAt(j));
                }
            }
            rSet[i][1] = helper.toString();
        }

        boolean status = true;
        Set<String> answer = new HashSet<>(aSet);
        int length = 2;
        Set<String> lastStep = new HashSet<>(aSet);

        while (status) {
            Set<String> lastStepHelper = new HashSet<>();
            int answerSize = answer.size();
            for (String str : lastStep) {
                for (int i = 0; i < aSize; i++) {
                    String helper = str + aSet.get(i);
                    helper = doIter(helper, rSet);
                    answer.add(helper);
                    lastStepHelper.add(helper);
                }
            }
            if (answer.size() == answerSize) {
                status = false;
            }

            lastStep = new HashSet<>(lastStepHelper);
        }

        List<String> listAnswer = new ArrayList<>(answer);
        listAnswer.sort(String.CASE_INSENSITIVE_ORDER);

        System.out.println(listAnswer);

        for (int i = 0; i < listAnswer.size(); i++) {

            for (int j = 0; j < listAnswer.size(); j++) {
                String elem1 = listAnswer.get(i);
                elem1 += listAnswer.get(j);

                System.out.print( doIter(elem1, rSet) + " ");
            }
            System.out.println();
        }




    }

    private static String doIter(String str, String[][] rSet) {
        int counter = 0;
        while (counter < 1_000) {
            counter += 1;
            for (int i = 0; i < rSize; i++) {
                while (str.contains(rSet[i][0])) {
                    str = str.replace(rSet[i][0], rSet[i][1]);
                }
            }
        }
        return str;
    }


}

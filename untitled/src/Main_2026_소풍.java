import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_2026_소풍 {

    static boolean[][] isfriend;
    static Person[] people;
    static int k;
    static int n;
    static int[] resultArray;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        k = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());
        int f = Integer.parseInt(st.nextToken());
        people = new Person[n + 1];
        for (int i = 1; i <= n; i++) {
            people[i] = new Person(i);
        }
        isfriend = new boolean[n + 1][n + 1];
        for (int i = 0; i < f; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            friend(a, b);
        }
        for (int i = 1; i <= n; i++) {
            people[i].fridendsList.sort(Integer::compare);
        }
        resultArray = new int[k];
        String result = findAndGetResult();
        bw.write(result);
        bw.close();
        br.close();
    }

    static void friend(int a, int b) {
        int small = Math.min(a, b);
        int big = Math.max(a, b);
        isfriend[small][big] = true;
        people[small].friends++;
        people[small].fridendsList.add(big);
    }

    static String findAndGetResult(){
        boolean isPossible = false;
        for (int i = 1; i <= n; i++) {
            if (findGroup(i, 1)) {
                isPossible = true;
                break;
            }
        }
        return getResult(isPossible);
    }

    static boolean findGroup(int num, int depth) {
        resultArray[depth - 1] = num;
        if (depth == k) {
            return true;
        }
        if (people[num].friends + depth < k) {
            return false;
        }
        nextNum:
        for (int i : people[num].fridendsList) {
            for (int j = 0; j < depth; j++) {
                int prev = resultArray[j];
                if (!isfriend[prev][i]) {
                    continue nextNum;
                }
            }
            if (findGroup(i, depth + 1)) {
                return true;
            }
        }
        return false;
    }

    static String getResult(boolean isPossible) {
        StringBuilder sb = new StringBuilder();
        if (isPossible) {
            for (int i = 0; i < k; i++) {
                sb.append(resultArray[i]).append("\n");
            }
        } else {
            sb.append(-1);
        }
        return sb.toString();
    }

    static class Person {
        int num;
        int friends;
        List<Integer> fridendsList = new ArrayList<>();

        Person(int num) {
            this.num = num;
        }
    }
}
import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_14404_동전시스템 {

    static int small;
    static int big;
    static int x;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken());
        small = Math.min(a, b);
        big = Math.max(a, b);
        int result = checkAndCount();
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int checkAndCount() {
        boolean[] xPossible = possibleNumbersOfX();
        if (canMakeAllCoin(xPossible)) {
            return -1;
        }
        return countAllPossibleNumber(xPossible);
    }

    static boolean[] possibleNumbersOfX(){
        boolean[] xPossible = new boolean[big + 1];
        xPossible[0] = true;
        for (int i = x; i <= big; i += x) {
            xPossible[i] = true;
        }
        return xPossible;
    }

    static boolean canMakeAllCoin(boolean[] possible) {
        return possible[small] && possible[big];
    }

    static int countAllPossibleNumber(boolean[] xPossible) {
        int result = 0;
        for (int i = 1; i <= big; i++) {
            if (isPossibleNumber(xPossible, i)) {
                result++;
            }
        }
        return result;
    }

    static boolean isPossibleNumber(boolean[] xPossible, int num) {
        boolean[] isPossible = Arrays.copyOf(xPossible, big + 1);
        for (int j = num; j <= big; j++) {
            if (isPossible[j - num]) {
                isPossible[j] = true;
            }
        }
        return canMakeAllCoin(isPossible);
    }
}
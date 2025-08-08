import java.io.*;
import java.util.StringTokenizer;

public class Main_14552_Mahjong {

    static int[] nums;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        nums = new int[10];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < 13; i++) {
            int n = Integer.parseInt(st.nextToken());
            nums[n]++;
        }
        String result = possibleNumbers();
        bw.write(result);
        bw.close();
        br.close();
    }

    static String possibleNumbers() {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (int i = 1; i <= 9; i++) {
            if (nums[i] == 4) continue;
            nums[i]++;
            if (isPossibleHand()) {
                sb.append(i).append(" ");
                count++;
            }
            nums[i]--;
        }
        if (count == 0) {
            sb.append(-1);
        }
        return sb.toString();
    }

    static boolean isPossibleHand() {
        if (is7Heads()) {
            return true;
        }
        for (int i = 1; i <= 9; i++) {
            if (nums[i] < 2) {
                continue;
            }
            makeHead(i);
            boolean isPossible = makeBody(12);
            resetHead(i);
            if (isPossible) {
                return true;
            }
        }
        return false;
    }

    static boolean is7Heads() {
        int count = 0;
        for (int i = 1; i <= 9; i++) {
            if (nums[i] == 2) {
                count++;
            }
        }
        return count == 7;
    }

    static void makeHead(int num) {
        nums[num] -= 2;
    }

    static void resetHead(int num) {
        nums[num] += 2;
    }

    static boolean makeBody(int count) {
        if (count == 0) {
            return true;
        }
        int nextCount = count - 3;
        boolean result;
        int i = 1;
        while (nums[i] == 0) {
            i++;
        }
        if (nums[i] >= 3) {
            nums[i] -= 3;
            result = makeBody(nextCount);
            nums[i] += 3;
            if (result) {
                return true;
            }
        }
        if (i < 8 && nums[i] > 0 && nums[i + 1] > 0 && nums[i + 2] > 0) {
            nums[i]--;
            nums[i + 1]--;
            nums[i + 2]--;
            result = makeBody(nextCount);
            nums[i]++;
            nums[i + 1]++;
            nums[i + 2]++;
            if (result) {
                return true;
            }
        }
        return false;
    }
}
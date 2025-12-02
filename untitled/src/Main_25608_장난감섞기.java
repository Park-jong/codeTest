import java.io.*;
import java.util.StringTokenizer;

public class Main_25608_장난감섞기 {
    static int[] sum;
    static int[] front;
    static int[] back;
    static int[] maxInArray;
    static boolean[] isUsed;
    static int n;
    static int m;
    static int max;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        int[][] array = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                array[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int result = findMaxSequenceSum(array);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMaxSequenceSum(int[][] array) {
        sum = new int[n];
        front = new int[n];
        back = new int[n];
        maxInArray = new int[n];
        isUsed = new boolean[n];
        for (int i = 0; i < n; i++) {
            int backSum = 0;
            int inArray = 0;
            for (int j = 0; j < m; j++) {
                sum[i] += array[i][j];
                inArray += array[i][j];
                if (inArray < 0) {
                    inArray = 0;
                }
                maxInArray[i] = Math.max(maxInArray[i], inArray);
                backSum += array[i][m - j - 1];
                if (sum[i] > front[i]) {
                    front[i] = sum[i];
                }
                if (backSum > back[i]) {
                    back[i] = backSum;
                }
            }
        }
        findMax(0, 0);
        return max;
    }

    static void findMax(int depth, int value) {
        if (depth == n) return;
        if (depth == 0) {
            for (int i = 0; i < n; i++) {
                if (back[i] <= 0 || isUsed[i]) continue;
                isUsed[i] = true;
                max = Math.max(max, maxInArray[i]);
                findMax(depth + 1, back[i]);
                isUsed[i] = false;
            }
        } else {
            for (int i = 0; i < n; i++) {
                if (front[i] <= 0 || isUsed[i]) continue;
                max = Math.max(max, value + front[i]);
                if (sum[i] <= 0) continue;
                isUsed[i] = true;
                findMax(depth + 1, value + sum[i]);
                isUsed[i] = false;
            }
        }
    }
}
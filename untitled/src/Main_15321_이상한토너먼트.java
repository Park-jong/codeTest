import java.io.*;

public class Main_15321_이상한토너먼트 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        int[] power = new int[n];
        for (int i = 0; i < n; i++) {
            power[i] = Integer.parseInt(br.readLine());
        }
        int result = findMinDifference(power);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMinDifference(int[] power) {
        int n = power.length;
        int[][] range = new int[n][n];
        int[][] sub = new int[n][n];
        for (int i = 0; i < n; i++) {
            range[i][i] = power[i];
        }
        for (int i = 1; i < n; i++) {
            for (int left = 0, right = left + i; right < n; left++, right++) {
                sub[left][right] = Integer.MAX_VALUE;
                for (int mid = left; mid < right; mid++) {
                    range[left][right] = Math.max(range[left][mid], range[mid + 1][right]);
                    sub[left][right] = Math.min(sub[left][right], sub[left][mid] + sub[mid + 1][right] + Math.abs(range[left][mid] - range[mid + 1][right]));
                }
            }
        }
        return sub[0][n - 1];
    }
}
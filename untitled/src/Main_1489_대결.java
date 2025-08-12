import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_1489_대결 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        int[] A = new int[n];
        int[] B = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            B[i] = Integer.parseInt(st.nextToken());
        }
        int result = getMaxScore(n, A, B);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int getMaxScore(int n, int[] A, int[] B) {
        Arrays.sort(A);
        Arrays.sort(B);
        boolean[][][] dp = new boolean[n + 1][n + 1][n + 1];
        dp[0][0][0] = true;
        for (int i = 0; i < n; i++) {
            for (int win = 0; win <= i; win++) {
                for (int draw = 0; draw <= i - win; draw++) {
                    int lose = i - win - draw;
                    if (dp[win][draw][lose]) {
                        if (A[i] > B[i - lose]) {
                            dp[win + 1][draw][lose] = true;
                        } else if (A[i] == B[i - lose]) {
                            dp[win][draw + 1][lose] = true;
                            dp[win][draw][lose + 1] = true;
                        } else {
                            dp[win][draw][lose + 1] = true;
                        }
                    }
                }
            }
        }
        int result = 0;
        for (int win = 0; win <= n; win++) {
            for (int draw = 0; draw <= n - win; draw++) {
                int lose = n - win - draw;
                if (dp[win][draw][lose]) {
                    result = Math.max(result, win * 2 + draw);
                }
            }
        }
        return result;
    }
}
import java.io.*;
import java.util.StringTokenizer;

public class Main_17953_dp_디저트 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] dessert = new int[n][m];
        int[][] dp = new int[n][m];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                dessert[j][i] = Integer.parseInt(st.nextToken());
            }
        }
        int result = eat(dessert, dp, n, m);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int eat(int[][] dessert, int[][] dp, int n, int m){
        for (int i = 0; i < m; i++) {
            dp[0][i] = dessert[0][i];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for(int k = 0; k < m; k++){
                    if(j == k) {
                        dp[i][j] = Math.max(dp[i][j], dp[i-1][k] + dessert[i][j] / 2);
                    }
                    else {
                        dp[i][j] = Math.max(dp[i][j], dp[i-1][k] + dessert[i][j]);
                    }
                }
            }
        }
        int result = 0;
        for(int i = 0; i < m; i++){
            result = Math.max(dp[n-1][i], result);
        }
        return result;
    }
}
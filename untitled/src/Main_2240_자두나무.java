import java.io.*;
import java.util.StringTokenizer;

public class Main_2240_자두나무 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int t = Integer.parseInt(st.nextToken());
        int w = Integer.parseInt(st.nextToken());
        int[] jadoo = new int[t];
        for(int i = 0; i < t; i++){
            jadoo[i] = Integer.parseInt(br.readLine());
        }
        int result = maxCount(t, w, jadoo);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int maxCount(int t, int w, int[] jadoo){
        int[][] dp = new int[w + 2][t + 1];
        for(int i = 1; i < w + 2; i++){
            int position = i % 2 == 0 ? 2 : 1;
            for(int j = 1; j <= t; j++){
                dp[i][j] = Math.max(dp[i - 1][j - 1], dp[i][j - 1]);
                if(jadoo[j - 1] == position){
                    dp[i][j]++;
                }
            }
        }
        int result = 0;
        for(int i = 1; i < w + 2; i++){
            result = Math.max(result, dp[i][t]);
        }
        return result;
    }
}
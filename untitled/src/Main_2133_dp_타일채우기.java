import java.io.*;

public class Main_2133_dp_타일채우기 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for(int i = 0; i <= n; i = i + 2){
            if(i - 2 >= 0) dp[i] += dp[i - 2] * 3;
            for(int j = 4; i >= j; j = j + 2){
                if(i - j >= 0) dp[i] += dp[i - j] * 2;
            }
        }
        bw.write(String.valueOf(dp[n]));
        bw.flush();
        bw.close();
        br.close();
    }
}
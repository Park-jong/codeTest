import java.io.*;

public class Main_14437_준오는심술쟁이 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int s = Integer.parseInt(br.readLine());
        String name = br.readLine();
        int result = findNumberOfCases(s, name);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findNumberOfCases(int s, String name) {
        int n = name.length();
        int MOD = 1_000_000_007;
        int[] count = new int[s + 1];
        int[] sum = new int[s + 2];
        count[0] = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= s; j++) {
                sum[j + 1] = (sum[j] + count[j]) % MOD;
            }
            for (int j = 1; j <= s; j++) {
                count[j] = (sum[j + 1] + MOD - sum[Math.max(j - 25, 0)]) % MOD;
            }
        }
        return count[s];
    }
}
import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_28017_게임을클리어하자 {

    static int n;
    static int m;
    static int[][] weapons;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        weapons = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                weapons[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int result = findMinTime();
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMinTime() {
        int[] first = new int[n];
        int[] firstIndex = new int[n];
        int[] second = new int[n];
        Arrays.fill(first, Integer.MAX_VALUE);
        Arrays.fill(second, Integer.MAX_VALUE);
        int[][] dp = new int[n][m];
        for (int i = 0; i < m; i++) {
            dp[0][i] = weapons[0][i];
            settingArrays(dp, first, second, firstIndex, 0, i);
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (firstIndex[i - 1] != j) {
                    dp[i][j] = first[i - 1] + weapons[i][j];
                } else {
                    dp[i][j] = second[i - 1] + weapons[i][j];
                }
                settingArrays(dp, first, second, firstIndex, i, j);
            }
        }
        return first[n - 1];
    }

    static void settingArrays(int[][] dp, int[] first, int[] second, int[] firstIndex, int gameIndex, int weaponIndex){
        if (first[gameIndex] > dp[gameIndex][weaponIndex]) {
            second[gameIndex] = first[gameIndex];
            first[gameIndex] = dp[gameIndex][weaponIndex];
            firstIndex[gameIndex] = weaponIndex;
        } else if (second[gameIndex] > dp[gameIndex][weaponIndex]) {
            second[gameIndex] = dp[gameIndex][weaponIndex];
        }
    }
}
import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String sentence = br.readLine();
        int n = Integer.parseInt(br.readLine());
        String[] words = new String[n];
        for (int i = 0; i < n; i++) {
            words[i] = br.readLine();
        }
        int result = findMinCost(sentence, n, words);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMinCost(String sentence, int n, String[] words) {
        int length = sentence.length();
        char[][] alphas = new char[n][];
        for (int i = 0; i < n; i++) {
            alphas[i] = words[i].toCharArray();
            Arrays.sort(alphas[i]);
        }
        char[][][] sub = new char[length][length + 1][];
        String[][] subString = new String[length][length + 1];
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j <= length; j++) {
                subString[i][j] = sentence.substring(i, j);
                sub[i][j] = subString[i][j].toCharArray();
                Arrays.sort(sub[i][j]);
            }
        }
        int[] dp = new int[length + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 1; i <= length; i++) {
            for (int j = 0; j < n; j++) {
                int m = words[j].length();
                if (i - m < 0 || dp[i - m] == Integer.MAX_VALUE) continue;
                if (!isPossible(alphas[j], sub[i - m][i])) continue;
                dp[i] = Math.min(dp[i], dp[i - m] + count(words[j], subString[i - m][i]));
            }
        }
        return dp[length] != Integer.MAX_VALUE ? dp[length] : -1;
    }

    static boolean isPossible(char[] c1, char[] c2) {
        int n = c1.length;
        for (int i = 0; i < n; i++) {
            if (c1[i] != c2[i]) return false;
        }
        return true;
    }

    static int count(String a, String b) {
        int n = a.length();
        int result = 0;
        for (int i = 0; i < n; i++) {
            if (a.charAt(i) != b.charAt(i)) result++;
        }
        return result;
    }
}
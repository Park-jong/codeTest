import java.io.*;
import java.util.StringTokenizer;

public class Main_20542_받아쓰기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        String answer = br.readLine();
        String correct = br.readLine();
        int big = Math.max(n, m);
        int result = Math.min(big, getChangeCount(n, m, answer, correct));
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int getChangeCount(int n, int m, String answer, String correct) {
        int[][] lcs = longestCommon(n, m, answer, correct);
        return requireChange(lcs, n, m);
    }

    static int[][] longestCommon(int n, int m, String answer, String correct) {
        int[][] lcs = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                char a = answer.charAt(i - 1);
                char c = correct.charAt(j - 1);
                if (isSameOrSimilar(a, c)) {
                    lcs[i][j] = lcs[i - 1][j - 1] + 1;
                } else {
                    lcs[i][j] = Math.max(lcs[i][j - 1], lcs[i - 1][j]);
                }
            }
        }
        return lcs;
    }

    static boolean isSameOrSimilar(char a, char c) {
        return a == c || isSimilarI(a, c) || isSimilarV(a, c);
    }

    static boolean isSimilarI(char a, char c) {
        return a == 'i' && (c == 'j' || c == 'l');
    }

    static boolean isSimilarV(char a, char c) {
        return a == 'v' && c == 'w';
    }

    static int requireChange(int[][] lcs, int n, int m) {
        int answer = 0;
        while (true) {
            if (n == 0 || m == 0) {
                answer += m + n;
                break;
            }
            if (lcs[n][m] == lcs[n - 1][m - 1]) {
                answer++;
                n--;
                m--;
            } else if (lcs[n][m] == lcs[n - 1][m]) {
                answer++;
                n--;
            } else if (lcs[n][m] == lcs[n][m - 1]) {
                answer++;
                m--;
            } else {
                n--;
                m--;
            }
        }
        return answer;
    }
}
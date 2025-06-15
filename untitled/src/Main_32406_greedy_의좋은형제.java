import java.io.*;
import java.util.StringTokenizer;

public class Main_32406_greedy_의좋은형제 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        int[][] rice = new int[2][n];
        for (int i = 0; i < 2; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                rice[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int bigger = 0;
        int smaller = 0;
        for (int i = 0; i < n - 2; i++) {
            bigger += Math.max(rice[0][i], rice[1][i]);
            smaller += Math.min(rice[0][i], rice[1][i]);
        }
        int last1 = rice[0][n - 2] + rice[1][n - 1];
        int last2 = rice[1][n - 2] + rice[0][n - 1];
        bigger += Math.max(last1, last2);
        smaller += Math.min(last1, last2);
        int result = bigger - smaller;
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }
}
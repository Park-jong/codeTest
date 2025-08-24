import java.io.*;
import java.util.StringTokenizer;

public class Main_30972_닭강정의전설 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        int[][] chicken = new int[n][n];
        int[][] sum = new int[n + 1][n + 1];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                chicken[i][j] = Integer.parseInt(st.nextToken());
                sum[i + 1][j + 1] = sum[i + 1][j] + sum[i][j + 1] - sum[i][j] + chicken[i][j];
            }
        }
        StringBuilder answer = new StringBuilder();
        int q = Integer.parseInt(br.readLine());
        for (int i = 0; i < q; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int r1 = Integer.parseInt(st.nextToken());
            int c1 = Integer.parseInt(st.nextToken());
            int r2 = Integer.parseInt(st.nextToken());
            int c2 = Integer.parseInt(st.nextToken());
            int query = solveQuery(r1, c1, r2, c2, sum);
            answer.append(query).append("\n");
        }
        bw.write(answer.toString());
        bw.close();
        br.close();
    }

    static int solveQuery(int r1, int c1, int r2, int c2, int[][] sum) {
        return -areaSum(r1, c1, r2, c2, sum) + 2 * areaSum(r1 + 1, c1 + 1, r2 - 1, c2 - 1, sum);
    }

    static int areaSum(int r1, int c1, int r2, int c2, int[][] sum) {
        return sum[r2][c2] - sum[r2][c1 - 1] - sum[r1 - 1][c2] + sum[r1 - 1][c1 - 1];
    }
}
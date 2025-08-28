import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main_2157_여행 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int[][] airplanes = new int[n + 1][n + 1];
        City[] cities = new City[n + 1];
        for (int i = 1; i <= n; i++) {
            cities[i] = new City();
        }
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            airplanes[a][b] = Math.max(airplanes[a][b], c);
        }
        for (int i = 1; i < n; i++) {
            for (int j = i + 1; j <= n; j++) {
                if (airplanes[i][j] > 0) {
                    cities[j].edges.add(new Edge(i, airplanes[i][j]));
                }
            }
        }
        int result = getMaxPoint(n, m, cities);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int getMaxPoint(int n, int m, City[] cities) {
        int[][] dp = new int[m][n + 1];
        int MIN_VALUE = Integer.MIN_VALUE >> 1;
        for (int i = 0; i < m; i++) {
            Arrays.fill(dp[i], MIN_VALUE);
        }
        dp[0][1] = 0;
        for (int i = 1; i < m; i++) {
            for (int j = 2; j <= n; j++) {
                City destination = cities[j];
                for (Edge edge : destination.edges) {
                    int start = edge.start;
                    int point = edge.point;
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][start] + point);
                }
            }
        }
        int result = 0;
        for (int i = 1; i < m; i++) {
            result = Math.max(result, dp[i][n]);
        }
        return result;
    }

    static class City {
        List<Edge> edges = new ArrayList<>();
    }

    static class Edge {
        int start;
        int point;

        Edge(int start, int point) {
            this.start = start;
            this.point = point;
        }
    }
}
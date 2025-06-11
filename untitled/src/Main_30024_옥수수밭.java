import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_30024_옥수수밭 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] corn = new int[n][m];
        boolean[][] isVisited = new boolean[n][m];
        PriorityQueue<Corn> pq = new PriorityQueue<>((a, b) -> Integer.compare(b.value, a.value));
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                corn[i][j] = Integer.parseInt(st.nextToken());
                if (i == 0 || i == n - 1 || j == 0 || j == m - 1) {
                    addCorn(pq, corn, isVisited, i, j);
                }
            }
        }
        int k = Integer.parseInt(br.readLine());
        String result = harvest(pq, corn, isVisited, n, m, k);
        bw.write(result);
        bw.close();
        br.close();
    }

    static String harvest(PriorityQueue<Corn> pq, int[][] corn, boolean[][] isVisited, int n, int m, int k){
        StringBuilder sb = new StringBuilder();
        int[] dx = {1, 0, -1, 0};
        int[] dy = {0, 1, 0, -1};
        for (int i = 0; i < k; i++) {
            Corn current = pq.poll();
            sb.append(current.x + 1).append(" ").append(current.y + 1).append("\n");
            for (int j = 0; j < 4; j++) {
                int x = current.x + dx[j];
                int y = current.y + dy[j];
                if (x >= 0 && x < n && y >= 0 && y < m && !isVisited[x][y]) {
                    addCorn(pq, corn, isVisited, x, y);
                }
            }
        }
        return sb.toString();
    }

    static void addCorn(PriorityQueue<Corn> pq, int[][] corn, boolean[][] isVisited, int x, int y) {
        pq.offer(new Corn(x, y, corn[x][y]));
        isVisited[x][y] = true;
    }

    static class Corn {
        int x;
        int y;
        int value;

        Corn(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }
    }
}
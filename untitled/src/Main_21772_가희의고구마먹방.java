import java.io.*;
import java.util.StringTokenizer;

public class Main_21772_가희의고구마먹방 {

    static boolean[][] isVisited;
    static char[][] map;
    static int[] dx = {0, 0, 1, -1};
    static int[] dy = {1, -1, 0, 0};
    static int max;
    static int r;
    static int c;
    static int t;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        t = Integer.parseInt(st.nextToken());
        isVisited = new boolean[r][c];
        int x = 0;
        int y = 0;
        map = new char[r][c];
        for (int i = 0; i < r; i++) {
            String str = br.readLine();
            for (int j = 0; j < c; j++) {
                map[i][j] = str.charAt(j);
                if (map[i][j] == 'G') {
                    x = i;
                    y = j;
                }
            }
        }
        dfs(0, x, y, 0);
        bw.write(String.valueOf(max));
        bw.close();
        br.close();
    }

    static void dfs(int depth, int x, int y, int eat) {
        if (depth == t) {
            return;
        }
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (nx < 0 || nx >= r || ny < 0 || ny >= c || map[nx][ny] == '#') {
                continue;
            }
            int newEat = eat;
            if (map[nx][ny] == 'S' && !isVisited[nx][ny]) {
                newEat++;
                max = Math.max(max, newEat);
            }
            if (!isVisited[nx][ny]) {
                isVisited[nx][ny] = true;
                dfs(depth + 1, nx, ny, newEat);
                isVisited[nx][ny] = false;
            } else {
                dfs(depth + 1, nx, ny, newEat);
            }
        }
    }
}
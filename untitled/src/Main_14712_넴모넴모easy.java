import java.io.*;
import java.util.StringTokenizer;

public class Main_14712_넴모넴모easy {

    static int n;
    static int m;
    static int[][] board;
    static int count;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        board = new int[n + 1][m + 1];
        dfs(0);
        bw.write(String.valueOf(count));
        bw.close();
        br.close();
    }

    static void dfs(int depth) {
        int x = depth / m;
        int y = depth % m;
        if (depth == n * m) {
            count++;
            return;
        }
        if (board[x][y] + board[x][y + 1] + board[x + 1][y] < 3) {
            board[x + 1][y + 1] = 1;
            dfs(depth + 1);
            board[x + 1][y + 1] = 0;
        }
        dfs(depth + 1);
    }
}
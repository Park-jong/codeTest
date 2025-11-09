import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_16509_장군 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        Position sang = new Position(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        st = new StringTokenizer(br.readLine());
        Position king = new Position(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        int result = findMinMove(sang, king);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMinMove(Position sang, Position king) {
        boolean[][] isVisited = new boolean[10][9];
        Queue<Position> queue = new ArrayDeque<>();
        queue.offer(sang);
        isVisited[sang.r][sang.c] = true;
        int[] oneR = {1, -1, 0, 0};
        int[] oneC = {0, 0, 1, -1};
        int[][] twoR = {{2, 2}, {-2, -2}, {1, -1}, {1, -1}};
        int[][] twoC = {{1, -1}, {1, -1}, {2, 2}, {-2, -2}};
        int[][] threeR = {{3, 3}, {-3, -3}, {2, -2}, {2, -2}};
        int[][] threeC = {{2, -2}, {2, -2}, {3, 3}, {-3, -3}};
        while (!queue.isEmpty()) {
            Position current = queue.poll();
            for (int i = 0; i < 4; i++) {
                int or = oneR[i] + current.r;
                int oc = oneC[i] + current.c;
                if (or == king.r && oc == king.c) continue;
                for (int j = 0; j < 2; j++) {
                    int tr = twoR[i][j] + current.r;
                    int tc = twoC[i][j] + current.c;
                    if (tr == king.r && tc == king.c) continue;
                    int moveR = threeR[i][j] + current.r;
                    int moveC = threeC[i][j] + current.c;
                    if (moveR < 0 || moveC < 0 || moveR >= 10 || moveC >= 9 || isVisited[moveR][moveC]) continue;
                    if (moveR == king.r && moveC == king.c) return current.move + 1;
                    queue.offer(new Position(moveR, moveC, current.move + 1));
                    isVisited[moveR][moveC] = true;
                }
            }
        }
        return -1;
    }

    static class Position {
        int r;
        int c;
        int move;

        Position(int r, int c) {
            this.r = r;
            this.c = c;
        }

        Position(int r, int c, int move) {
            this.r = r;
            this.c = c;
            this.move = move;
        }
    }
}
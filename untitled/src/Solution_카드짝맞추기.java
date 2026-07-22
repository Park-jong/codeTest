import java.util.*;

class Solution_카드짝맞추기 {
    int[][][] visited = new int[4][4][1 << 6];
    int[][] board;
    int min = Integer.MAX_VALUE;
    int findAll;
    int[] dx = {1, -1, 0, 0};
    int[] dy = {0, 0, 1, -1};

    public int solution(int[][] board, int r, int c) {
        this.board = board;
        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Arrays.fill(visited[i][j], Integer.MAX_VALUE);
            }
        }
        for (int[] row : board) {
            for (int card : row) {
                if (card > 0) {
                    findAll |= 1 << (card - 1);
                    count++;
                }
            }
        }
        findCard(0, r, c, 0);
        return min + count;
    }

    void findCard(int bit, int r, int c, int move) {
        if (bit == findAll) {
            min = Math.min(move, min);
            return;
        }
        if (visited[r][c][bit] <= move) return;
        visited[r][c][bit] = move;
        if (board[r][c] != 0) {
            Pair pair = match(r, c);
            board[pair.start[0]][pair.start[1]] = 0;
            board[pair.goal[0]][pair.goal[1]] = 0;
            findCard(bit | (1 << (pair.card - 1)), pair.goal[0], pair.goal[1], move + pair.move);
            board[pair.start[0]][pair.start[1]] = pair.card;
            board[pair.goal[0]][pair.goal[1]] = pair.card;
        }
        for (int i = 0; i < 4; i++) {
            int nr = dx[i] + r;
            int nc = dy[i] + c;
            if (nr < 0 || nc < 0 || nr >= 4 || nc >= 4) continue;
            if (visited[nr][nc][bit] <= move + 1) continue;
            findCard(bit, nr, nc, move + 1);
        }
        for (int i = 0; i < 4; i++) {
            int nr = r;
            int nc = c;
            while (true) {
                nr += dx[i];
                nc += dy[i];
                if (nr < 0 || nc < 0 || nr >= 4 || nc >= 4) {
                    nr -= dx[i];
                    nc -= dy[i];
                    break;
                }
                if (board[nr][nc] != 0) break;
            }
            if (visited[nr][nc][bit] <= move + 1) continue;
            findCard(bit, nr, nc, move + 1);
        }
    }

    Pair match(int r, int c) {
        int card = board[r][c];
        boolean[][] isVisited = new boolean[4][4];
        isVisited[r][c] = true;
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{r, c, 0});
        Pair pair = new Pair();
        pair.start[0] = r;
        pair.start[1] = c;
        pair.card = card;
        bfs:
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nr = dx[i] + cur[0];
                int nc = dy[i] + cur[1];
                if (nr < 0 || nc < 0 || nr >= 4 || nc >= 4 || isVisited[nr][nc]) continue;
                if (board[nr][nc] == card) {
                    pair.move = cur[2] + 1;
                    pair.goal[0] = nr;
                    pair.goal[1] = nc;
                    break bfs;
                }
                queue.offer(new int[]{nr, nc, cur[2] + 1});
                isVisited[nr][nc] = true;
            }
            for (int i = 0; i < 4; i++) {
                int nr = cur[0];
                int nc = cur[1];
                while (true) {
                    nr += dx[i];
                    nc += dy[i];
                    if (nr < 0 || nc < 0 || nr >= 4 || nc >= 4) {
                        nr -= dx[i];
                        nc -= dy[i];
                        break;
                    }
                    if (board[nr][nc] != 0) break;
                }
                if (isVisited[nr][nc]) continue;
                if (board[nr][nc] == card) {
                    pair.move = cur[2] + 1;
                    pair.goal[0] = nr;
                    pair.goal[1] = nc;
                    break bfs;
                }
                queue.offer(new int[]{nr, nc, cur[2] + 1});
                isVisited[nr][nc] = true;
            }
        }
        return pair;
    }

    class Pair {
        int[] start = new int[2];
        int[] goal = new int[2];
        int move;
        int card;
    }
}
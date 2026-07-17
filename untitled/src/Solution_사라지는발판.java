class Solution_사라지는발판 {
    int[] dx = {1, -1, 0, 0};
    int[] dy = {0, 0, 1, -1};
    int n;
    int m;
    int MAX_VALUE = Integer.MAX_VALUE >> 1;

    public int solution(int[][] board, int[] aloc, int[] bloc) {
        n = board.length;
        m = board[0].length;
        int answer = MAX_VALUE - Math.abs(playGame(board, aloc, bloc, 0));
        return answer;
    }

    int playGame(int[][] board, int[] aloc, int[] bloc, int depth) {
        int[] loc;
        boolean flag = (depth % 2 == 0);
        if (flag) {
            loc = aloc;
        } else {
            loc = bloc;
        }
        if (board[loc[0]][loc[1]] == 0) {
            if (flag)
                return -MAX_VALUE + depth;
            else
                return MAX_VALUE - depth;
        }
        if (flag) {
            int max = -MAX_VALUE;
            for (int i = 0; i < 4; i++) {
                int nx = dx[i] + loc[0];
                int ny = dy[i] + loc[1];
                if (nx < 0 || ny < 0 || nx >= n || ny >= m || board[nx][ny] == 0) continue;
                board[loc[0]][loc[1]] = 0;
                loc[0] = nx;
                loc[1] = ny;
                int result = playGame(board, aloc, bloc, depth + 1);
                loc[0] -= dx[i];
                loc[1] -= dy[i];
                board[loc[0]][loc[1]] = 1;
                max = Math.max(max, result);
            }
            if (max == -MAX_VALUE) {
                return -MAX_VALUE + depth;
            }
            return max;
        } else {
            int min = MAX_VALUE;
            for (int i = 0; i < 4; i++) {
                int nx = dx[i] + loc[0];
                int ny = dy[i] + loc[1];
                if (nx < 0 || ny < 0 || nx >= n || ny >= m || board[nx][ny] == 0) continue;
                board[loc[0]][loc[1]] = 0;
                loc[0] = nx;
                loc[1] = ny;
                int result = playGame(board, aloc, bloc, depth + 1);
                loc[0] -= dx[i];
                loc[1] -= dy[i];
                board[loc[0]][loc[1]] = 1;
                min = Math.min(min, result);
            }
            if (min == MAX_VALUE) {
                return MAX_VALUE - depth;
            }
            return min;
        }
    }
}
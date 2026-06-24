import java.util.*;

class Solution_기차선로 {
    boolean[][][] used;
    int answer;
    int n;
    int m;
    int[] dx = {0, 1, 0, -1};
    int[] dy = {1, 0, -1, 0};

    public int solution(int[][] grid) {
        n = grid.length;
        m = grid[0].length;
        used = new boolean[n][m][4];
        int required = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] > 0) {
                    required++;
                    switch (grid[i][j]) {
                        case 1:
                            used[i][j][1] = used[i][j][3] = true;
                            break;
                        case 2:
                            used[i][j][0] = used[i][j][2] = true;
                            break;
                        case 3:
                            required++;
                            break;
                        case 4:
                            used[i][j][0] = used[i][j][1] = true;
                            break;
                        case 5:
                            used[i][j][1] = used[i][j][2] = true;
                            break;
                        case 6:
                            used[i][j][2] = used[i][j][3] = true;
                            break;
                        case 7:
                            used[i][j][3] = used[i][j][0] = true;
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        findTrack(0, 0, 0, required, grid);
        return answer;
    }

    //direction 0:right 1:down 2:left 3:up
    //used 0:가로 1:세로
    void findTrack(int x, int y, int direction, int required, int[][] grid) {
        if (x == n - 1 && y == m - 1 && required == 1) {
            answer++;
            return;
        }
        if (grid[x][y] == 0) {
            for (int i = 0; i < 4; i++) {
                if (direction == (i + 2) % 4) continue;
                int nx = x + dx[i];
                int ny = y + dy[i];
                if (nx < 0 || ny < 0 || nx >= n || ny >= m ||
                        grid[nx][ny] == -1 || used[nx][ny][(i + 2) % 4]) continue;
                if (direction == i) {
                    used[x][y][i] = true;
                    used[x][y][(i + 2) % 4] = true;
                    findTrack(nx, ny, i, required, grid);
                    used[x][y][i] = false;
                    used[x][y][(i + 2) % 4] = false;
                } else {
                    Arrays.fill(used[x][y], true);
                    findTrack(nx, ny, i, required, grid);
                    Arrays.fill(used[x][y], false);
                }
            }
        } else if (grid[x][y] == 3) {
            int nx = x + dx[direction];
            int ny = y + dy[direction];
            if (nx < 0 || ny < 0 || nx >= n || ny >= m ||
                    grid[nx][ny] == -1 || used[nx][ny][(direction + 2) % 4]) return;
            used[x][y][direction] = true;
            used[x][y][(direction + 2) % 4] = true;
            findTrack(nx, ny, direction, required - 1, grid);
            used[x][y][direction] = false;
            used[x][y][(direction + 2) % 4] = false;
        } else {
            used[x][y][(direction + 2) % 4] = true;
            for (int i = 0; i < 4; i++) {
                if (used[x][y][i]) continue;
                int nx = x + dx[i];
                int ny = y + dy[i];
                if (nx < 0 || ny < 0 || nx >= n || ny >= m ||
                        grid[nx][ny] == -1 || used[nx][ny][(i + 2) % 4]) continue;
                used[x][y][i] = true;
                findTrack(nx, ny, i, required - 1, grid);
                used[x][y][i] = false;
            }
            used[x][y][(direction + 2) % 4] = false;
        }
    }
}
import java.util.*;

class Solution_발전소회로복구 {
    final int MAX_VALUE = Integer.MAX_VALUE >> 1;
    int k;
    int[] prev;
    int[][] dp;
    int[][] time;

    public int solution(int h, String[] grid, int[][] panels, int[][] seqs) {
        int n = grid.length;
        int m = grid[0].length();
        k = panels.length;
        char[][] map = new char[n][m];
        Position elevator = new Position();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                map[i][j] = grid[i].charAt(j);
                if (map[i][j] == '@') {
                    elevator.x = i;
                    elevator.y = j;
                }
            }
        }
        time = findTime(elevator, map, panels);
        prev = findPrevBit(k, seqs);
        dp = new int[k][1 << k];
        for (int i = 0; i < k; i++) {
            Arrays.fill(dp[i], MAX_VALUE);
        }
        int answer = find(0, 0);
        return answer;
    }

    int find(int cur, int bit) {
        if (bit == (1 << k) - 1)
            return 0;
        if (dp[cur][bit] != MAX_VALUE)
            return dp[cur][bit];
        for (int i = 0; i < k; i++) {
            int check = 1 << i;
            if ((bit & check) != 0 || (bit & prev[i]) != prev[i]) continue;
            dp[cur][bit] = Math.min(dp[cur][bit], find(i, (check | bit)) + time[i][cur]);
        }
        return dp[cur][bit];
    }

    int[][] findTime(Position elevator, char[][] map, int[][] panels) {
        int k = panels.length;
        int[][] distance = new int[k][k];
        int[][] timeFromElevator = findTimeFromElevator(map, elevator);
        Position[] positions = new Position[k];
        for (int i = 0; i < k; i++) {
            positions[i] = new Position(panels[i][1] - 1, panels[i][2] - 1);
            distance[i][i] = 0;
        }
        for (int i = 0; i < k; i++) {
            for (int j = i + 1; j < k; j++) {
                if (panels[i][0] == panels[j][0]) {
                    distance[i][j] = distance[j][i] = findTimeP2P(map, positions[i], positions[j]);
                } else {
                    distance[i][j] = distance[j][i] =
                            timeFromElevator[positions[i].x][positions[i].y] + timeFromElevator[positions[j].x][positions[j].y] + Math.abs(panels[i][0] - panels[j][0]);
                }
            }
        }
        return distance;
    }

    int[][] findTimeFromElevator(char[][] map, Position elevator) {
        int n = map.length;
        int m = map[0].length;
        int[][] time = new int[n][m];
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};
        for (int i = 0; i < n; i++) {
            Arrays.fill(time[i], Integer.MAX_VALUE);
        }
        Queue<Position> queue = new ArrayDeque<>();
        queue.offer(elevator);
        time[elevator.x][elevator.y] = 0;
        while (!queue.isEmpty()) {
            Position p = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = p.x + dx[i];
                int ny = p.y + dy[i];
                if (nx < 0 || nx >= n || ny < 0 || ny >= m ||
                        time[nx][ny] != Integer.MAX_VALUE || map[nx][ny] == '#') continue;
                time[nx][ny] = time[p.x][p.y] + 1;
                queue.offer(new Position(nx, ny));
            }
        }
        return time;
    }

    int findTimeP2P(char[][] map, Position a, Position b) {
        int n = map.length;
        int m = map[0].length;
        int[][] time = new int[n][m];
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};
        for (int i = 0; i < n; i++) {
            Arrays.fill(time[i], MAX_VALUE);
        }
        Queue<Position> queue = new ArrayDeque<>();
        queue.offer(a);
        time[a.x][a.y] = 0;
        int result = -1;
        main:
        while (!queue.isEmpty()) {
            Position p = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = p.x + dx[i];
                int ny = p.y + dy[i];
                if (nx < 0 || nx >= n || ny < 0 || ny >= m ||
                        time[nx][ny] != MAX_VALUE || map[nx][ny] == '#') continue;
                if (nx == b.x && ny == b.y) {
                    result = time[p.x][p.y] + 1;
                    break main;
                }
                time[nx][ny] = time[p.x][p.y] + 1;
                queue.offer(new Position(nx, ny));
            }
        }
        return result;
    }

    int[] findPrevBit(int k, int[][] seqs) {
        int[] prev = new int[k];
        for (int[] seq : seqs) {
            int a = seq[0] - 1;
            int b = seq[1] - 1;
            prev[b] |= (1 << a);
        }
        return prev;
    }

    class Position {
        int x;
        int y;

        Position() {
        }

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
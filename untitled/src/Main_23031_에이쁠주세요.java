import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main_23031_에이쁠주세요 {

    static boolean[][] map;
    static boolean[][] safeZone;
    static int[][] zombieCount;
    static List<Zombie> zombies = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        map = new boolean[n][n];
        safeZone = new boolean[n][n];
        zombieCount = new int[n][n];
        String move = br.readLine();
        for (int i = 0; i < n; i++) {
            String str = br.readLine();
            for (int j = 0; j < n; j++) {
                char c = str.charAt(j);
                if (c == 'Z') {
                    Zombie zombie = new Zombie(i, j);
                    zombies.add(zombie);
                    zombieCount[i][j]++;
                } else if (c == 'S') {
                    map[i][j] = true;
                    safeZone[i][j] = true;
                }
            }
        }
        String result = isPossibleMove(n, move) ? "Phew..." : "Aaaaaah!";
        bw.write(result);
        bw.close();
        br.close();
    }

    static boolean isPossibleMove(int n, String move) {
        int m = move.length();
        Ahri ahri = new Ahri(n);
        for (int i = 0; i < m; i++) {
            char action = move.charAt(i);
            actionAhri(ahri, action);
            int x = ahri.x;
            int y = ahri.y;
            turnOnLight(n, x, y);
            if (encounterZombie(x, y)) {
                return false;
            }
            moveZombie(n);
            if (encounterZombie(x, y)) {
                return false;
            }
        }
        return true;
    }

    static void actionAhri(Ahri ahri, char action) {
        if (action == 'F') {
            ahri.goFront();
        } else if (action == 'L') {
            ahri.turnLeft();
        } else {
            ahri.turnRight();
        }
    }

    static void turnOnLight(int n, int x, int y) {
        if (map[x][y]) {
            for (int j = x - 1; j <= x + 1; j++) {
                if (j < 0) continue;
                if (j >= n) break;
                for (int k = y - 1; k <= y + 1; k++) {
                    if (k < 0) continue;
                    if (k >= n) break;
                    safeZone[j][k] = true;
                }
            }
        }
    }

    static void moveZombie(int n) {
        for (Zombie zombie : zombies) {
            int zx = zombie.x + zombie.dx;
            if (zx < 0 || zx >= n) {
                zombie.dx = -zombie.dx;
            } else {
                zombieCount[zombie.x][zombie.y]--;
                zombie.x = zx;
                zombieCount[zombie.x][zombie.y]++;
            }
        }
    }

    static boolean encounterZombie(int x, int y) {
        return !safeZone[x][y] && zombieCount[x][y] > 0;
    }

    static class Ahri {
        int x = 0;
        int y = 0;
        int[] dx = {1, 0, -1, 0};
        int[] dy = {0, 1, 0, -1};
        int d = 0;
        int n;

        Ahri(int n) {
            this.n = n;
        }

        void goFront() {
            int nx = x + dx[d];
            int ny = y + dy[d];
            if (nx >= 0 && nx < n && ny >= 0 && ny < n) {
                x = nx;
                y = ny;
            }
        }

        void turnLeft() {
            d = (d + 1) % 4;
        }

        void turnRight() {
            d = (d + 3) % 4;
        }
    }

    static class Zombie {
        int x;
        int y;
        int dx = 1;

        Zombie(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
import java.io.*;
import java.util.StringTokenizer;

public class Main_1460_진욱이의농장 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] farm = new int[n][n];
        Seed[] seeds = new Seed[m];
        int[][][] countSum = new int[8][n + 1][n + 1];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            seeds[i] = new Seed(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = m - 1; k >= 0; k--) {
                    Seed seed = seeds[k];
                    if (seed.x <= i && seed.y <= j && seed.x + seed.length > i && seed.y + seed.length > j) {
                        farm[i][j] = seed.type;
                        break;
                    }

                }
                for (int type = 0; type < 8; type++) {
                    countSum[type][i + 1][j + 1] = countSum[type][i + 1][j] + countSum[type][i][j + 1] - countSum[type][i][j];
                }
                countSum[farm[i][j]][i + 1][j + 1]++;
            }
        }
        int result = findMaxSquare(n, farm, countSum);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMaxSquare(int n, int[][] farm, int[][][] countSum) {
        int result = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (farm[i][j] == 0) continue;
                result = Math.max(result, binarySearch(i, j, farm, countSum, result));
            }
        }
        return result * result;
    }

    static int binarySearch(int x, int y, int[][] farm, int[][][] countSum, int max) {
        int n = farm.length;
        int left = max;
        int right = Math.min(n - x, n - y);
        int result = 1;
        while (left <= right) {
            int mid = (left + right) >> 1;
            int count = 0;
            int nx = x + mid;
            int ny = y + mid;
            if (countSum[0][nx][ny] - countSum[0][x][ny] - countSum[0][nx][y] + countSum[0][x][y] > 0) {
                count = -1;
            }
            if (count != -1) {
                for (int i = 1; i < 8; i++) {
                    int c = countSum[i][nx][ny] - countSum[i][x][ny] - countSum[i][nx][y] + countSum[i][x][y];
                    if (c > 0) {
                        count++;
                    }
                }
            }
            if (count > 0 && count < 3) {
                result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }

    static class Seed {
        int x;
        int y;
        int length;
        int type;

        Seed(int x, int y, int length, int type) {
            this.x = x;
            this.y = y;
            this.length = length;
            this.type = type;
        }
    }
}
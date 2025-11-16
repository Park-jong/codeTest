import java.io.*;
import java.util.StringTokenizer;

public class Main_34621_제설작업 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] map = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int[] row = new int[n];
        int[] col = new int[m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                row[i] += map[i][j];
                col[j] += map[i][j];
            }
        }
        int result = findMinPower(n, m, map, row, col);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMinPower(int n, int m, int[][] map, int[] row, int[] col) {
        int result = 0;
        while (true) {
            int minR = Integer.MAX_VALUE;
            int indexR = 0;
            for (int i = 0; i < n; i++) {
                if (row[i] <= 0) continue;
                if (row[i] < minR) {
                    minR = row[i];
                    indexR = i;
                }
            }
            int minC = Integer.MAX_VALUE;
            int indexC = 0;
            for (int i = 0; i < m; i++) {
                if (col[i] <= 0) continue;
                if (col[i] < minC) {
                    minC = col[i];
                    indexC = i;
                }
            }
            if (minR == Integer.MAX_VALUE && minC == Integer.MAX_VALUE) break;
            if (minR <= minC) {
                removeRow(map, row, col, indexR);
                result = Math.max(result, minR);
            } else {
                removeCol(map, row, col, indexC);
                result = Math.max(result, minC);
            }
        }
        return result;
    }

    static void removeRow(int[][] map, int[] row, int[] col, int index) {
        row[index] = 0;
        for (int i = 0; i < col.length; i++) {
            col[i] -= map[index][i];
            map[index][i] = 0;
        }
    }

    static void removeCol(int[][] map, int[] row, int[] col, int index) {
        col[index] = 0;
        for (int i = 0; i < row.length; i++) {
            row[i] -= map[i][index];
            map[i][index] = 0;
        }
    }
}
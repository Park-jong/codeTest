import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_9029_dp_정육면체 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int testCase = Integer.parseInt(br.readLine());
        Cube maxCube = new Cube(201, 201, 201);

        int[][][] cut = new int[maxCube.n1 + 1][maxCube.n2 + 1][maxCube.n3 + 1];
        final int MAX_VALUE = Integer.MAX_VALUE >> 2;
        for (int i = 0; i <= maxCube.n1; i++) {
            for (int j = 0; j <= maxCube.n2; j++) {
                Arrays.fill(cut[i][j], MAX_VALUE);
            }
        }
        for (int i = 0; i <= maxCube.n1; i++) {
            cut[i][i][i] = 0;
        }

        for (int n1 = 1; n1 <= maxCube.n1; n1++) {
            for (int n2 = n1; n2 <= maxCube.n2; n2++) {
                for (int n3 = n2; n3 <= maxCube.n3; n3++) {
                    cutFirst(cut, n1, n2, n3);
                    cutSecond(cut, n1, n2, n3);
                    cutThird(cut, n1, n2, n3);
                }
            }
        }
        for (int tc = 0; tc < testCase; tc++) {
            st = new StringTokenizer(br.readLine());
            int W = Integer.parseInt(st.nextToken());
            int L = Integer.parseInt(st.nextToken());
            int H = Integer.parseInt(st.nextToken());
            Cube cube = new Cube(W, L, H);
            sb.append(cut[cube.n1][cube.n2][cube.n3] + 1).append("\n");
        }

        bw.write(sb.toString());
        bw.close();
        br.close();
    }


    static void cutFirst(int[][][] cut, int n1, int n2, int n3) {
        for (int i = 1; i < n1; i++) {
            Cube cube1 = new Cube(i, n2, n3);
            Cube cube2 = new Cube(n1 - i, n2, n3);
            cut[n1][n2][n3] = Math.min(cut[n1][n2][n3], cut[cube1.n1][cube1.n2][cube1.n3] + cut[cube2.n1][cube2.n2][cube2.n3] + 1);
        }
    }

    static void cutSecond(int[][][] cut, int n1, int n2, int n3) {
        for (int i = 1; i < n2; i++) {
            Cube cube1 = new Cube(n1, i, n3);
            Cube cube2 = new Cube(n1, n2 - i, n3);
            cut[n1][n2][n3] = Math.min(cut[n1][n2][n3], cut[cube1.n1][cube1.n2][cube1.n3] + cut[cube2.n1][cube2.n2][cube2.n3] + 1);
        }
    }

    static void cutThird(int[][][] cut, int n1, int n2, int n3) {
        for (int i = 1; i < n3; i++) {
            Cube cube1 = new Cube(n1, n2, i);
            Cube cube2 = new Cube(n1, n2, n3 - i);
            cut[n1][n2][n3] = Math.min(cut[n1][n2][n3], cut[cube1.n1][cube1.n2][cube1.n3] + cut[cube2.n1][cube2.n2][cube2.n3] + 1);
        }
    }

    static class Cube {
        int n1;
        int n2;
        int n3;

        Cube(int a, int b, int c) {
            int small;
            int big;
            int middle;
            if (a <= b) {
                small = a;
                big = b;
            } else {
                small = b;
                big = a;
            }

            if (c <= small) {
                middle = small;
                small = c;
            } else if (c > big) {
                middle = big;
                big = c;
            } else {
                middle = c;
            }

            this.n1 = small;
            this.n2 = middle;
            this.n3 = big;
        }
    }
}
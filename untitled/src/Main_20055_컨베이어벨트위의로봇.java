import java.io.*;
import java.util.StringTokenizer;

public class Main_20055_컨베이어벨트위의로봇 {
    static int n;
    static int k;
    static int[] belts;
    static boolean[] using;
    static int beltStartIndex;
    static int count;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        belts = new int[2 * n];
        using = new boolean[2 * n];
        beltStartIndex = 0;
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < 2 * n; i++) {
            belts[i] = Integer.parseInt(st.nextToken());
        }
        int step = 0;
        while (count < k) {
            step++;
            rotateBelt();
            moveRobot();
            putRobot();
        }
        bw.write(String.valueOf(step));
        bw.close();
        br.close();
    }

    static void rotateBelt() {
        beltStartIndex--;
        if (beltStartIndex < 0) {
            beltStartIndex = 2 * n - 1;
        }
        using[getIndex(n - 1)] = false;
    }

    static void moveRobot() {
        for (int i = n - 1; i > 0; i--) {
            if (!using[getIndex(i)] && using[getIndex(i - 1)] && belts[getIndex(i)] > 0) {
                using[getIndex(i)] = true;
                using[getIndex(i - 1)] = false;
                if (--belts[getIndex(i)] == 0) count++;
            }
        }
        using[getIndex(n - 1)] = false;
    }

    static void putRobot() {
        if (belts[beltStartIndex] > 0) {
            if (--belts[beltStartIndex] == 0) count++;
            using[beltStartIndex] = true;
        }
    }

    static int getIndex(int index) {
        return (beltStartIndex + index) % (2 * n);
    }
}
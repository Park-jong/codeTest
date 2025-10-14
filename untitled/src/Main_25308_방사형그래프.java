import java.io.*;
import java.util.StringTokenizer;

public class Main_25308_방사형그래프 {

    static int[] array = new int[8];
    static int[] used = new int[8];
    static boolean[] isVisited = new boolean[8];
    static int count = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < 8; i++) {
            array[i] = Integer.parseInt(st.nextToken());
        }
        findCountOfNumber(0);
        bw.write(String.valueOf(count));
        bw.close();
        br.close();
    }

    static void findCountOfNumber(int depth) {
        if (depth == 8) {
            if (isPossible(used[6], used[7], used[0]) && isPossible(used[7], used[0], used[1])) {
                count++;
            }
            return;
        }
        for (int i = 0; i < 8; i++) {
            if (isVisited[i]) continue;
            used[depth] = array[i];
            isVisited[i] = true;
            if (depth < 2) {
                findCountOfNumber(depth + 1);
            } else {
                if (isPossible(used[depth - 2], used[depth - 1], used[depth])) {
                    findCountOfNumber(depth + 1);
                }
            }
            isVisited[i] = false;
        }
    }

    static boolean isPossible(int a, int b, int c) {
        double triangle = (double) (a * c);
        double triSum = (double) (a * b) * Math.sqrt(2) / 2 + (double) (b * c) * Math.sqrt(2) / 2;
        return triangle < triSum;
    }
}
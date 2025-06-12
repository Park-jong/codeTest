import java.io.*;
import java.util.StringTokenizer;

public class Main_3980_선발명단 {
    static int[][] s;
    static boolean[] isVisited;
    static int max;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        int c = Integer.parseInt(br.readLine());
        for (int testcase = 0; testcase < c; testcase++) {
            s = new int[11][11];
            isVisited = new boolean[11];
            max = 0;
            for (int i = 0; i < 11; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < 11; j++) {
                    s[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            setPosition(0, 0);
            sb.append(max).append("\n");
        }
        bw.write(sb.toString());
        bw.close();
        br.close();
    }

    static void setPosition(int depth, int sum) {
        if (depth == 11) {
            max = Math.max(max, sum);
            return;
        }
        for (int i = 0; i < 11; i++) {
            if (s[depth][i] == 0 || isVisited[i]) continue;
            isVisited[i] = true;
            setPosition(depth + 1, sum + s[depth][i]);
            isVisited[i] = false;
        }
    }
}
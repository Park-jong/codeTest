import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_23749_카드컨트롤 {

    static int max;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        max = n * 2;
        StringTokenizer st = new StringTokenizer(br.readLine());
        int card = 0;
        for (int i = 0; i < 2 * n; i++) {
            String str = st.nextToken();
            if (str.equals("O")) {
                card |= (1 << i);
            }
        }
        int result = findMinChange(n, card);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMinChange(int n, int card) {
        int m = 2 * n;
        boolean[] isUsed = new boolean[1 << m];
        if (isWin(n, card)) {
            return 0;
        }
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{card, 0});
        isUsed[card] = true;
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            for (int i = 1; i < m; i++) {
                int next = insert(m, cur[0], i);
                if (isUsed[next]) continue;
                if (isWin(n, next)) return cur[1] + 1;
                queue.offer(new int[]{next, cur[1] + 1});
                isUsed[next] = true;
            }
        }
        return -1;
    }

    static int insert(int m, int card, int a) {
        int insert = 0;
        for (int i = 0; i < m; i++) {
            int bit = ((1 << i) & card) >> i;
            if (i < a) {
                insert |= (bit << (i + 1));
            } else if (i == a) {
                insert |= bit;
            } else {
                insert |= (bit << i);
            }
        }
        return insert;
    }

    static boolean isWin(int n, int card) {
        int count = 0;
        int count2 = 0;
        for (int i = 0; i < 2 * n; i += 2) {
            if (((card & (1 << i)) != 0) && ((card & (1 << (i + 1))) == 0)) count++;
            if (((card & (1 << i)) == 0) && ((card & (1 << (i + 1))) != 0)) count2++;
        }
        return count > count2;
    }
}
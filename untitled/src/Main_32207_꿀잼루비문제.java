import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_32207_꿀잼루비문제 {
    static int n;
    static int m;
    static int k;
    static int length;
    static int searchLength;
    static int maxValue;
    static boolean[][] isImpossible;
    static Ruby[] rubies;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        length = n * m;
        searchLength = Math.min(length, 5 * k);
        isImpossible = new boolean[n][m];
        PriorityQueue<Ruby> pq = new PriorityQueue<>(searchLength, Comparator.comparingInt(a -> a.value));
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                int value = Integer.parseInt(st.nextToken());
                if (pq.size() < searchLength) {
                    pq.offer(new Ruby(i, j, value));
                } else if (pq.peek().value < value) {
                    pq.poll();
                    pq.offer(new Ruby(i, j, value));
                }
            }
        }
        rubies = pq.toArray(new Ruby[0]);
        int result = getMaxRuby();
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int getMaxRuby() {
        Arrays.sort(rubies, (a, b) -> Integer.compare(b.value, a.value));
        searchMaxValue(0, 0, 0);
        return maxValue;
    }

    static void searchMaxValue(int start, int depth, int sum) {
        if (depth >= k) {
            return;
        }
        for (int i = start; i < searchLength; i++) {
            Ruby ruby = rubies[i];
            int remainCount = k - depth;
            if (ruby.value * remainCount + sum <= maxValue) {
                return;
            }
            if (isImpossible[ruby.x][ruby.y]) {
                continue;
            }
            int nextIndex = i + 1;
            int nextDepth = depth + 1;
            int nextSum = sum + ruby.value;
            maxValue = Math.max(maxValue, nextSum);
            getRuby(ruby.x, ruby.y);
            searchMaxValue(nextIndex, nextDepth, nextSum);
            returnRuby(ruby.x, ruby.y);
        }
    }

    static void getRuby(int x, int y) {
        if (x > 0) {
            isImpossible[x - 1][y] = true;
        }
        if (y > 0) {
            isImpossible[x][y - 1] = true;
        }
        if (x < n - 1) {
            isImpossible[x + 1][y] = true;
        }
        if (y < m - 1) {
            isImpossible[x][y + 1] = true;
        }
    }

    static void returnRuby(int x, int y) {
        if (x > 0) {
            isImpossible[x - 1][y] = false;
        }
        if (y > 0) {
            isImpossible[x][y - 1] = false;
        }
        if (x < n - 1) {
            isImpossible[x + 1][y] = false;
        }
        if (y < m - 1) {
            isImpossible[x][y + 1] = false;
        }
    }

    static class Ruby {
        int x;
        int y;
        int value;

        Ruby(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }
    }
}
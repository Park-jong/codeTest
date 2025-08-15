import java.io.*;
import java.util.StringTokenizer;

public class Main_21315_카드섞기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] cards = new int[n];
        for (int i = 0; i < n; i++) {
            cards[i] = Integer.parseInt(st.nextToken());
        }
        int maxK = getMaxK(n);
        String result = getResult(n, maxK, cards);
        bw.write(result);
        bw.close();
        br.close();
    }

    static int getMaxK(int n) {
        int k = 1;
        while ((int) Math.pow(2, k + 1) < n) {
            k++;
        }
        return k;
    }

    static String getResult(int n, int maxK, int[] cards) {
        int[] base = new int[n];
        for (int i = 0; i < n; i++) {
            base[i] = i + 1;
        }
        for (int i = 1; i <= maxK; i++) {
            int[] oneShuffle = shuffle(n, i, base);
            for (int j = 1; j <= maxK; j++) {
                int[] twoShuffle = shuffle(n, j, oneShuffle);
                if (equals(cards, twoShuffle)) {
                    return i + " " + j;
                }
            }
        }
        return "";
    }

    static int[] shuffle(int n, int k, int[] cards) {
        int[] result = new int[n];
        int s = (int) Math.pow(2, k);
        int num = 0;
        for (int i = 0; i < n - s; i++) {
            result[s + i] = cards[num++];
        }
        int index = s;
        while (s > 0) {
            s = s >> 1;
            index -= s;
            for (int i = 0; i < s; i++) {
                result[index + i] = cards[num++];
            }
        }
        result[0] = cards[num];
        return result;
    }

    static boolean equals(int[] c1, int[] c2) {
        int n = c1.length;
        for (int i = 0; i < n; i++) {
            if (c1[i] != c2[i]) return false;
        }
        return true;
    }
}
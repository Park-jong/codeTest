import java.io.*;
import java.util.StringTokenizer;

public class Main_1062_가르침 {
    static int n;
    static int k;
    static int[] words;
    static int max;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        words = new int[n];
        for (int i = 0; i < n; i++) {
            String word = br.readLine();
            for (int j = 0; j < word.length(); j++) {
                words[i] |= (1 << (word.charAt(j) - 'a'));
            }
        }
        int bit = (1 << (0)) | (1 << ('c' - 'a')) | (1 << ('t' - 'a')) | (1 << ('n' - 'a')) | (1 << ('i' - 'a'));
        if (k >= 5) {
            findMaxSpelling(bit, 0, 5);
        }
        bw.write(String.valueOf(max));
        bw.close();
        br.close();
    }

    static void findMaxSpelling(int bit, int depth, int count) {
        while (((1 << depth) & bit) != 0) depth++;
        if (count == k) {
            int wordCount = 0;
            for (int i = 0; i < n; i++) {
                if ((bit | words[i]) == bit) wordCount++;
            }
            max = Math.max(wordCount, max);
            return;
        }
        for (int i = depth; i < 26; i++) {
            int newBit = bit | (1 << i);
            if (newBit == bit) continue;
            findMaxSpelling(newBit, i + 1, count + 1);
        }
    }
}
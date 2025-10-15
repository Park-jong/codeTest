import java.io.*;
import java.util.StringTokenizer;

public class Main_14610_좋은대회 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        Entry[] entries = new Entry[n];
        int[] problemCount = new int[m];
        for (int i = 0; i < n; i++) {
            entries[i] = new Entry(m);
            st = new StringTokenizer(br.readLine());
            entries[i].count = Integer.parseInt(st.nextToken());
            for (int j = 0; j < m; j++) {
                entries[i].result[j] = Integer.parseInt(st.nextToken());
                if (entries[i].result[j] == 1) {
                    entries[i].c++;
                    problemCount[j]++;
                }
            }
        }
        String result = isPossible(n, m, entries, problemCount) ? "YES" : "NO";
        bw.write(result);
        bw.close();
        br.close();
    }

    static boolean isPossible(int n, int m, Entry[] entries, int[] problemCount) {
        for (int i = 0; i < n; i++) {
            if (entries[i].count == 0 || entries[i].count == m) {
                return false;
            }
            for (int j = 0; j < m && entries[i].c < entries[i].count; j++) {
                if (entries[i].result[j] == -1 && problemCount[j] == 0) {
                    problemCount[j]++;
                    entries[i].c++;
                }
            }
        }
        for (int i = 0; i < m; i++) {
            if (problemCount[i] == 0) {
                return false;
            }
        }
        return true;
    }

    static class Entry {
        int count;
        int c;
        int[] result;

        Entry(int m) {
            result = new int[m];
        }
    }
}
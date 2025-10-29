import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main_7983_내일할거야 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        Work[] works = new Work[n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            works[i] = new Work(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        int result = findRestDays(works);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findRestDays(Work[] works) {
        Arrays.sort(works, Comparator.comparingInt(a -> -a.t));
        int day = Integer.MAX_VALUE;
        for (Work w : works) {
            day = Math.min(day, w.t);
            day -= w.d;
        }
        return day;
    }

    static class Work {
        int d;
        int t;

        Work(int d, int t) {
            this.d = d;
            this.t = t;
        }
    }
}
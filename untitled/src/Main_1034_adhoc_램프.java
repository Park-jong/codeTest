import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main_1034_adhoc_램프 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        Map<String, Row> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            String str = br.readLine();
            int zerocount = 0;
            for (int j = 0; j < m; j++) {
                if (str.charAt(j) == '0') zerocount++;
            }

            if (!map.containsKey(str)) {
                map.put(str, new Row(zerocount));
            }
            map.get(str).increase();
        }
        int k = Integer.parseInt(br.readLine());
        int result = 0;
        for (Row r : map.values()) {
            if (r.zero <= k && (k - r.zero) % 2 == 0 && result < r.count) {
                result = r.count;
            }
        }
        bw.write(String.valueOf(result));
        bw.flush();
        bw.close();
        br.close();
    }

    static class Row {
        int count;
        int zero;

        Row(int zero) {
            this.zero = zero;
        }

        void increase() {
            count++;
        }
    }
}
import java.io.*;
import java.util.StringTokenizer;

public class Main_11735_정산소 {

    static int n;
    static int removeRowCount;
    static long removeRowIndexSum;
    static int removeColCount;
    static long removeColIndexSum;
    static long sumOneToN;
    static boolean[] row;
    static boolean[] col;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        row = new boolean[n + 1];
        col = new boolean[n + 1];
        sumOneToN = sumOneToN(n);
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            String str = st.nextToken();
            int number = Integer.parseInt(st.nextToken());
            long result = sumRowOrCol(str.charAt(0), number);
            sb.append(result).append("\n");
        }
        bw.write(sb.toString());
        bw.close();
        br.close();
    }

    static long sumOneToN(int n) {
        return (((long) n + 1) * n) >> 1;
    }

    static long sumRowOrCol(char type, int line) {
        if (type == 'R') {
            return sumRow(line);
        } else {
            return sumCol(line);
        }
    }

    static long sumRow(int line) {
        if (row[line]) {
            return 0;
        }
        row[line] = true;
        removeRowCount++;
        removeRowIndexSum += line;
        return ((long) n - removeColCount) * line + sumOneToN - removeColIndexSum;
    }

    static long sumCol(int line) {
        if (col[line]) {
            return 0;
        }
        col[line] = true;
        removeColCount++;
        removeColIndexSum += line;
        return ((long) n - removeRowCount) * line + sumOneToN - removeRowIndexSum;
    }
}
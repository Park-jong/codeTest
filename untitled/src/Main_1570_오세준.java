import java.io.*;
import java.util.StringTokenizer;

public class Main_1570_오세준 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int sx = Integer.parseInt(st.nextToken());
        int sy = Integer.parseInt(st.nextToken());
        int mx = Integer.parseInt(st.nextToken());
        int my = Integer.parseInt(st.nextToken());
        String result = getCommand(n, sx, sy, mx, my);
        bw.write(result);
        bw.close();
        br.close();
    }

    static String getCommand(int n, int sx, int sy, int mx, int my) {
        int subX = mx - sx;
        int subY = my - sy;
        if (subY < 0 || subX < 0) {
            return "-1";
        }
        if (subY == 0) {
            return "R".repeat(n);
        }
        if (subX == 0) {
            if ((subY - 1) / n == 0) {
                return "U".repeat(subY) + "R".repeat(n - subY);
            }
            return "U".repeat(n);
        }
        int r = n - 1;
        int u = 1;
        String result = "";
        for (; u < n; u++, r--) {
            int repeatX = subX / r;
            int repeatY = subY / u;
            int remainX = subX % r;
            int remainY = subY % u;
            if (repeatX == repeatY) {
                String str = "R".repeat(remainX) + "U".repeat(remainY) + "R".repeat(r - remainX) + "U".repeat(u - remainY);
                result = getResult(result, str);
            } else if (repeatX + 1 == repeatY && remainY == 0) {
                String str = "R".repeat(remainX) + "U".repeat(u) + "R".repeat(r - remainX);
                result = getResult(result, str);
            } else if (repeatY + 1 == repeatX && remainX == 0) {
                String str = "R".repeat(r) + "U".repeat(u);
                result = getResult(result, str);
            }
        }
        if (result.isEmpty()) {
            return "-1";
        }
        return result;
    }

    static String getResult(String result, String compare) {
        if (result.isEmpty()) {
            return compare;
        } else if (compare.compareTo(result) < 0) {
            return compare;
        }
        return result;
    }
}
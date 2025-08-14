import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_23748_방문판매 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        Product[] products = new Product[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            products[i] = new Product(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        Result result = getMinCount(n, x, y, products);
        String output = result.count > 0 ? result.count + "\n" + result.number : "-1";
        bw.write(output);
        bw.close();
        br.close();
    }

    static Result getMinCount(int n, int x, int y, Product[] products) {
        Result result = new Result();
        int[][][] dp = new int[n + 1][x + 1][y + 1];
        int MAX_VALUE = Integer.MAX_VALUE >> 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= x; j++) {
                Arrays.fill(dp[i][j], MAX_VALUE);
            }
        }
        dp[0][0][0] = 0;
        for (int num = 1; num <= n; num++) {
            Product product = products[num - 1];
            for (int i = 0; i <= x; i++) {
                int px = i - product.x;
                if (px < 0) px = 0;
                for (int j = 0; j <= y; j++) {
                    int py = j - product.y;
                    if (py < 0) py = 0;
                    if (i == x && j == y && dp[num - 1][i][j] > dp[num - 1][px][py] + 1) {
                        result.count = dp[num - 1][px][py] + 1;
                        result.number = num;
                    }
                    dp[num][i][j] = Math.min(dp[num - 1][i][j], dp[num - 1][px][py] + 1);
                }
            }
        }
        return result;
    }

    static class Product {
        int x;
        int y;

        Product(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Result {
        int count;
        int number;
    }
}
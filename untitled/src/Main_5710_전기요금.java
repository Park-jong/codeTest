import java.io.*;
import java.util.StringTokenizer;

public class Main_5710_전기요금 {
    static int[] cost;
    static int[] watt;
    static int[] max;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        cost = new int[4];
        cost[0] = 2;
        cost[1] = 3;
        cost[2] = 5;
        cost[3] = 7;
        watt = new int[5];
        watt[1] = 100;
        watt[2] = 10000;
        watt[3] = 1000000;
        watt[4] = Integer.MAX_VALUE;
        max = new int[5];
        max[1] = cost[0] * watt[1];
        max[2] = cost[1] * (watt[2] - watt[1]) + max[1];
        max[3] = cost[2] * (watt[3] - watt[2]) + max[2];
        max[4] = Integer.MAX_VALUE;
        StringBuilder sb = new StringBuilder();
        int test = getCost(10123);
        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int sum = Integer.parseInt(st.nextToken());
            int sub = Integer.parseInt(st.nextToken());
            if (sum == 0 && sub == 0) {
                break;
            }
            sb.append(findResult(sum, sub)).append("\n");
        }
        bw.write(sb.toString());
        bw.close();
        br.close();
    }

    static int findResult(int a, int b) {
        int sumWatt = getWatt(a);
        return binarySearch(sumWatt, b);
    }

    static int getWatt(int c) {
        int w = 0;
        for (int i = 0; i < watt.length; i++) {
            if (max[i] <= c) continue;
            w = ((c - max[i - 1]) / cost[i - 1]) + watt[i - 1];
            break;
        }
        return w;
    }

    static int binarySearch(int sumWatt, int b) {
        int left = 0;
        int right = sumWatt >> 1;
        int result = 0;
        while (left <= right) {
            int mid = (left + right) >> 1;
            int other = sumWatt - mid;
            int midCost = getCost(mid);
            int otherCost = getCost(other);
            if (otherCost - midCost == b) {
                result = midCost;
                break;
            } else if (otherCost - midCost > b) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }

    static int getCost(int w) {
        int result = 0;
        for (int i = 0; i < watt.length; i++) {
            if (watt[i] <= w) continue;
            result = max[i - 1] + (w - watt[i - 1]) * cost[i - 1];
            break;
        }
        return result;
    }
}
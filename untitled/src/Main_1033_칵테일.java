import java.io.*;
import java.util.StringTokenizer;

public class Main_1033_칵테일 {
    static int n;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        Drink[] drinks = new Drink[n - 1];
        for (int i = 0; i < n - 1; i++) {
            drinks[i] = new Drink(n);
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());
            int q = Integer.parseInt(st.nextToken());
            int big = Math.max(p, q);
            int small = Math.min(p, q);
            int gcd = GCD(big, small);
            drinks[i].setRatios(a, b, p / gcd, q / gcd);
        }

        int[] totalRatio = drinks[0].ratios;
        boolean[] used = new boolean[n - 1];
        used[0] = true;
        int count = 1;
        while (count < n - 1) {
            for (int i = 1; i < n - 1; i++) {
                if (used[i]) continue;
                for (int j = 0; j < n; j++) {
                    if (totalRatio[j] != 0 && drinks[i].ratios[j] != 0) {
                        int num1 = totalRatio[j];
                        int num2 = drinks[i].ratios[j];
                        int lcm = LCM(num1, num2);
                        int mul1 = lcm / num1;
                        int mul2 = lcm / num2;
                        multiply(totalRatio, mul1);
                        multiply(drinks[i].ratios, mul2);
                        add(totalRatio, drinks[i].ratios);
                        used[i] = true;
                        count++;
                        break;
                    }
                }
            }
        }
        String answer = makeAnswer(totalRatio);
        bw.write(answer);
        bw.close();
        br.close();
    }

    static int GCD(int big, int small) {
        if (big % small == 0) {
            return small;
        }
        return GCD(small, big % small);
    }

    static int LCM(int a, int b) {
        int max = Math.max(a, b);
        int min = Math.min(a, b);
        int gcd = GCD(max, min);
        return max * min / gcd;
    }

    static void multiply(int[] array, int mul) {
        for (int i = 0; i < n; i++) {
            array[i] *= mul;
        }
    }

    static void add(int[] total, int[] sub) {
        for (int i = 0; i < n; i++) {
            if (total[i] == 0 || sub[i] == 0) {
                total[i] = total[i] + sub[i];
            }
        }
    }

    static String makeAnswer(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(array[i]).append(" ");
        }
        return sb.toString();
    }

    static class Drink {
        int[] ratios;

        Drink(int n) {
            ratios = new int[n];
        }

        void setRatios(int a, int b, int p, int q) {
            ratios[a] = p;
            ratios[b] = q;
        }
    }
}
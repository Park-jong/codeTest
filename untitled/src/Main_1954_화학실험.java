import java.io.*;
import java.util.StringTokenizer;

public class Main_1954_화학실험 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        T[] t = new T[n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            t[i] = new T(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        int m = Integer.parseInt(br.readLine());
        int result = findAmount(n, t, m);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findAmount(int n, T[] t, int m) {
        x:
        for (int i = 1; i <= m; i++) {
            int gas = gas(t[0], i);
            int sum = i;
            for (int j = 1; j < n; j++) {
                if (!isPossible(t[j], gas)) continue x;
                sum += getX(t[j], gas);
            }
            if (sum == m) return gas;
            else if (sum > m) break;
        }
        return 0;
    }

    static boolean isPossible(T t, int gas) {
        return (gas - t.b) % t.a == 0;
    }

    static int getX(T t, int gas) {
        return (gas - t.b) / t.a;
    }

    static int gas(T t, int x) {
        return t.a * x + t.b;
    }

    static class T {
        int a;
        int b;

        T(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }
}
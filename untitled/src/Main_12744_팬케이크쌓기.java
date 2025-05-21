import java.io.*;
import java.util.*;

public class Main_12744_팬케이크쌓기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());
        Set<String> visited = new HashSet<>();
        int[] num = new int[n];
        boolean[] state = new boolean[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            num[i] = Integer.parseInt(st.nextToken()) - 1;
            state[i] = st.nextToken().equals("+");
        }
        Cake cake = new Cake(num, state, 0);
        visited.add(cake.str);
        Queue<Cake> cakes = new ArrayDeque<>();
        cakes.offer(cake);
        int result = 0;
        while (!cakes.isEmpty()) {
            Cake current = cakes.poll();
            if (check(current)) {
                result = current.step;
                break;
            }
            for (int i = 0; i < n; i++) {
                Cake newCake = makeCake(current, i, n);
                if(!visited.contains(newCake.str)){
                    visited.add(newCake.str);
                    cakes.offer(newCake);
                }
            }
        }
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static boolean check(Cake cake) {
        int n = cake.num.length;
        for (int i = 0; i < n; i++) {
            if (i != cake.num[i] || !cake.state[i]) return false;
        }
        return true;
    }

    static Cake makeCake(Cake cake, int turn, int n) {
        int[] newNum = new int[n];
        boolean[] newState = new boolean[n];
        for (int i = 0; i <= turn; i++) {
            newNum[i] = cake.num[turn - i];
            newState[i] = !cake.state[turn - i];
        }
        for (int i = turn + 1; i < n; i++) {
            newNum[i] = cake.num[i];
            newState[i] = cake.state[i];
        }
        return new Cake(newNum, newState, cake.step + 1);
    }

    static class Cake {
        String str;
        int step;
        int[] num;
        boolean[] state;

        Cake(int[] num, boolean[] state, int step) {
            this.num = num;
            this.state = state;
            this.step = step;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < num.length; i++) {
                sb.append(num[i]).append(state[i] ? "+" : "-");
            }
            str = sb.toString();
        }
    }
}
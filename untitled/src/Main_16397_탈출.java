import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_16397_탈출 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        int g = Integer.parseInt(st.nextToken());

        Queue<Number> q = new ArrayDeque<>();
        q.offer(new Number(n, 0));
        boolean[] visited = new boolean[100000];
        int result = -1;
        while (!q.isEmpty()) {
            Number current = q.poll();
            if(current.num == g){
                result = current.stage;
                break;
            }
            if (visited[current.num]) continue;
            visited[current.num] = true;

            int typeOne = plusOne(current.num);
            if (typeOne < 100000 && !visited[typeOne] && current.stage < t) {
                q.offer(new Number(typeOne, current.stage + 1));
            }
            int typeTwo = typeTwo(current.num);
            if (typeTwo < 100000 && !visited[typeTwo] && current.stage < t) {
                q.offer(new Number(typeTwo, current.stage + 1));
            }
        }

        bw.write(result < 0 ? "ANG" : String.valueOf(result));
        bw.close();
        br.close();
    }

    static int plusOne(int number) {
        return number + 1;
    }

    static int typeTwo(int number) {
        int n = number * 2;
        if (n == 0) return n;
        if(n >= 100000) return 100000;
        int n2 = n;
        int count = 0;
        while (true) {
            int next = n2 / 10;
            if (next == 0) {
                break;
            }
            n2 = next;
            count++;
        }
        int sub = 1;
        for (int i = 0; i < count; i++) {
            sub = sub * 10;
        }
        return n - sub;
    }

    static class Number {
        int num;
        int stage;

        Number(int num, int stage) {
            this.num = num;
            this.stage = stage;
        }
    }
}
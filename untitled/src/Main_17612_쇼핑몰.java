import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_17612_쇼핑몰 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        Counter[] counters = new Counter[k];
        for (int i = 0; i < k; i++) {
            counters[i] = new Counter(i, 0);
        }
        Member[] members = new Member[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            members[i] = new Member(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        long result = findOrder(n, k, counters, members);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static long findOrder(int n, int k, Counter[] counters, Member[] members) {
        long result = 0;
        int index = 0;
        PriorityQueue<Counter> wait = new PriorityQueue<>((a, b) -> {
            if (a.time == b.time) return Integer.compare(a.num, b.num);
            return Integer.compare(a.time, b.time);
        });
        PriorityQueue<Counter> count = new PriorityQueue<>((a, b) -> {
            if (a.time == b.time) return Integer.compare(b.num, a.num);
            return Integer.compare(a.time, b.time);
        });
        for (Counter c : counters) {
            wait.offer(c);
        }
        for (Member member : members) {
            if (wait.isEmpty()) {
                Counter out = count.poll();
                wait.offer(out);
                result += (long) ++index * out.member;
                while (!count.isEmpty() && count.peek().time == out.time) {
                    result += count(++index, wait, count);
                }
            }
            Counter next = wait.poll();
            next.time += member.w;
            next.member = member.id;
            count.offer(next);
        }
        while (!count.isEmpty()) {
            result += count(++index, wait, count);
        }
        return result;
    }

    static long count(int index, PriorityQueue<Counter> wait, PriorityQueue<Counter> count) {
        Counter out = count.poll();
        wait.offer(out);
        return (long) index * out.member;
    }

    static class Counter {
        int num;
        int time;
        int member;

        Counter(int num, int time) {
            this.num = num;
            this.time = time;
        }
    }

    static class Member {
        int id;
        int w;

        Member(int id, int w) {
            this.id = id;
            this.w = w;
        }
    }
}
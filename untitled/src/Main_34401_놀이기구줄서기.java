import java.io.*;
import java.util.*;

public class Main_34401_놀이기구줄서기 {

    static int size;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int p = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        Group[] groups = new Group[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            groups[i] = new Group(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        long result = findWaitTime(n, p, k, groups);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static long findWaitTime(int n, int p, int k, Group[] groups) {
        Arrays.sort(groups, Comparator.comparingInt(a -> a.t));
        Queue<Group>[] waitGroups = new Queue[k + 1];
        for (int i = 1; i <= k; i++) {
            waitGroups[i] = new ArrayDeque<>();
        }
        long time = 0;
        long result = 0;
        for (int i = 0; i < n; ) {
            if (groups[i].t <= time) {
                waitGroups[groups[i].a].offer(groups[i]);
                size++;
                i++;
            } else {
                result += ride(k, time, waitGroups);
                time += p;
            }
        }
        while (size > 0) {
            result += ride(k, time, waitGroups);
            time += p;
        }
        return result;
    }

    static long ride(int k, long time, Queue<Group>[] waitGroups) {
        int remain = k;
        long result = 0;
        while (true) {
            int ride = 0;
            int min = Integer.MAX_VALUE;
            for (int i = 1; i <= remain; i++) {
                if (waitGroups[i].isEmpty()) continue;
                int t = waitGroups[i].peek().t;
                if (t < min) {
                    ride = i;
                    min = t;
                }
            }
            if (ride == 0) break;
            waitGroups[ride].poll();
            remain -= ride;
            result += (time - min);
            size--;
        }
        return result;
    }

    static class Group {
        int t;
        int a;

        Group(int t, int a) {
            this.t = t;
            this.a = a;
        }
    }
}
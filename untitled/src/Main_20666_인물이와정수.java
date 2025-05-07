import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_20666_인물이와정수 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        long[] level = new long[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            level[i] = Long.parseLong(st.nextToken());
        }
        int p = Integer.parseInt(br.readLine());
        Item[] items = new Item[n];
        for (int i = 0; i < n; i++) {
            items[i] = new Item();
        }
        for (int i = 0; i < p; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int t = Integer.parseInt(st.nextToken());
            level[b] += t;
            items[a].next.add(new int[]{b, t});
        }

        PriorityQueue<Monster> pq = new PriorityQueue<>((a, b) -> Long.compare(a.level, b.level));
        for (int i = 0; i < n; i++) {
            pq.offer(new Monster(i, level[i]));
        }
        int loop = 0;
        long max = 0;
        boolean[] isVisited = new boolean[n];
        while(loop < m){
            Monster monster = pq.poll();
            int current = monster.index;
            long currentLevel = monster.level;
            if(isVisited[current]) continue;
            isVisited[current] = true;
            max = Math.max(max, currentLevel);
            for(int[] next : items[current].next){
                int minusIndex = next[0];
                int minusValue = next[1];
                level[minusIndex] -= minusValue;
                if(isVisited[minusIndex]) continue;
                pq.offer(new Monster(minusIndex, level[minusIndex]));
            }
            loop++;
        }

        bw.write(String.valueOf(max));
        bw.flush();
        bw.close();
        br.close();
    }

    static class Item {
        List<int[]> next = new ArrayList<>();
    }

    static class Monster{
        int index;
        long level;
        Monster(int index, long level){
            this.index = index;
            this.level = level;
        }
    }
}
import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_23559_밥 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        PriorityQueue<Menu> pq = new PriorityQueue<>((a, b) -> Integer.compare(b.sub, a.sub));
        int like = 0;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            Menu menu = new Menu(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            pq.offer(menu);
            like += menu.food1000;
        }
        int cost = 1000 * n;
        while (!pq.isEmpty()){
            Menu menu = pq.poll();
            if(menu.sub <= 0){
                break;
            }
            if(cost + 4000 > x){
                break;
            }
            like += menu.sub;
            cost += 4000;
        }
        bw.write(String.valueOf(like));
        bw.close();
        br.close();
    }

    static class Menu {
        int food1000;
        int food5000;
        int sub;

        Menu(int food5000, int food1000) {
            this.food5000 = food5000;
            this.food1000 = food1000;
            sub = food5000 - food1000;
        }
    }
}
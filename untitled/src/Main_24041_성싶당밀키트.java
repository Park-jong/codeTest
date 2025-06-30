import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_24041_성싶당밀키트 {

    static List<Ingredient> ingredients0 = new ArrayList<>();
    static List<Ingredient> ingredients1 = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int g = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            long speed = Long.parseLong(st.nextToken());
            long limit = Long.parseLong(st.nextToken());
            int importance = Integer.parseInt(st.nextToken());
            Ingredient ingredient = new Ingredient(speed, limit, importance);
            if (importance == 1) {
                ingredients1.add(ingredient);
            } else {
                ingredients0.add(ingredient);
            }
        }
        long result = binarySearchDay(g, k);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static long binarySearchDay(int g, int k) {
        long left = 0;
        long right = Integer.MAX_VALUE;
        long result = 0;
        while (left <= right) {
            long mid = (left + right) >> 1;
            long sum0 = sumIngredients0(mid, g);
            long sum1 = sumIngredients1(mid, g, k);
            if (sum1 + sum0 > g) {
                right = mid - 1;
            } else {
                result = mid;
                left = mid + 1;
            }

        }
        return result;
    }

    static long sumIngredients0(long day, int g) {
        long sum = 0;
        for (Ingredient ingredient : ingredients0) {
            sum += Math.max(1, day - ingredient.limit) * ingredient.speed;
            if (sum > g) return g + 1;
        }
        return sum;
    }

    static long sumIngredients1(long day, int g, int k) {
        int size = ingredients1.size();
        if (size <= k) {
            return 0;
        }
        PriorityQueue<Long> pq = new PriorityQueue<>((a, b) -> Long.compare(a, b));
        for (Ingredient ingredient : ingredients1) {
            pq.offer(Math.max(1, day - ingredient.limit) * ingredient.speed);
        }
        long sum = 0;
        int require = size - k;
        for (int i = 0; i < require; i++) {
            sum += pq.poll();
            if (sum > g) return g + 1;
        }
        return sum;
    }

    static class Ingredient {
        long speed;
        long limit;
        int importance;

        Ingredient(long speed, long limit, int importance) {
            this.speed = speed;
            this.limit = limit;
            this.importance = importance;
        }
    }
}
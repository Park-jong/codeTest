import java.io.*;
import java.util.*;

public class Main_2132_나무위의벌레 {

    static Fruit[] fruits;
    static boolean[] isVisited;
    static int n;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        fruits = new Fruit[n + 1];
        isVisited = new boolean[n + 1];
        for (int i = 1; i <= n; i++) {
            fruits[i] = new Fruit(i, Integer.parseInt(st.nextToken()));
        }
        for (int i = 1; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            fruits[a].next.add(fruits[b]);
            fruits[b].next.add(fruits[a]);
        }
        Result result = getMaxCount();
        String output = result.count + " " + result.index;
        bw.write(output);
        bw.close();
        br.close();
    }

    static Result getMaxCount() {
        Result result = new Result();
        result.count = -1;
        for (int i = 1; i <= n; i++) {
            isVisited[i] = true;
            int count = checkCount(fruits[i], 0);
            checkAndChangeResult(result, i, count);
            isVisited[i] = false;
        }
        return result;
    }

    static void checkAndChangeResult(Result result, int index, int count) {
        if (count > result.count) {
            result.count = count;
            result.index = index;
        }
    }

    static int checkCount(Fruit fruit, int prev) {
        if(fruit.maxValue.containsKey(prev)){
            return fruit.maxValue.get(prev);
        }
        int count = fruit.count;
        int index = fruit.index;
        for (Fruit next : fruit.next) {
            if (isNotVisitedFruit(next)) {
                isVisited[fruit.index] = true;
                count = Math.max(checkCount(next, index) + fruit.count, count);
                isVisited[fruit.index] = false;
            }
        }
        fruit.maxValue.put(prev, count);
        return count;
    }

    static boolean isNotVisitedFruit(Fruit fruit) {
        return !isVisited[fruit.index];
    }

    static class Fruit {
        List<Fruit> next = new ArrayList<>();
        int index;
        int count;
        Map<Integer, Integer> maxValue = new HashMap<>();

        Fruit(int index, int count) {
            this.index = index;
            this.count = count;
        }
    }

    static class Result {
        int count;
        int index;
    }
}
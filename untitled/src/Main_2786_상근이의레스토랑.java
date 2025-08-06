import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_2786_상근이의레스토랑 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        Food[] firstFood = new Food[n];
        Food[] otherFood = new Food[n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            long first = Long.parseLong(st.nextToken());
            long other = Long.parseLong(st.nextToken());
            Food food = new Food(i, first, other);
            firstFood[i] = food;
            otherFood[i] = food;
        }
        String result = getMinCost(firstFood, otherFood, n);
        bw.write(result);
        bw.close();
        br.close();
    }

    static String getMinCost(Food[] firstFood, Food[] otherFood, int n) {
        Arrays.sort(firstFood, (a, b) -> {
            if (a.first == b.first) {
                return Long.compare(b.other, a.other);
            }
            return Long.compare(a.first, b.first);
        });
        Arrays.sort(otherFood, (a, b) -> {
            if (a.other == b.other) {
                return Long.compare(b.first, a.first);
            }
            return Long.compare(a.other, b.other);
        });
        StringBuilder result = new StringBuilder();
        long sum = firstFood[0].first;
        result.append(sum).append("\n");
        boolean[] visited = new boolean[n];
        int firstFoodIndex = 0;
        int otherFoodIndex = 0;
        int nextFirstIndex = 1;
        for (int i = 0; i < n - 1; i++) {
            Food first = firstFood[firstFoodIndex];
            Food other = otherFood[otherFoodIndex];
            while (nextFirstIndex < n && visited[firstFood[nextFirstIndex].index]) {
                nextFirstIndex++;
            }
            if (first.index == other.index) {
                long compare1 = nextFirstIndex < n ? sum - first.first + first.other + firstFood[nextFirstIndex].first : Long.MAX_VALUE;
                long compare2 = otherFoodIndex + 1 < n ? sum + otherFood[otherFoodIndex + 1].other : Long.MAX_VALUE;
                if (compare1 <= compare2) {
                    sum = compare1;
                    firstFoodIndex = nextFirstIndex;
                    nextFirstIndex++;
                    visited[firstFood[firstFoodIndex].index] = true;
                } else {
                    sum = compare2;
                    otherFoodIndex++;
                    visited[otherFood[otherFoodIndex].index] = true;
                }
                otherFoodIndex++;
            } else {
                long compare1 = nextFirstIndex < n ? sum - first.first + first.other + firstFood[nextFirstIndex].first : Long.MAX_VALUE;
                long compare2 = otherFoodIndex < n ? sum + other.other : Long.MAX_VALUE;
                if (compare1 <= compare2) {
                    sum = compare1;
                    firstFoodIndex = nextFirstIndex;
                    nextFirstIndex++;
                    visited[firstFood[firstFoodIndex].index] = true;
                } else {
                    sum = compare2;
                    visited[otherFood[otherFoodIndex].index] = true;
                    otherFoodIndex++;
                }
            }
            result.append(sum).append("\n");
        }
        return result.toString();
    }

    static class Food {
        int index;
        long first;
        long other;

        Food(int index, long first, long other) {
            this.index = index;
            this.first = first;
            this.other = other;
        }
    }
}
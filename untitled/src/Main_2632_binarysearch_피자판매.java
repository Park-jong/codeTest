import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_2632_binarysearch_피자판매 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int pizzaSize = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        int[] pizzaA = new int[m];
        int[] pizzaB = new int[n];
        for (int i = 0; i < m; i++) {
            pizzaA[i] = Integer.parseInt(br.readLine());
        }
        for (int i = 0; i < n; i++) {
            pizzaB[i] = Integer.parseInt(br.readLine());
        }
        int[] sumPizzaA = sumPizzaPiece(pizzaA);
        int[] sumPizzaB = sumPizzaPiece(pizzaB);
        int result = combPizza(sumPizzaA, sumPizzaB, pizzaSize);

        bw.write(String.valueOf(result));
        bw.flush();
        bw.close();
        br.close();
    }

    static int[] sumPizzaPiece(int[] pizza) {
        int n = pizza.length;
        int[] sumPizza = new int[n * (n - 1) + 1];
        int index = 0;
        for (int i = 0; i < n; i++) {
            int sum = pizza[i];
            sumPizza[index++] = sum;
            for (int j = (i + 1) % n; j != (i + n - 1) % n; j = (j + 1) % n) {
                sum += pizza[j];
                sumPizza[index++] = sum;
            }
            if (i == 0) {
                sum += pizza[n - 1];
                sumPizza[index++] = sum;
            }
        }
        Arrays.sort(sumPizza);
        return sumPizza;
    }

    static int combPizza(int[] sumPizzaA, int[] sumPizzaB, int pizzaSize) {
        int m = sumPizzaA.length;
        int n = sumPizzaB.length;
        int count = 0;
        count += countSumPizza(sumPizzaA, pizzaSize);
        count += countSumPizza(sumPizzaB, pizzaSize);
        if (m <= n) {
            return count + countCombination(sumPizzaA, sumPizzaB, pizzaSize);
        }
        return count + countCombination(sumPizzaB, sumPizzaA, pizzaSize);
    }


    static int countCombination(int[] sumPizzaA, int[] sumPizzaB, int pizzaSize) {
        int n = sumPizzaA.length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            int current = sumPizzaA[i];
            if (current > pizzaSize) break;
            count += countSumPizza(sumPizzaB, pizzaSize - current);
        }
        return count;
    }

    static int countSumPizza(int[] sumPizza, int pizzaSize) {
        int count = 0;
        int left = binarySearchLeft(sumPizza, pizzaSize);
        int right = binarySearchRight(sumPizza, pizzaSize);
        if (left >= 0) {
            count = right - left + 1;
        }
        return count;
    }

    static int binarySearchLeft(int[] arr, int findNumber) {
        int n = arr.length;
        int left = 0;
        int right = n - 1;
        int index = -1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (findNumber <= arr[mid]) {
                right = mid - 1;
                if (findNumber == arr[mid]) index = mid;
            } else {
                left = mid + 1;
            }
        }
        return index;
    }

    static int binarySearchRight(int[] arr, int findNumber) {
        int n = arr.length;
        int left = 0;
        int right = n - 1;
        int index = -1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (findNumber < arr[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
                if (findNumber == arr[mid]) index = mid;
            }
        }
        return index;
    }
}
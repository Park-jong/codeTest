import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_2258_정육점 {

    static int n;
    static int m;
    static Meat[] meats;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        meats = new Meat[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            meats[i] = new Meat(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        sortMeats();
        int result = buyMeat();
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int buyMeat() {
        int weightSum = 0;
        int result = -1;
        int prevPrice = -1;
        int sameWeightSum = 0;
        int samePriceSum = 0;
        for (int i = 0; i < n; i++) {
            if (meats[i].price == prevPrice) {
                samePriceSum += meats[i].price;
                sameWeightSum += meats[i].weight;
            } else {
                weightSum += sameWeightSum;
                samePriceSum = meats[i].price;
                sameWeightSum = meats[i].weight;
            }
            if (weightSum + sameWeightSum >= m) {
                if (result == -1) {
                    result = samePriceSum;
                } else {
                    result = Math.min(result, samePriceSum);
                }
            }
            prevPrice = meats[i].price;
        }
        return result;
    }

    static void sortMeats() {
        Arrays.sort(meats, (a, b) -> {
            if (a.price == b.price) return Integer.compare(b.weight, a.weight);
            return Integer.compare(a.price, b.price);
        });
    }

    static class Meat {
        int weight;
        int price;

        Meat(int weight, int price) {
            this.weight = weight;
            this.price = price;
        }
    }
}
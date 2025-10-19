import java.io.*;
import java.util.StringTokenizer;

public class Main_19942_다이어트 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int mp = Integer.parseInt(st.nextToken());
        int mf = Integer.parseInt(st.nextToken());
        int ms = Integer.parseInt(st.nextToken());
        int mv = Integer.parseInt(st.nextToken());
        Ingredient[] ingredients = new Ingredient[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            ingredients[i] = new Ingredient(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        String result = findMinCost(n, mp, mf, ms, mv, ingredients);
        bw.write(result);
        bw.close();
        br.close();
    }

    static String findMinCost(int n, int mp, int mf, int ms, int mv, Ingredient[] ingredients) {
        int m = 1 << n;
        int minCost = Integer.MAX_VALUE;
        StringBuilder comb = new StringBuilder();
        for (int i = 1; i < m; i++) {
            Ingredient ingredient = new Ingredient();
            for (int j = 1, index = 0; j < m; j = j << 1, index++) {
                if ((i & j) > 0) {
                    ingredient.add(ingredients[index]);
                }
            }
            if (check(ingredient, mp, mf, ms, mv)) {
                if (ingredient.c < minCost) {
                    minCost = ingredient.c;
                    comb = new StringBuilder();
                    for (int j = 1, index = 0; j < m; j = j << 1, index++) {
                        if ((i & j) > 0) {
                            comb.append(index + 1).append(" ");
                        }
                    }
                } else if (ingredient.c == minCost) {
                    StringBuilder compare = new StringBuilder();
                    for (int j = 1, index = 0; j < m; j = j << 1, index++) {
                        if ((i & j) > 0) {
                            compare.append(index + 1).append(" ");
                        }
                    }
                    if (compare.toString().compareTo(comb.toString()) < 0) {
                        comb = compare;
                    }
                }
            }
        }
        return minCost != Integer.MAX_VALUE ? minCost + "\n" + comb : "-1";
    }

    static boolean check(Ingredient ingredient, int mp, int mf, int ms, int mv) {
        return ingredient.p >= mp && ingredient.f >= mf && ingredient.s >= ms && ingredient.v >= mv;
    }

    static class Ingredient {
        int p;
        int f;
        int s;
        int v;
        int c;

        Ingredient() {
        }

        Ingredient(int p, int f, int s, int v, int c) {
            this.p = p;
            this.f = f;
            this.s = s;
            this.v = v;
            this.c = c;
        }

        void add(Ingredient ingredient) {
            this.p += ingredient.p;
            this.f += ingredient.f;
            this.s += ingredient.s;
            this.v += ingredient.v;
            this.c += ingredient.c;
        }
    }
}
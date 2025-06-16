import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_16235_나무재테크 {

    static int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
    static int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
    static int n;
    static int[][] A;
    static int[][] death;
    static int[][] ground;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        A = new int[n + 1][n + 1];
        death = new int[n + 1][n + 1];
        ground = new int[n + 1][n + 1];
        Area[][] areas = new Area[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= n; j++) {
                A[i][j] = Integer.parseInt(st.nextToken());
                ground[i][j] = 5;
                areas[i][j] = new Area();
            }
        }

        int[][] trees = new int[m][3];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            trees[i][0] = Integer.parseInt(st.nextToken());
            trees[i][1] = Integer.parseInt(st.nextToken());
            trees[i][2] = Integer.parseInt(st.nextToken());
        }
        int result = growTree(trees, areas, m, k);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int growTree(int[][] trees, Area[][] areas, int m, int k) {
        Arrays.sort(trees, (a, b) -> Integer.compare(b[2], a[2]));
        for (int i = 0; i < m; i++) {
            int x = trees[i][0];
            int y = trees[i][1];
            int age = trees[i][2];
            Area area = areas[x][y];
            add(area, age);
        }
        int[][] young = new int[n + 1][n + 1];
        for (int tc = 0; tc < k; tc++) {
            surviveOrDead(areas, young);
        }
        return countTotal(areas);
    }

    static void add(Area area, int age) {
        if (area.start == null || area.start.age != age) {
            Tree prev = area.start;
            area.start = new Tree(age);
            area.start.next = prev;
        } else {
            area.start.add();
        }
    }

    static void surviveOrDead(Area[][] areas, int[][] young){
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                Area area = areas[i][j];
                Tree current = area.start;
                area.totalCount = 0;
                while (current != null) {
                    takeNutrientAndDeadOrGrow(current, i, j);
                    makeYoungTree(current, young, i, j);
                    area.totalCount += current.count;
                    removeNextIfNull(current);
                    current = current.next;
                }
            }
        }
        supply(areas, young);
    }

    static void takeNutrientAndDeadOrGrow(Tree tree, int x, int y){
        int mul = tree.age * tree.count;
        if (mul > ground[x][y]) {
            int count = tree.count;
            tree.count = ground[x][y] / tree.age;
            mul = tree.age * tree.count;
            death[x][y] += (count - tree.count) * (tree.age >> 1);
        }
        ground[x][y] -= mul;
        tree.age++;
    }

    static void makeYoungTree(Tree tree, int[][] young, int x, int y){
        if (tree.count > 0 && tree.age % 5 == 0) {
            for (int d = 0; d < 8; d++) {
                int newX = x + dx[d];
                int newY = y + dy[d];
                if (newX > 0 && newY > 0 && newX <= n && newY <= n) {
                    young[newX][newY] += tree.count;
                }
            }
        }
    }

    static void removeNextIfNull(Tree tree){
        if (tree.next != null && tree.next.count == 0) {
            tree.next = null;
        }
    }

    static void supply(Area[][] areas, int[][] young){
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (young[i][j] > 0) {
                    areas[i][j].add(young[i][j]);
                    areas[i][j].totalCount += young[i][j];
                    young[i][j] = 0;
                }
                ground[i][j] += death[i][j] + A[i][j];
                death[i][j] = 0;
            }
        }
    }

    static int countTotal(Area[][] areas){
        int total = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                total += areas[i][j].totalCount;
            }
        }
        return total;
    }

    static class Tree {
        int count;
        int age;
        Tree next;

        Tree(int age) {
            this.age = age;
            this.count = 1;
        }

        void add() {
            this.count++;
        }
    }

    static class Area {
        Tree start;
        int totalCount;

        void add(int count) {
            Tree temp = start;
            start = new Tree(1);
            start.count = count;
            start.next = temp;
        }
    }
}
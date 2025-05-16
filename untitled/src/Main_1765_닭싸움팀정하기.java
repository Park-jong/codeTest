import java.io.*;
import java.util.*;

public class Main_1765_닭싸움팀정하기 {

    static int[] set;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());

        set = makeSet(n);
        Enemy[] enemies = makeEnemies(n);

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            String relation = st.nextToken();
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            if (relation.equals("F")) {
                union(a, b);
            } else {
                enemies[a].list.add(b);
                enemies[b].list.add(a);
            }
        }

        for (int i = 0; i < n; i++) {
            List<Integer> list = enemies[i].list;
            int size = list.size();
            if (size > 1) {
                for (int j = 1; j < size; j++) {
                    union(list.get(0), list.get(j));
                }
            }
        }
        int result = groupCount();

        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int[] makeSet(int n) {
        int[] set = new int[n];
        for (int i = 0; i < n; i++) {
            set[i] = i;
        }
        return set;
    }

    static Enemy[] makeEnemies(int n) {
        Enemy[] enemies = new Enemy[n];
        for (int i = 0; i < n; i++) {
            enemies[i] = new Enemy();
        }
        return enemies;
    }

    static int findSet(int a) {
        if (a == set[a]) return a;
        return set[a] = findSet(set[a]);
    }

    static void union(int a, int b) {
        int rootA = findSet(a);
        int rootB = findSet(b);
        if (rootB == rootA) return;
        set[rootB] = rootA;
    }

    static int groupCount(){
        Set<Integer> s = new HashSet<>();
        for(int i : set){
            s.add(findSet(i));
        }
        return s.size();
    }

    static class Enemy {
        List<Integer> list = new ArrayList<>();
    }
}
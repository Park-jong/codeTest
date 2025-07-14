import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_13032_ps테러리스트 {

    static Bomb[] bombs;
    static int n;

    public static void main(String[] args) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        bombs = makeBombsArray();
        int result = minNotExplodedBombCount();
        bw.write(String.valueOf(result));
        bw.close();
    }

    static Bomb[] makeBombsArray() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        Bomb[] bombs = new Bomb[n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int power = Integer.parseInt(st.nextToken());
            bombs[i] = new Bomb(x, power);
        }
        Arrays.sort(bombs, (a, b) -> Integer.compare(a.position, b.position));
        br.close();
        return bombs;
    }

    static int minNotExplodedBombCount(){
        int[] chainExplodedCount = checkChainExplodedBomb();
        return checkInstantBombCase(chainExplodedCount);
    }

    static int[] checkChainExplodedBomb() {
        int[] explodeBombCount = new int[n];
        for (int i = 0; i < n; i++) {
            int explodeArea = bombs[i].position - bombs[i].power;
            int next = findNextExplodeIndex(explodeArea, i);
            if (next < 0) {
                explodeBombCount[i] = 1;
            } else {
                explodeBombCount[i] = explodeBombCount[next] + 1;
            }
        }
        return explodeBombCount;
    }

    static int findNextExplodeIndex(int num, int index) {
        int mostLeft = bombs[0].position;
        if (num <= mostLeft) {
            return -1;
        }
        int left = 0;
        int right = index;
        int result = 0;
        while (left <= right) {
            int mid = (left + right) >> 1;
            if (bombs[mid].position < num) {
                result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }

    static int checkInstantBombCase(int[] explodeBombCount){
        int min = Integer.MAX_VALUE;
        for(int i = n - 1; i >= 0 && n - i - 1 < min; i--){
            int notExplodeCount = n - explodeBombCount[i];
            min = Math.min(notExplodeCount, min);
        }
        return min;
    }

    static class Bomb {
        int position;
        int power;

        Bomb(int position, int power) {
            this.position = position;
            this.power = power;
        }
    }
}
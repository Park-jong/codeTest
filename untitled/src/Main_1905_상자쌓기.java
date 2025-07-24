import java.io.*;
import java.util.StringTokenizer;

public class Main_1905_상자쌓기 {
    static int lx;
    static int ly;
    static int n;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        lx = Integer.parseInt(st.nextToken());
        ly = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());
        Box[] boxes = new Box[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int lx = Integer.parseInt(st.nextToken());
            int ly = Integer.parseInt(st.nextToken());
            int lz = Integer.parseInt(st.nextToken());
            int px = Integer.parseInt(st.nextToken());
            int py = Integer.parseInt(st.nextToken());
            boxes[i] = new Box(lx, ly, lz, px, py);
        }
        int result = findMaxHeight(boxes, n);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMaxHeight(Box[] boxes, int n) {
        int maxHeight = 0;
        for (int i = 0; i < n; i++) {
            Box box = boxes[i];
            int prevMaxHeight = 0;
            for(int j = 0; j < i; j++){
                Box compare = boxes[j];
                int height = compareBoxes(compare, box);
                prevMaxHeight = Math.max(prevMaxHeight, height);
            }
            box.height += prevMaxHeight;
            maxHeight = Math.max(maxHeight, box.height);
        }
        return maxHeight;
    }

    static int compareBoxes(Box compare, Box box) {
        if (compare.e < box.w || compare.w > box.e || compare.n < box.s || compare.s > box.n) {
            return 0;
        }
        return compare.height;
    }

    static class Box {
        int e;
        int w;
        int n;
        int s;
        int height;

        Box(int lx, int ly, int lz, int px, int py) {
            w = px;
            s = py;
            e = w + lx - 1;
            n = s + ly - 1;
            height = lz;
        }
    }
}
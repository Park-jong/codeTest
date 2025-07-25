import java.io.*;
import java.util.StringTokenizer;

public class Main_17354_DVD {

    static int w;
    static int h;
    static int W;
    static int H;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        w = Integer.parseInt(st.nextToken());
        h = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken()) - w;
        H = Integer.parseInt(st.nextToken()) - h;
        st = new StringTokenizer(br.readLine());
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        int moveX = Integer.parseInt(st.nextToken());
        int moveY = Integer.parseInt(st.nextToken());
        Position position = new Position(x, y, moveX, moveY);
        long result = findCorner(position);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static long findCorner(Position position){
        long time = moveEdge(position);
        int startX = position.x;
        int startY = position.y;
        while((position.x != W && position.x != 0) || (position.y != 0 && position.y != H)){
            time += moveEdge(position);
            if(position.x == startX && position.y == startY){
                time = -1;
                break;
            }
        }
        return time;
    }

    static int moveEdge(Position position) {
        int x = position.moveX > 0 ? W - position.x : position.x;
        int y = position.moveY > 0 ? H - position.y : position.y;
        int move = Math.min(x, y);
        position.x += position.moveX * move;
        position.y += position.moveY * move;
        if(move == x) {
            position.moveX = -position.moveX;
        }
        if(move == y) {
            position.moveY = -position.moveY;
        }
        return move;
    }

    static class Position {
        int x;
        int y;
        int moveX;
        int moveY;

        Position(int x, int y, int moveX, int moveY) {
            this.x = x;
            this.y = y;
            this.moveX = moveX;
            this.moveY = moveY;
        }
    }
}
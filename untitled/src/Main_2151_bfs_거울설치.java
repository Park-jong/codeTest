import java.io.*;
import java.util.PriorityQueue;
import java.util.Queue;

public class Main_2151_bfs_거울설치 {
    static int n;
    static char[][] room;
    static boolean isVisited[][][];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        n = Integer.parseInt(br.readLine());
        room = new char[n][n];
        isVisited = new boolean[n][n][4];
        int startX = 0;
        int startY = 0;
        int endX = 0;
        int endY = 0;
        boolean start = false;
        for (int i = 0; i < n; i++) {
            String str = br.readLine();
            for (int j = 0; j < n; j++) {
                room[i][j] = str.charAt(j);
                if (room[i][j] == '#') {
                    if (start) {
                        endX = i;
                        endY = j;
                    } else {
                        startX = i;
                        startY = j;
                        start = true;
                    }
                }
            }
        }

        PriorityQueue<Position> queue = new PriorityQueue<>((a, b) -> Integer.compare(a.useMirror, b.useMirror));
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};
        for (int i = 0; i < 4; i++) {
            int x = startX + dx[i];
            int y = startY + dy[i];
            if (x >= 0 && x < n && y >= 0 && y < n && room[x][y] != '*') {
                queue.offer(new Position(x, y, dx[i], dy[i], 0));
                isVisited[startX][startY][i] = true;
            }
        }

        int result = 0;
        while (!queue.isEmpty()) {
            Position current = queue.poll();
            if (isVisited[current.x][current.y][current.moveType]) continue;
            isVisited[current.x][current.y][current.moveType] = true;
            if (endX == current.x && endY == current.y) {
                result = current.useMirror;
                break;
            }
            addQueue(queue, current);
            if (room[current.x][current.y] == '!') {
                useMirror(queue, current);
            }
        }
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static void addQueue(Queue<Position> queue, Position position) {
        int x = position.x + position.moveX;
        int y = position.y + position.moveY;
        if (x >= 0 && x < n && y >= 0 && y < n && room[x][y] != '*' && !isVisited[x][y][position.moveType]) {
            queue.offer(new Position(x, y, position.moveX, position.moveY, position.useMirror));
        }
    }

    static void useMirror(Queue<Position> queue, Position position) {
        addQueue(queue, position.x, position.y, position.moveY, position.moveX, position.useMirror + 1);
        addQueue(queue, position.x, position.y, -position.moveY, -position.moveX, position.useMirror + 1);
    }

    static void addQueue(Queue<Position> queue, int prevX, int prevY, int moveX, int moveY, int useMirror) {
        int x = prevX + moveX;
        int y = prevY + moveY;
        if (x >= 0 && x < n && y >= 0 && y < n && room[x][y] != '*' && !isVisited[x][y][moveType(moveX, moveY)]) {
            queue.offer(new Position(x, y, moveX, moveY, useMirror));
        }
    }

    static int moveType(int moveX, int moveY) {
        if (moveX == 1) {
            return 0;
        } else if (moveX == -1) {
            return 1;
        } else if (moveY == 1) {
            return 2;
        } else {
            return 3;
        }
    }

    static class Position {
        int x;
        int y;
        int useMirror;
        int moveX;
        int moveY;
        int moveType;

        Position(int x, int y, int moveX, int moveY, int useMirror) {
            this.x = x;
            this.y = y;
            this.moveX = moveX;
            this.moveY = moveY;
            this.useMirror = useMirror;
            if (moveX == 1) {
                this.moveType = 0;
            } else if (moveX == -1) {
                this.moveType = 1;
            } else if (moveY == 1) {
                this.moveType = 2;
            } else {
                this.moveType = 3;
            }
        }
    }
}
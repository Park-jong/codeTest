import java.util.*;
class Solution_블록이동하기 {
    int n;
    int[][] board;
    boolean[][][] isVisited;
    int[] dx = {1, -1, 0, 0};
    int[] dy = {0, 0, 1, -1};

    public int solution(int[][] board) {
        n = board.length;
        this.board = board;
        isVisited = new boolean[n][n][2];
        int answer = count();
        return answer;
    }

    int count(){
        isVisited[0][0][0] = true;
        Queue<Position> queue = new ArrayDeque<>();
        queue.offer(new Position(0, 0, 0, 0));
        int count = -1;
        while(!queue.isEmpty()){
            Position cur = queue.poll();
            if(cur.type == 0){
                if(move0(cur, queue)) {
                    count = cur.count + 1;
                    break;
                }
                rotateUp(cur, queue);
                if(rotateDown(cur, queue)) {
                    count = cur.count + 1;
                    break;
                }
            } else {
                if(move1(cur, queue)) {
                    count = cur.count + 1;
                    break;
                }
                rotateLeft(cur, queue);
                if(rotateRight(cur, queue)){
                    count = cur.count + 1;
                    break;
                }
            }
        }
        return count;
    }

    boolean move0(Position p, Queue<Position> queue){
        for(int i = 0; i < 4; i++){
            int nx = p.x + dx[i];
            int ny = p.y + dy[i];
            if(nx >= n || nx < 0 || ny >= n - 1 || ny < 0 || isVisited[nx][ny][0]) continue;
            if(board[nx][ny] == 1 || board[nx][ny + 1] == 1) continue;
            queue.offer(new Position(nx, ny, 0, p.count + 1));
            isVisited[nx][ny][0] = true;
            if(nx == n - 1 && ny == n - 2) return true;
        }
        return false;
    }

    boolean move1(Position p, Queue<Position> queue){
        for(int i = 0; i < 4; i++){
            int nx = p.x + dx[i];
            int ny = p.y + dy[i];
            if(nx >= n - 1 || nx < 0 || ny >= n || ny < 0 || isVisited[nx][ny][1]) continue;
            if(board[nx][ny] == 1 || board[nx + 1][ny] == 1) continue;
            queue.offer(new Position(nx, ny, 1, p.count + 1));
            isVisited[nx][ny][1] = true;
            if(nx == n - 2 && ny == n - 1) return true;
        }
        return false;
    }

    boolean rotateDown(Position p, Queue<Position> queue){
        if(p.x >= n - 1 || board[p.x + 1][p.y] == 1 || board[p.x + 1][p.y + 1] == 1) return false;
        if(!isVisited[p.x][p.y][1]) {
            queue.offer(new Position(p.x, p.y, 1, p.count + 1));
            isVisited[p.x][p.y][1] = true;
        }
        if(!isVisited[p.x][p.y + 1][1]){
            queue.offer(new Position(p.x, p.y + 1, 1, p.count + 1));
            isVisited[p.x][p.y + 1][1] = true;
        }
        if(p.x + 1 == n - 1 && p.y + 1 == n - 1) return true;
        return false;
    }

    boolean rotateUp(Position p, Queue<Position> queue){
        if(p.x <= 0 || board[p.x - 1][p.y] == 1 || board[p.x - 1][p.y + 1] == 1) return false;
        if(!isVisited[p.x - 1][p.y][1]) {
            queue.offer(new Position(p.x - 1, p.y, 1, p.count + 1));
            isVisited[p.x - 1][p.y][1] = true;
        }
        if(!isVisited[p.x - 1][p.y + 1][1]){
            queue.offer(new Position(p.x - 1, p.y + 1, 1, p.count + 1));
            isVisited[p.x - 1][p.y + 1][1] = true;
        }
        return false;
    }

    boolean rotateLeft(Position p, Queue<Position> queue){
        if(p.y <= 0 || board[p.x][p.y - 1] == 1 || board[p.x + 1][p.y - 1] == 1) return false;
        if(!isVisited[p.x][p.y - 1][0]) {
            queue.offer(new Position(p.x, p.y - 1, 0, p.count + 1));
            isVisited[p.x][p.y - 1][0] = true;
        }
        if(!isVisited[p.x + 1][p.y - 1][0]){
            queue.offer(new Position(p.x + 1, p.y - 1, 0, p.count + 1));
            isVisited[p.x + 1][p.y - 1][0] = true;
        }
        return false;
    }

    boolean rotateRight(Position p, Queue<Position> queue){
        if(p.y >= n - 1 || board[p.x][p.y + 1] == 1 || board[p.x + 1][p.y + 1] == 1) return false;
        if(!isVisited[p.x][p.y][0]) {
            queue.offer(new Position(p.x, p.y, 0, p.count + 1));
            isVisited[p.x][p.y][0] = true;
        }
        if(!isVisited[p.x + 1][p.y][0]){
            queue.offer(new Position(p.x + 1, p.y, 0, p.count + 1));
            isVisited[p.x + 1][p.y][0] = true;
        }
        if(p.x + 1 == n - 1 && p.y + 1 == n - 1) return true;
        return false;
    }

    class Position {
        int x;
        int y;
        int type;
        int count;

        Position(int x, int y, int type, int count){
            this.x = x;
            this.y = y;
            this.type = type;
            this.count = count;
        }
    }
}
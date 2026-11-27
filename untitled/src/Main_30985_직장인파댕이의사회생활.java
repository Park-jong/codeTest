import java.io.*;
import java.util.*;

public class Main_30985_직장인파댕이의사회생활 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        Room[] rooms = new Room[n + 1];
        for (int i = 1; i <= n; i++) {
            rooms[i] = new Room(i);
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            long c = Long.parseLong(st.nextToken());
            rooms[u].addNext(rooms[v], c);
            rooms[v].addNext(rooms[u], c);
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            long e = Long.parseLong(st.nextToken());
            rooms[i].setElevator(e);
        }
        bw.write(String.valueOf(findMinTime(rooms, n, k)));
        bw.close();
        br.close();
    }

    static long findMinTime(Room[] rooms, int n, int k) {
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingLong(a -> a.time));
        queue.offer(new Node(1, 0, 0));
        boolean[][] isVisited = new boolean[2][n + 1];
        long result = -1;
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            Room room = rooms[current.index];
            int e = current.e;
            if (isVisited[e][room.index]) continue;
            isVisited[e][room.index] = true;
            if (e == 1 && room.index == n) {
                result = current.time;
                break;
            }
            for (int i = 0; i < room.path; i++) {
                Room next = room.next.get(i);
                long t = current.time + room.time.get(i);
                if (isVisited[e][next.index]) continue;
                queue.offer(new Node(next.index, e, t));
            }
            if (e == 0 && room.elevator != -1 && !isVisited[1][room.index]) {
                queue.offer(new Node(room.index, 1, current.time + room.elevator * (k - 1)));
            }
        }
        return result;
    }

    static class Node {
        int index;
        int e;
        long time;

        Node(int index, int e, long time) {
            this.index = index;
            this.e = e;
            this.time = time;
        }
    }

    static class Room {
        int index;
        int path;
        List<Room> next = new ArrayList<>();
        List<Long> time = new ArrayList<>();
        long elevator;

        Room(int index) {
            this.index = index;
        }

        void setElevator(long elevator) {
            this.elevator = elevator;
        }

        void addNext(Room room, long time) {
            this.next.add(room);
            this.time.add(time);
            path++;
        }
    }
}
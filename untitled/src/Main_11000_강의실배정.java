import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_11000_강의실배정 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        Room[] room = new Room[n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            room[i] = new Room(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        Arrays.sort(room, Comparator.comparingInt(a -> a.start));
        int result = findRoomCount(room);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findRoomCount(Room[] rooms) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int result = 0;
        for (Room room : rooms) {
            while (!pq.isEmpty() && pq.peek() <= room.start) {
                pq.poll();
            }
            pq.offer(room.end);
            result = Math.max(result, pq.size());
        }
        return result;
    }

    static class Room {
        int start;
        int end;

        Room(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
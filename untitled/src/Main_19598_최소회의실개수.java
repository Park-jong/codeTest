import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_19598_최소회의실개수 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        PriorityQueue<Meeting> meetings = new PriorityQueue<>(Comparator.comparingInt(a->a.start));
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            Meeting meeting = new Meeting(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            meetings.offer(meeting);
        }
        int result = requireRooms(meetings, n);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int requireRooms(PriorityQueue<Meeting> meetings, int n) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        int maxRoomCount = 0;
        for (int i = 0; i < n; i++) {
            int usingRoomCount = usingRooms(priorityQueue, meetings.poll());
            maxRoomCount = Math.max(usingRoomCount, maxRoomCount);
        }
        return maxRoomCount;
    }

    static int usingRooms(PriorityQueue<Integer> priorityQueue, Meeting meeting) {
        while (!priorityQueue.isEmpty() && priorityQueue.peek() <= meeting.start) {
            priorityQueue.poll();
        }
        priorityQueue.offer(meeting.end);
        return priorityQueue.size();
    }

    static class Meeting {
        int start;
        int end;

        Meeting(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
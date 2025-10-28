import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_16169_수행시간 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        Computer[] computers = new Computer[n + 1];
        for (int i = 1; i <= n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            computers[i] = new Computer(i, Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        int result = findRunTime(n, computers);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findRunTime(int n, Computer[] computers) {
        int maxLevel = 0;
        for (int i = 1; i <= n; i++) {
            maxLevel = Math.max(computers[i].level, maxLevel);
        }
        List<Computer>[] levels = new List[maxLevel + 1];
        for (int i = 1; i <= maxLevel; i++) {
            levels[i] = new ArrayList<>();
        }
        for (int i = 1; i <= n; i++) {
            levels[computers[i].level].add(computers[i]);
        }
        for (int i = 1; i < maxLevel; i++) {
            for (Computer send : levels[i]) {
                for (Computer receive : levels[i + 1]) {
                    receive.start = Math.max(send.start + send.time + (receive.index - send.index) * (receive.index - send.index), receive.start);
                }
            }
        }
        int result = 0;
        for (Computer c : levels[maxLevel]) {
            result = Math.max(result, c.start + c.time);
        }
        return result;
    }

    static class Computer {
        int index;
        int level;
        int time;
        int start;

        Computer(int index, int level, int time) {
            this.index = index;
            this.level = level;
            this.time = time;
        }
    }
}
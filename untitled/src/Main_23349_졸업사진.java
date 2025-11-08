import java.io.*;
import java.util.*;

public class Main_23349_졸업사진 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        Submit[] submits = new Submit[n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            submits[i] = new Submit(st.nextToken(), st.nextToken(), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        String result = hotPlace(submits);
        bw.write(result);
        bw.close();
        br.close();
    }

    static String hotPlace(Submit[] submits) {
        Set<String> name = new HashSet<>();
        Map<String, int[]> place = new TreeMap<>();
        for (Submit submit : submits) {
            if (name.contains(submit.name)) continue;
            name.add(submit.name);
            if (!place.containsKey(submit.place)) {
                place.put(submit.place, new int[50002]);
            }
            int[] array = place.get(submit.place);
            for (int i = submit.start; i < submit.end; i++) {
                array[i]++;
            }
        }
        String result = null;
        int count = 0;
        int start = 0;
        int end = 0;
        boolean check = false;
        for (String key : place.keySet()) {
            int[] array = place.get(key);
            for (int i = 1; i <= 50001; i++) {
                if (array[i] > count) {
                    check = true;
                    start = i;
                    count = array[i];
                    result = key;
                }
                if (check && array[i] != count) {
                    end = i;
                    check = false;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(result).append(" ").append(start).append(" ").append(end);
        return sb.toString();
    }

    static class Submit {
        String name;
        String place;
        int start;
        int end;

        Submit(String name, String place, int start, int end) {
            this.name = name;
            this.place = place;
            this.start = start;
            this.end = end;
        }
    }
}
import java.io.*;
import java.util.*;

public class Main_3691_컴퓨터조립 {

    static final int MAX_VALUE = Integer.MAX_VALUE >> 1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < t; i++) {
            sb.append(solve(br)).append("\n");
        }
        bw.write(sb.toString());
        bw.close();
        br.close();
    }

    static int solve(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int maxQuality = 0;
        Map<String, List<Part>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            String type = st.nextToken();
            String name = st.nextToken();
            int price = Integer.parseInt(st.nextToken());
            int quality = Integer.parseInt(st.nextToken());
            maxQuality = Math.max(quality, maxQuality);
            if (!map.containsKey(type)) {
                map.put(type, new ArrayList<>());
            }
            map.get(type).add(new Part(price, quality));
        }
        for (List<Part> list : map.values()) {
            list.sort(Comparator.comparing(a -> a.price));
        }
        return binarySearch(b, maxQuality, map);
    }

    static int binarySearch(int b, int maxQuality, Map<String, List<Part>> map) {
        int left = 0;
        int right = maxQuality;
        int result = 0;
        while (left <= right) {
            int mid = (left + right) >> 1;
            int sum = 0;
            for (List<Part> list : map.values()) {
                sum += minPrice(mid, list);
                if (sum > b) break;
            }
            if (sum > b) {
                right = mid - 1;
            } else {
                result = mid;
                left = mid + 1;
            }
        }
        return result;
    }

    static int minPrice(int q, List<Part> list) {
        for (Part part : list) {
            if (part.quality >= q) {
                return part.price;
            }
        }
        return MAX_VALUE;
    }

    static class Part {
        int price;
        int quality;

        Part(int price, int quality) {
            this.price = price;
            this.quality = quality;
        }
    }
}
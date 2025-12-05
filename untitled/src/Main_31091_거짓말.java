import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main_31091_거짓말 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] k = new int[n];
        for (int i = 0; i < n; i++) {
            k[i] = Integer.parseInt(st.nextToken());
        }
        String result = findNumberOfCases(n, k);
        bw.write(result);
        bw.close();
        br.close();
    }

    static String findNumberOfCases(int n, int[] k) {
        List<Integer> positive = new ArrayList<>();
        List<Integer> negative = new ArrayList<>();
        for (int i : k) {
            if (i > 0) positive.add(i);
            else negative.add(-i);
        }
        Collections.sort(positive);
        Collections.sort(negative);
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (int i = 0, nDown = 0, pUp = 0; i <= n; i++) {
            while (pUp < positive.size() && positive.get(pUp) <= i) pUp++;
            while (nDown < negative.size() && negative.get(nDown) < i) nDown++;
            int liar = nDown + positive.size() - pUp;
            if (i == liar) {
                count++;
                sb.append(i).append(" ");
            }
        }
        sb.insert(0, count + "\n");
        return sb.toString();
    }
}
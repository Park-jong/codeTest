import java.io.*;
import java.util.StringTokenizer;

public class Main_19539_사과나무 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        int[] tree = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            tree[i] = Integer.parseInt(st.nextToken());
        }
        String result = isPossibleTree(tree, n);
        bw.write(result);
        bw.close();
        br.close();
    }

    static String isPossibleTree(int[] tree, int n) {
        int oneCount = 0;
        int twoCount = 0;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += tree[i];
            oneCount += tree[i] % 2;
            twoCount += tree[i] / 2;
        }
        return checkTreeCondition(oneCount, twoCount, sum) ? "YES" : "NO";
    }

    static boolean checkTreeCondition(int oneCount, int twoCount, int sum) {
        return sum % 3 == 0 && oneCount <= twoCount;
    }
}
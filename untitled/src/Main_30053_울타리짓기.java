import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_30053_울타리짓기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        Tree[] trees = new Tree[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            trees[i] = new Tree(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        int[] workers = new int[m];
        for (int i = 0; i < m; i++) {
            workers[i] = Integer.parseInt(br.readLine());
        }
        long result = getPieceLength(trees, workers);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static long getPieceLength(Tree[] trees, int[] workers) {
        Arrays.sort(workers);
        long[] sum = sumCutTree(workers.length, workers);
        long result = 0;
        for (Tree tree : trees) {
            if (tree.s < tree.e) {
                result += binarySearchLeftToRight(tree, workers, sum);
            } else {
                result += binarySearchRightToLeft(tree, workers, sum);
            }
        }
        return result;
    }

    static long[] sumCutTree(int m, int[] workers) {
        long[] sum = new long[m + 1];
        for (int i = 1; i < m; i++) {
            sum[i + 1] = (long) workers[i] - workers[i - 1] + sum[i - 1];
        }
        return sum;
    }

    static long binarySearchLeftToRight(Tree tree, int[] workers, long[] sum) {
        int left = 0;
        int right = workers.length - 1;
        int start = workers.length;
        while (left <= right) {
            int mid = (left + right) >> 1;
            if (workers[mid] <= tree.s) {
                left = mid + 1;
            } else {
                start = mid;
                right = mid - 1;
            }
        }
        left = 0;
        right = workers.length - 1;
        int end = -1;
        while (left <= right) {
            int mid = (left + right) >> 1;
            if (workers[mid] < tree.e) {
                end = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        if (end < 0 || start >= workers.length || end < start) return 0;
        long length = workers[start] - tree.s;
        if (start % 2 != end % 2) end--;
        length += sum[end + 1] - sum[start + 1];
        return length;
    }

    static long binarySearchRightToLeft(Tree tree, int[] workers, long[] sum) {
        int left = 0;
        int right = workers.length - 1;
        int end = workers.length;
        while (left <= right) {
            int mid = (left + right) >> 1;
            if (workers[mid] <= tree.e) {
                left = mid + 1;
            } else {
                end = mid;
                right = mid - 1;
            }
        }
        left = 0;
        right = workers.length - 1;
        int start = -1;
        while (left <= right) {
            int mid = (left + right) >> 1;
            if (workers[mid] < tree.s) {
                start = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        if (start < 0 || end >= workers.length || end > start) return 0;
        long length = tree.s - workers[start];
        if (start % 2 != end % 2) end++;
        length += sum[start] - sum[end];
        return length;
    }

    static class Tree {
        int s;
        int e;

        Tree(int s, int e) {
            this.s = s;
            this.e = e;
        }
    }
}
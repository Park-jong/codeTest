import java.io.*;
import java.util.StringTokenizer;

public class Main_1818_책정리 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        int[] books = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            books[i] = Integer.parseInt(st.nextToken());
        }
        int result = changeCount(books);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int changeCount(int[] books) {
        int n = books.length;
        int[] array = new int[n];
        int size = 0;
        for (int book : books) {
            int index = binarySearchIndex(array, size, book);
            array[index] = book;
            if (index == size) {
                size++;
            }
        }
        return n - size;
    }

    static int binarySearchIndex(int[] array, int size, int num) {
        int left = 0;
        int right = size;
        int index = size;
        while (left <= right) {
            int mid = (left + right) >> 1;
            if (array[mid] < num) {
                left = mid + 1;
            } else if (array[mid] > num) {
                index = mid;
                right = mid - 1;
            } else {
                return mid;
            }
        }
        return index;
    }
}
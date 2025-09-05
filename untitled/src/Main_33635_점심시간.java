import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main_33635_점심시간 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        Map<String, Integer> map = new HashMap<>();
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            map.put(st.nextToken(), i);
        }
        int m = Integer.parseInt(br.readLine());
        Book[] books = new Book[m];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int count = Integer.parseInt(st.nextToken());
            books[i] = new Book();
            books[i].name = st.nextToken();
            for (int j = 0; j < count; j++) {
                String genre = st.nextToken();
                books[i].addGenre(map.get(genre));
            }
        }
        int query = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < query; i++) {
            st = new StringTokenizer(br.readLine());
            int g = Integer.parseInt(st.nextToken());
            String[] bookGenre = new String[g];
            int count = 0;
            for (int j = 0; j < g; j++) {
                bookGenre[j] = st.nextToken();
            }
            long[] bit = new long[4];
            for (int j = 0; j < g; j++) {
                bit(bit, map.get(bookGenre[j]));
            }
            for (int j = 0; j < m; j++) {
                if (books[j].containsGenre(bit)) count++;
            }
            sb.append(count).append("\n");
        }
        bw.write(sb.toString());
        bw.close();
        br.close();
    }

    static void bit(long[] bit, int index) {
        int n = index / 64;
        long bitIndex = 1L << (index % 64);
        bit[n] |= bitIndex;
    }

    static class Book {
        String name;
        long[] genres = new long[4];

        void addGenre(int index) {
            int n = index / 64;
            long bit = 1L << (index % 64);
            genres[n] |= bit;
        }

        boolean containsGenre(long[] index) {
            for (int i = 0; i < index.length; i++) {
                if ((genres[i] & index[i]) != index[i]) return false;
            }
            return true;
        }
    }

}
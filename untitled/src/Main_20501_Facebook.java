import java.io.*;
import java.util.StringTokenizer;

public class Main_20501_Facebook {

    static int n;
    static Person[] people;
    static final int BIT = 64;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        people = new Person[n];
        for (int i = 0; i < n; i++) {
            people[i] = new Person(n);
            String f = br.readLine();
            for (int j = 0; j < n; j++) {
                if (f.charAt(j) == '1') {
                    int index = j / BIT;
                    people[i].friends[index] |= (1L << (j % BIT));
                }
            }
        }
        String result = query(br);
        bw.write(result);
        bw.close();
        br.close();
    }

    static String query(BufferedReader br) throws IOException {
        int q = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int p1 = Integer.parseInt(st.nextToken()) - 1;
            int p2 = Integer.parseInt(st.nextToken()) - 1;
            sb.append(count(p1, p2)).append("\n");
        }
        return sb.toString();
    }

    static int count(int p1, int p2) {
        int result = 0;
        int size = n / BIT + 1;
        for (int i = 0; i < size; i++) {
            result += Long.bitCount(people[p1].friends[i] & people[p2].friends[i]);
        }
        return result;
    }

    static class Person {
        long[] friends;

        Person(int n) {
            friends = new long[n / BIT + 1];
        }
    }
}
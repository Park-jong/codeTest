import java.io.*;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Main_17089_세친구 {

    static int min = Integer.MAX_VALUE;
    static Person[] people;
    static int[] nums = new int[3];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        people = new Person[n];
        for (int i = 0; i < n; i++) {
            people[i] = new Person(i);
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            setFriend(a, b);
        }
        for (int i = 0; i < n; i++) {
            nums[0] = i;
            findMinFriend(0, 0);
        }
        if (min == Integer.MAX_VALUE) {
            min = -1;
        }
        bw.write(String.valueOf(min));
        bw.close();
        br.close();
    }

    static void findMinFriend(int depth, int sum) {
        Person person = people[nums[depth]];
        if (depth == 2) {
            min = Math.min(min, sum + person.friends.size() - 6);
            return;
        }
        for (int i : person.friends) {
            if (i <= person.num) continue;
            nums[depth + 1] = i;
            if (depth == 0) {
                findMinFriend(depth + 1, sum + person.friends.size());
            } else if (depth == 1) {
                if (people[nums[0]].friends.contains(i)) {
                    findMinFriend(depth + 1, sum + person.friends.size());
                }
            }
        }
    }

    static void setFriend(int p1, int p2) {
        people[p1].friends.add(p2);
        people[p2].friends.add(p1);
    }

    static class Person {
        int num;
        Set<Integer> friends = new TreeSet<>();

        Person(int i) {
            num = i;
        }
    }
}
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_17220_마약수사대 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        Person[] people = new Person[n];
        for (int i = 0; i < n; i++) {
            people[i] = new Person();
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int prev = convert(st.nextToken());
            int next = convert(st.nextToken());
            link(people[prev], people[next]);
        }
        st = new StringTokenizer(br.readLine());
        int k = Integer.parseInt(st.nextToken());
        for (int i = 0; i < k; i++) {
            int num = convert(st.nextToken());
            removeLink(people[num]);
        }
        int count = count(people);
        bw.write(String.valueOf(count));
        bw.close();
        br.close();
    }

    static int convert(String s) {
        return s.charAt(0) - 'A';
    }

    static void link(Person prev, Person next) {
        prev.next.add(next);
        next.prevCount++;
    }

    static void removeLink(Person person) {
        if (person.next == null) {
            return;
        }
        person.isCaught = true;
        for (Person next : person.next) {
            decreaseLink(next);
        }
        person.next = null;
    }

    static void decreaseLink(Person person) {
        if (person.next == null) {
            return;
        }
        person.prevCount--;
        if (person.prevCount == 0) {
            for (Person next : person.next) {
                decreaseLink(next);
            }
            person.next = null;
        }
    }

    static int count(Person[] people){
        int count = 0;
        for(Person person : people){
            if(person.prevCount > 0 && !person.isCaught){
                count++;
            }
        }
        return count;
    }

    static class Person {
        List<Person> next = new ArrayList<>();
        int prevCount;
        boolean isCaught;
    }
}
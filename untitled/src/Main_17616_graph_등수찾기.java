import java.io.*;
import java.util.*;
import java.util.function.Function;

public class Main_17616_graph_등수찾기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken()) - 1;
        Student[] students = new Student[n];
        for(int i = 0; i < n; i++){
            students[i] = new Student(i);
        }

        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int higher = Integer.parseInt(st.nextToken()) - 1;
            int lower = Integer.parseInt(st.nextToken()) - 1;
            students[higher].addLower(students[lower]);
            students[lower].addHigher(students[higher]);
        }

        int lower = countLower(students[x]);
        int higher = countHigher(students[x]);
        int best = higher + 1;
        int worst = n - lower;
        sb.append(best).append(" ").append(worst);
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    static int count(Student student, Function<Student, List<Student>> getFunction){
        int count = 0;
        student.init();
        Queue<Student> q = new ArrayDeque<>();
        q.offer(student);
        while(!q.isEmpty()){
            Student current = q.poll();
            if(current.getVisited()){
                continue;
            }
            current.isVisited();
            for(Student s : getFunction.apply(current)){
                if(!s.getVisited()){
                    q.offer(s);
                }
            }
            count++;
        }
        return count - 1;
    }

    static int countHigher(Student student){
        return count(student, Student::getHigherList);
    }

    static int countLower(Student student){
        return count(student, Student::getLowerList);
    }


    static class Student{
        int num;
        boolean isVisited = false;
        List<Student> higher = new ArrayList<>();
        List<Student> lower = new ArrayList<>();
        Student(int num){
            this.num = num;
        }
        void init(){
            this.isVisited = false;
        }
        void addHigher(Student higher){
            this.higher.add(higher);
        }
        void addLower(Student lower){
            this.lower.add(lower);
        }
        void isVisited(){
            this.isVisited = true;
        }
        List<Student> getHigherList(){
            return higher;
        }
        List<Student> getLowerList(){
            return lower;
        }
        boolean getVisited(){
            return isVisited;
        }
    }
}
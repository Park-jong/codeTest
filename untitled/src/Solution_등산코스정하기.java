import java.util.*;
class Solution_등산코스정하기 {
    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        Node[] nodes = new Node[n];
        for(int i = 0; i < n; i++){
            nodes[i] = new Node(i);
        }
        for(int[] path : paths){
            int a = path[0] - 1;
            int b = path[1] - 1;
            nodes[a].next.add(new Course(b, path[2]));
            nodes[b].next.add(new Course(a, path[2]));
        }
        int[] intensity = findAllMinIntensity(n, gates, summits, nodes);
        int[] answer = findGoal(intensity, summits);
        return answer;
    }

    int[] findAllMinIntensity(int n, int[] gates, int[] summits, Node[] nodes){
        Set<Integer> goals = new HashSet<>();
        int[] cost = new int[n];
        Arrays.fill(cost, Integer.MAX_VALUE);
        PriorityQueue<Course> pq = new PriorityQueue<>((a, b)->{
            if(a.intensity == b.intensity) return Integer.compare(a.goal, b.goal);
            return Integer.compare(a.intensity, b.intensity);
        });
        for(int gate : gates){
            int index = gate - 1;
            pq.offer(new Course(index, 0));
            cost[index] = 0;
        }
        for(int summit : summits){
            goals.add(summit - 1);
        }
        while(!pq.isEmpty()){
            Course course = pq.poll();
            if(goals.contains(course.goal) || course.intensity > cost[course.goal]) continue;
            for(Course next : nodes[course.goal].next){
                int intensity = Math.max(next.intensity, course.intensity);
                if(intensity >= cost[next.goal]) continue;
                cost[next.goal] = intensity;
                pq.offer(new Course(next.goal, intensity));
            }
        }
        return cost;
    }

    int[] findGoal(int[] intensity, int[] summits){
        Arrays.sort(summits);
        int goal = 0;
        int min = Integer.MAX_VALUE;
        for(int summit : summits){
            if(min > intensity[summit - 1]){
                goal = summit;
                min = intensity[summit - 1];
            }
        }
        return new int[]{goal, min};
    }

    class Node {
        int index;
        List<Course> next = new ArrayList<>();

        Node(int index){
            this.index = index;
        }
    }

    class Course {
        int goal;
        int intensity;

        Course (int goal, int intensity){
            this.goal = goal;
            this.intensity = intensity;
        }
    }
}
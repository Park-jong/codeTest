import java.io.*;
import java.util.*;

public class Main_33906_다익스트라_점심나가서먹기 {
    static int n;
    static final int MAX_VALUE = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        PriorityQueue<Place> restaurants = new PriorityQueue<>((a, b) -> Integer.compare(a.cost, b.cost));
        PriorityQueue<Place> cafes = new PriorityQueue<>((a, b) -> Integer.compare(a.cost, b.cost));
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            int cost = Integer.parseInt(st.nextToken());
            if (cost == 0) continue;
            restaurants.offer(new Place(i, cost));
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            int cost = Integer.parseInt(st.nextToken());
            if (cost == 0) continue;
            cafes.offer(new Place(i, cost));
        }

        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node();
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int distance = Integer.parseInt(st.nextToken());
            nodes[a].next.add(new Edge(b, distance));
            nodes[b].next.add(new Edge(a, distance));
        }
        int home = 0;
        Result result = distance(home, restaurants, cafes, nodes);
        int restaurantToCafe = distance(result.toRestaurant, result.toCafe, nodes);
        int sum = result.toRestaurantDistance + result.toCafeDistance + restaurantToCafe;
        bw.write(String.valueOf(sum));
        bw.close();
        br.close();
    }

    static Result distance(int start, PriorityQueue<Place> restaurants, PriorityQueue<Place> cafes, Node[] nodes) {
        PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.distance, b.distance));
        boolean[] isVisited = new boolean[n];
        int[] distance = new int[n];
        Arrays.fill(distance, MAX_VALUE);
        pq.offer(new Edge(start, 0));
        while (!pq.isEmpty()) {
            Edge current = pq.poll();
            if (isVisited[current.destination]) continue;
            isVisited[current.destination] = true;
            distance[current.destination] = current.distance;
            for (Edge next : nodes[current.destination].next) {
                if (isVisited[next.destination]) continue;
                Edge e = new Edge(next.destination, distance[current.destination] + next.distance);
                pq.offer(e);
            }
        }
        Result result = new Result();
        while (!restaurants.isEmpty()) {
            Place place = restaurants.poll();
            if (isVisited[place.number]) {
                result.toRestaurant = place.number;
                result.toRestaurantDistance = distance[place.number];
                break;
            }
        }
        while (!cafes.isEmpty()) {
            Place place = cafes.poll();
            if (isVisited[place.number]) {
                result.toCafe = place.number;
                result.toCafeDistance = distance[place.number];
                break;
            }
        }
        return result;
    }

    static int distance(int start, int goal, Node[] nodes) {
        PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.distance, b.distance));
        boolean[] isVisited = new boolean[n];
        int[] distance = new int[n];
        Arrays.fill(distance, MAX_VALUE);
        pq.offer(new Edge(start, 0));
        int result = MAX_VALUE;
        while (!pq.isEmpty()) {
            Edge current = pq.poll();
            if (isVisited[current.destination]) continue;
            isVisited[current.destination] = true;
            distance[current.destination] = current.distance;
            if (current.destination == goal) {
                result = distance[current.destination];
                break;
            }
            for (Edge next : nodes[current.destination].next) {
                if (isVisited[next.destination]) continue;
                Edge e = new Edge(next.destination, distance[current.destination] + next.distance);
                pq.offer(e);
            }
        }
        return result;
    }

    static class Place {
        int number;
        int cost;

        Place(int number, int cost) {
            this.number = number;
            this.cost = cost;
        }
    }

    static class Node {
        List<Edge> next = new ArrayList<>();
    }

    static class Edge {
        int destination;
        int distance;

        Edge(int destination, int distance) {
            this.destination = destination;
            this.distance = distance;
        }
    }

    static class Result {
        int toRestaurant;
        int toCafe;
        int toRestaurantDistance;
        int toCafeDistance;
    }
}
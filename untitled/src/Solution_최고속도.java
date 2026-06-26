import java.util.*;

class Solution_최고속도 {
    public int[] solution(int[][] city, int[][] road) {
        Map<String, Point> points = new HashMap<>();
        addCameraPoint(road, points);
        addCityPoint(city, points);
        divideRoads(road, points);
        int[] result = findMaxSpeed(city, points);
        return result;
    }

    void addCameraPoint(int[][] road, Map<String, Point> points){
        for(int[] r : road){
            int cctvX = (r[0] + r[2]) >> 1;
            int cctvY = (r[1] + r[3]) >> 1;
            Point point = new Point(cctvX, cctvY);
            point.limit = r[4];
            String key = cctvX + " " + cctvY;
            if(points.containsKey(key))
                point.limit = Math.min(r[4], points.get(key).limit);
            points.put(key, point);
        }
    }

    void addCityPoint(int[][] city, Map<String, Point> points){
        int n = city.length;
        for(int i = 0; i < n; i++){
            Point point = new Point(city[i][0], city[i][1]);
            point.city = i;
            String key = city[i][0] + " " + city[i][1];
            if(!points.containsKey(key))
                points.put(key, point);
        }
    }

    void divideRoads(int[][] road, Map<String, Point> points){
        int n = road.length;
        Road[] roads = new Road[n];
        for(int i = 0; i < n; i++){
            roads[i] = new Road(road[i]);
        }
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(roads[i].y1 == roads[i].y2 || roads[j].x1 == roads[j].x2) continue;
                if(roads[i].x1 < roads[j].x1 || roads[i].x2 > roads[j].x2 ||
                        roads[i].y1 > roads[j].y1 || roads[i].y2 < roads[j].y2) continue;
                int x = roads[i].x1;
                int y = roads[j].y1;
                String key = x + " " + y;
                if(points.containsKey(key)) continue;
                Point point = new Point(x, y);
                points.put(key, point);
            }
        }
        for(Road r : roads){
            String key = r.x1 + " " + r.y1;
            if(!points.containsKey(key)){
                Point p = new Point(r.x1, r.y1);
                points.put(key, p);
            }
            key = r.x2 + " " + r.y2;
            if(!points.containsKey(key)){
                Point p = new Point(r.x2, r.y2);
                points.put(key, p);
            }
            for(Point p : points.values()){
                if(r.x1 <= p.x && r.x2 >= p.x && r.y1 <= p.y && r.y2 >= p.y){
                    r.divide.add(p);
                }
            }
            r.divide.sort((a, b) -> {
                if(a.x == b.x) return Integer.compare(a.y, b.y);
                return Integer.compare(a.x, b.x);
            });
            int size = r.divide.size();
            for(int i = 0; i < size - 1; i++){
                Point p1 = r.divide.get(i);
                Point p2 = r.divide.get(i + 1);
                Edge edge = new Edge(p1, p2);
                p1.edges.add(edge);
                p2.edges.add(edge);
            }
        }
    }

    int[] findMaxSpeed(int[][] city, Map<String, Point> points){
        int n = city.length;
        String key = city[0][0] + " " + city[0][1];
        Point startCity = points.get(key);
        Position start = new Position(startCity.x, startCity.y, Integer.MAX_VALUE);
        PriorityQueue<Position> pq = new PriorityQueue<>((a, b) -> Integer.compare(b.speed, a.speed));
        pq.offer(start);
        int[] result = new int[n - 1];
        int count = 0;
        while(!pq.isEmpty()){
            Position p = pq.poll();
            key = p.x + " " + p.y;
            Point point = points.get(key);
            if(point.isVisited) continue;
            point.isVisited = true;
            if(point.city > 0){
                result[point.city - 1] = p.speed == Integer.MAX_VALUE ? 0 : p.speed;
                count++;
            }
            if(count == n - 1) break;
            for(Edge e : point.edges){
                Point next = e.p1 == point ? e.p2 : e.p1;
                if(next.isVisited) continue;
                pq.offer(new Position(next.x, next.y, Math.min(next.limit, p.speed)));
            }
        }
        return result;
    }

    class Road {
        int x1;
        int x2;
        int y1;
        int y2;
        List<Point> divide = new ArrayList<>();

        Road(int[] road){
            this.x1 = road[0];
            this.y1 = road[1];
            this.x2 = road[2];
            this.y2 = road[3];
        }
    }

    class Edge {
        Point p1;
        Point p2;

        Edge(Point p1, Point p2){
            this.p1 = p1;
            this.p2 = p2;
        }
    }

    class Point {
        int x;
        int y;
        int limit = Integer.MAX_VALUE;
        int city = -1;
        boolean isVisited;
        List<Edge> edges = new ArrayList<>();

        Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    class Position{
        int x;
        int y;
        int speed;

        Position(int x, int y, int speed){
            this.x = x;
            this.y = y;
            this.speed = speed;
        }
    }
}
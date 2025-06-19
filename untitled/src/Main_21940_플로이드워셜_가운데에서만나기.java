import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_21940_플로이드워셜_가운데에서만나기 {

    static final int MAX_VALUE = Integer.MAX_VALUE >> 1;
    static int n;
    static int m;
    static int friends;
    static int[][] roads;
    static int[] friendsCity;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        roads = makeRoadsArray(br);
        friendsCity = makeFriendsArray(br);
        String cities = fillAndFindCities();
        bw.write(cities);
        bw.close();
        br.close();
    }

    static String fillAndFindCities(){
        fillRoadsMinimumTime();
        int[] times = new int[n];
        int minTime = findMinimumTime(times);
        return findCities(times, minTime);
    }

    static int[][] makeRoadsArray(BufferedReader br) throws IOException {
        int[][] roads = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(roads[i], MAX_VALUE);
            roads[i][i] = 0;
        }
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken()) - 1;
            int goal = Integer.parseInt(st.nextToken()) - 1;
            int time = Integer.parseInt(st.nextToken());
            roads[start][goal] = Math.min(time, roads[start][goal]);
        }
        return roads;
    }

    static int[] makeFriendsArray(BufferedReader br) throws IOException{
        StringTokenizer st = new StringTokenizer(br.readLine());
        friends = Integer.parseInt(st.nextToken());
        int[] friendsCity = new int[friends];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < friends; i++){
            friendsCity[i] = Integer.parseInt(st.nextToken()) - 1;
        }
        return friendsCity;
    }

    static void fillRoadsMinimumTime(){
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(i == j) continue;
                for(int k = 0; k < n; k++){
                    if(i == k || j == k) continue;
                    roads[j][k] = Math.min(roads[j][k], roads[j][i] + roads[i][k]);
                }
            }
        }
    }

    static int findMinimumTime(int[] cityTime){
        int min = MAX_VALUE;
        for(int i = 0; i < n; i++){
            int max = 0;
            for(int j = 0; j < friends; j++){
                int friend = friendsCity[j];
                max = Math.max(max, roads[friend][i] + roads[i][friend]);
            }
            cityTime[i] = max;
            min = Math.min(min, max);
        }
        return min;
    }

    static String findCities(int[] times, int time){
        StringBuilder cities = new StringBuilder();
        for(int i = 0; i < n; i++){
            if(times[i] == time){
                cities.append(i + 1).append(" ");
            }
        }
        return cities.toString();
    }
}
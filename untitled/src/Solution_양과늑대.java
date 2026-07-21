class Solution_양과늑대 {
    int n;
    int max = 0;
    boolean[] visited;
    int[] info;
    int[][] edges;
    public int solution(int[] info, int[][] edges) {
        n = info.length;
        visited = new boolean[1 << n];
        this.info = info;
        this.edges = edges;
        findMaxSheep(1, 1, 0);
        return max;
    }

    void findMaxSheep(int bit, int sheep, int wolf){
        if(sheep <= wolf || visited[bit]) return;
        max = Math.max(max, sheep);
        visited[bit] = true;
        for(int[] edge : edges){
            int cur = edge[1];
            int b = 1 << edge[1];
            int pb = 1 << edge[0];
            if((bit & b) != 0 || (bit & pb) == 0) continue;
            int nBit = bit | b;
            if(info[cur] == 0){
                findMaxSheep(nBit, sheep + 1, wolf);
            } else {
                findMaxSheep(nBit, sheep, wolf + 1);
            }
        }
    }
}
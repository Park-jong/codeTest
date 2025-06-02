import java.io.*;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_27315_틀리는건싫으니까 {
    static int idea;
    static int solve;
    static long wrong;
    static int solveCount;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        Problem[] problems = new Problem[n];
        PriorityQueue<Problem> normalQueue = new PriorityQueue<>((a, b) -> Integer.compare(a.ideaLevel, b.ideaLevel));
        PriorityQueue<Problem> editorialQueue = new PriorityQueue<>((a, b) -> Integer.compare(a.ideaLevel, b.ideaLevel));
        PriorityQueue<Problem> possibleQueue = new PriorityQueue<>((a, b) -> {
            if (a.existData == b.existData)
                return Integer.compare(a.solveLevel, b.solveLevel);
            return Boolean.compare(b.existData, a.existData);
        });

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            problems[i] = new Problem(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            if (problems[i].existEditorial) {
                editorialQueue.offer(problems[i]);
            } else {
                normalQueue.offer(problems[i]);
            }
        }

        st = new StringTokenizer(br.readLine());
        idea = Integer.parseInt(st.nextToken());
        solve = Integer.parseInt(st.nextToken());
        while (true) {
            checkNormalQueue(normalQueue, possibleQueue);
            checkEditorialQueue(editorialQueue, possibleQueue);
            int currentSolveCount = solveProblem(possibleQueue, m);
            if (currentSolveCount == 0 || solveCount >= m) break;
        }
        long result = wrong;
        if (solveCount < m) {
            result = -1;
        }
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static void checkNormalQueue(PriorityQueue<Problem> queue, PriorityQueue<Problem> possibleQueue) {
        while (!queue.isEmpty() && queue.peek().ideaLevel <= idea) {
            possibleQueue.offer(queue.poll());
        }
    }

    static void checkEditorialQueue(PriorityQueue<Problem> queue, PriorityQueue<Problem> possibleQueue) {
        while (!queue.isEmpty() && queue.peek().ideaLevel <= idea * 2) {
            Problem problem = queue.poll();
            problem.ideaLevel /= 2;
            problem.solveLevel /= 2;
            possibleQueue.offer(problem);
        }
    }

    static int solveProblem(PriorityQueue<Problem> queue, int goal) {
        if (queue.isEmpty()) return 0;
        int count = 0;
        while (!queue.isEmpty()) {
            Problem problem = queue.peek();
            if (!problem.existData && problem.solveLevel > solve) {
                break;
            }
            queue.poll();
            count++;
            solveSuccess();
            if (solveCount == goal) {
                return count;
            }
        }
        if (count == 0) {
            count = solveOne(queue);
        }
        return count;
    }

    static void solveSuccess() {
        idea++;
        solve++;
        solveCount++;
    }

    static int solveOne(PriorityQueue<Problem> queue) {
        wrong += Objects.requireNonNull(queue.poll()).solveLevel - solve;
        solveSuccess();
        return 1;
    }

    static class Problem {
        int ideaLevel;
        int solveLevel;
        boolean existData;
        boolean existEditorial;

        Problem(int ideaLevel, int solveLevel, int data, int editorial) {
            this.ideaLevel = ideaLevel;
            this.solveLevel = solveLevel;
            this.existData = data > 0;
            this.existEditorial = editorial > 0;
        }
    }
}
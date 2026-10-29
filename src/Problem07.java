import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Problem07 {

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] pair : prerequisites) {
            int course = pair[0];
            int prerequisite = pair[1];
            graph.get(prerequisite).add(course);
        }

        int[] state = new int[numCourses];

        for (int start = 0; start < numCourses; start++) {
            if (state[start] != 0) {
                continue;
            }

            Deque<int[]> stack = new ArrayDeque<>();
            stack.push(new int[]{start, 0});
            state[start] = 1;

            while (!stack.isEmpty()) {
                int[] frame = stack.peek();
                int course = frame[0];
                List<Integer> nextCourses = graph.get(course);

                if (frame[1] == nextCourses.size()) {
                    state[course] = 2;
                    stack.pop();
                    continue;
                }

                int next = nextCourses.get(frame[1]);
                frame[1]++;

                if (state[next] == 1) {
                    return false;
                }

                if (state[next] == 0) {
                    state[next] = 1;
                    stack.push(new int[]{next, 0});
                }
            }
        }

        return true;
    }
}

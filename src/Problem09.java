import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Problem09 {

    public int[] findOrder(int numCourses, int[][] prerequisites) {
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
        int[] order = new int[numCourses];
        int size = 0;

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

                if (frame[1] < nextCourses.size()) {
                    int next = nextCourses.get(frame[1]);
                    frame[1]++;

                    if (state[next] == 1) {
                        return new int[0];
                    }

                    if (state[next] == 0) {
                        state[next] = 1;
                        stack.push(new int[]{next, 0});
                    }
                } else {
                    state[course] = 2;
                    order[size] = course;
                    size++;
                    stack.pop();
                }
            }
        }

        for (int left = 0, right = size - 1; left < right; left++, right--) {
            int temp = order[left];
            order[left] = order[right];
            order[right] = temp;
        }

        return order;
    }
}

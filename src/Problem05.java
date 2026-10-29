import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class Problem05 {

    public int countDependencyGroups(int n, int[][] dependencies) {
        List<List<Integer>> graph = new ArrayList<>();
        List<List<Integer>> reversedGraph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
            reversedGraph.add(new ArrayList<>());
        }

        for (int[] dependency : dependencies) {
            int from = dependency[0];
            int to = dependency[1];
            graph.get(from).add(to);
            reversedGraph.get(to).add(from);
        }

        boolean[] visited = new boolean[n];
        List<Integer> finishOrder = new ArrayList<>(n);

        // First pass: record vertices in DFS finishing order.
        for (int start = 0; start < n; start++) {
            if (visited[start]) {
                continue;
            }

            Deque<int[]> stack = new ArrayDeque<>();
            stack.push(new int[]{start, 0});
            visited[start] = true;

            while (!stack.isEmpty()) {
                int[] frame = stack.peek();
                int node = frame[0];
                List<Integer> neighbors = graph.get(node);

                if (frame[1] < neighbors.size()) {
                    int next = neighbors.get(frame[1]);
                    frame[1]++;

                    if (!visited[next]) {
                        visited[next] = true;
                        stack.push(new int[]{next, 0});
                    }
                } else {
                    finishOrder.add(node);
                    stack.pop();
                }
            }
        }

        Arrays.fill(visited, false);
        int groups = 0;

        // Second pass: each traversal in the reversed graph finds one SCC.
        for (int i = finishOrder.size() - 1; i >= 0; i--) {
            int start = finishOrder.get(i);
            if (visited[start]) {
                continue;
            }

            groups++;
            Deque<Integer> stack = new ArrayDeque<>();
            stack.push(start);
            visited[start] = true;

            while (!stack.isEmpty()) {
                int node = stack.pop();

                for (int next : reversedGraph.get(node)) {
                    if (!visited[next]) {
                        visited[next] = true;
                        stack.push(next);
                    }
                }
            }
        }

        return groups;
    }
}

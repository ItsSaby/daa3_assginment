import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class Problem06 {

    public int countDependencyGroups(int n, int[][] dependencies) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] dependency : dependencies) {
            graph.get(dependency[0]).add(dependency[1]);
        }

        int[] index = new int[n];
        int[] low = new int[n];
        Arrays.fill(index, -1);

        boolean[] onStack = new boolean[n];
        Deque<Integer> activeStack = new ArrayDeque<>();
        int timer = 0;
        int groups = 0;

        // Explicit DFS frames keep long dependency chains safe from StackOverflowError.
        for (int start = 0; start < n; start++) {
            if (index[start] != -1) {
                continue;
            }

            Deque<int[]> dfs = new ArrayDeque<>();
            index[start] = timer;
            low[start] = timer;
            timer++;
            activeStack.push(start);
            onStack[start] = true;
            dfs.push(new int[]{start, 0, -1});

            while (!dfs.isEmpty()) {
                int[] frame = dfs.peek();
                int node = frame[0];
                List<Integer> neighbors = graph.get(node);

                if (frame[1] < neighbors.size()) {
                    int next = neighbors.get(frame[1]);
                    frame[1]++;

                    if (index[next] == -1) {
                        index[next] = timer;
                        low[next] = timer;
                        timer++;
                        activeStack.push(next);
                        onStack[next] = true;
                        dfs.push(new int[]{next, 0, node});
                    } else if (onStack[next]) {
                        low[node] = Math.min(low[node], index[next]);
                    }
                } else {
                    dfs.pop();
                    int parent = frame[2];

                    if (parent != -1) {
                        low[parent] = Math.min(low[parent], low[node]);
                    }

                    if (low[node] == index[node]) {
                        while (true) {
                            int member = activeStack.pop();
                            onStack[member] = false;
                            if (member == node) {
                                break;
                            }
                        }
                        groups++;
                    }
                }
            }
        }

        return groups;
    }
}

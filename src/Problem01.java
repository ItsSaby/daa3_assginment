import java.util.ArrayDeque;
import java.util.Deque;

public class Problem01 {

    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        boolean[] visited = new boolean[n];
        int provinces = 0;

        for (int city = 0; city < n; city++) {
            if (visited[city]) {
                continue;
            }

            provinces++;
            Deque<Integer> stack = new ArrayDeque<>();
            stack.push(city);
            visited[city] = true;

            while (!stack.isEmpty()) {
                int current = stack.pop();

                for (int neighbor = 0; neighbor < n; neighbor++) {
                    if (isConnected[current][neighbor] == 1 && !visited[neighbor]) {
                        visited[neighbor] = true;
                        stack.push(neighbor);
                    }
                }
            }
        }

        return provinces;
    }
}

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Problem03 {

    public int minCostConnectPoints(int[][] points) {
        int n = points.length;
        if (n <= 1) {
            return 0;
        }

        List<int[]> edges = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int distance = Math.abs(points[i][0] - points[j][0])
                        + Math.abs(points[i][1] - points[j][1]);
                edges.add(new int[]{distance, i, j});
            }
        }

        edges.sort(Comparator.comparingInt(edge -> edge[0]));

        int[] parent = new int[n];
        int[] rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        int totalCost = 0;
        int edgesUsed = 0;

        for (int[] edge : edges) {
            int first = edge[1];
            int second = edge[2];

            if (union(first, second, parent, rank)) {
                totalCost += edge[0];
                edgesUsed++;

                if (edgesUsed == n - 1) {
                    break;
                }
            }
        }

        return totalCost;
    }

    private int find(int point, int[] parent) {
        if (parent[point] != point) {
            parent[point] = find(parent[point], parent);
        }
        return parent[point];
    }

    private boolean union(int first, int second, int[] parent, int[] rank) {
        int rootFirst = find(first, parent);
        int rootSecond = find(second, parent);

        if (rootFirst == rootSecond) {
            return false;
        }

        if (rank[rootFirst] < rank[rootSecond]) {
            parent[rootFirst] = rootSecond;
        } else if (rank[rootFirst] > rank[rootSecond]) {
            parent[rootSecond] = rootFirst;
        } else {
            parent[rootSecond] = rootFirst;
            rank[rootFirst]++;
        }

        return true;
    }
}

import java.util.Arrays;

public class Problem04 {

    public int minCostConnectPoints(int[][] points) {
        int n = points.length;
        if (n <= 1) {
            return 0;
        }

        int[] minDistance = new int[n];
        boolean[] inTree = new boolean[n];
        Arrays.fill(minDistance, Integer.MAX_VALUE);
        minDistance[0] = 0;

        int totalCost = 0;

        for (int count = 0; count < n; count++) {
            int current = -1;

            for (int point = 0; point < n; point++) {
                if (!inTree[point]
                        && (current == -1 || minDistance[point] < minDistance[current])) {
                    current = point;
                }
            }

            inTree[current] = true;
            totalCost += minDistance[current];

            for (int next = 0; next < n; next++) {
                if (inTree[next]) {
                    continue;
                }

                int distance = Math.abs(points[current][0] - points[next][0])
                        + Math.abs(points[current][1] - points[next][1]);
                minDistance[next] = Math.min(minDistance[next], distance);
            }
        }

        return totalCost;
    }
}

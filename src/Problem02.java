public class Problem02 {

    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        int[] parent = new int[n];
        int[] rank = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        int provinces = n;


        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (isConnected[i][j] == 1 && union(i, j, parent, rank)) {
                    provinces--;
                }
            }
        }

        return provinces;
    }

    private int find(int city, int[] parent) {
        while (parent[city] != city) {
            parent[city] = parent[parent[city]];
            city = parent[city];
        }

        return city;
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

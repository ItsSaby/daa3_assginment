# DAA Assignment 3 - Graph Algorithms Report


## Problem 1 - Number of Provinces using DFS
**Method:** `Problem01.findCircleNum(int[][] isConnected)`

Treat each city as a vertex. Two cities are adjacent when `isConnected[i][j] == 1`. For every city not yet visited, increase the province count and run an iterative depth-first search (DFS) to mark every city reachable from it. Each DFS traversal visits exactly one connected component, which is one province.

**Time complexity:** `O(V^2)`, because the matrix row of each visited city is scanned.  
**Space complexity:** `O(V)` for the visited array and explicit DFS stack.

**Important detail:** The stack is iterative rather than recursive, avoiding Java call-stack overflow on large inputs.

## Problem 2 - Number of Provinces using DSU
**Method:** `Problem02.findCircleNum(int[][] isConnected)`

Disjoint Set Union (DSU), also called Union-Find, initially places each city in its own set. For each connected pair, `union` merges their sets if they are different. Every successful merge reduces the number of provinces by one. `find` uses path compression and `union` uses rank to keep the trees shallow. The matrix is symmetric, so only pairs where `j > i` are checked.

**Time complexity:** `O(V^2 α(V))`, where `α` is the inverse Ackermann function and is effectively constant for practical input sizes.  
**Space complexity:** `O(V)` for parent and rank arrays.

## Problem 3 - Minimum Cost to Connect All Points using Kruskal
**Method:** `Problem03.minCostConnectPoints(int[][] points)`

Every point is a vertex. The Manhattan distance between every pair of points is treated as an edge weight. Kruskal's algorithm sorts all edges by weight and processes them from smallest to largest. DSU accepts an edge only when it joins two previously separate components, preventing cycles. The algorithm stops after accepting `V - 1` edges, which is enough to connect all points.

**Time complexity:** `O(V^2 log V)`, since there are `V(V-1)/2 = O(V^2)` edges and they are sorted. DSU operations add a near-constant factor.  
**Space complexity:** `O(V^2)` to store all possible edges.

## Problem 4 - Minimum Cost to Connect All Points using Prim
**Method:** `Problem04.minCostConnectPoints(int[][] points)`

Prim's algorithm grows one spanning tree. `minDist[i]` stores the cheapest known edge connecting an unselected point to the current tree. Each iteration selects the unused point with the smallest value, adds that cost, and updates distances to the remaining points using Manhattan distance. This implementation uses arrays and a linear scan rather than a priority queue.

**Time complexity:** `O(V^2)`: there are `V` iterations, each with a linear selection scan and a linear update scan.  
**Space complexity:** `O(V)` for `minDist` and `used`.

## Problem 5 - Software Dependency Cycles using Kosaraju
**Method:** `Problem05.countDependencyGroups(int n, int[][] dependencies)`

The answer is the number of strongly connected components (SCCs). A dependency `[a, b]` creates a directed edge `a -> b`. Kosaraju's algorithm has two passes:
1. Run DFS on the original graph and record each vertex when its exploration finishes.
2. Reverse every edge, then process vertices in reverse finishing order. Each traversal in the reversed graph identifies one SCC, so the counter is increased once per traversal.

Both DFS passes are iterative to handle long paths safely. A vertex with no cycle still forms a one-vertex SCC, as required by the prompt.

**Time complexity:** `O(V + E)`, since each vertex and edge is processed a constant number of times.  
**Space complexity:** `O(V + E)` for the original and reversed adjacency lists, visited array, order list, and stacks.

## Problem 6 - Software Dependency Cycles using Tarjan
**Method:** `Problem06.countDependencyGroups(int n, int[][] dependencies)`

Tarjan's algorithm finds SCCs in one DFS traversal. `index[v]` is the discovery order of a vertex; `low[v]` is the smallest discovery index reachable from its DFS subtree using tree edges and, where appropriate, a back edge. Vertices currently in the active SCC stack are marked with `onStack`. When `low[v] == index[v]`, `v` is the root of an SCC, and vertices are popped through `v` to complete that component.

The implementation uses explicit DFS frames instead of recursive calls so a chain with up to `100,000` vertices does not overflow the Java call stack.

**Time complexity:** `O(V + E)`, because each vertex and edge is processed a constant number of times.  
**Space complexity:** `O(V + E)` for the adjacency list, arrays, active stack, and DFS frames.

## Problem 7 - Course Schedule using DFS cycle detection
**Method:** `Problem07.canFinish(int numCourses, int[][] prerequisites)`

A prerequisite pair `[a, b]` means course `b` must be taken before `a`, so the graph contains `b -> a`. Each vertex has one of three states: `0` means unvisited, `1` means currently on the DFS path, and `2` means fully processed. Finding an edge to a state-`1` vertex proves there is a directed cycle, meaning not all courses can be completed. If no such edge is found, the courses can be completed.

**Time complexity:** `O(V + E)`.  
**Space complexity:** `O(V + E)` for the graph, state array, and explicit DFS stack.

## Problem 8 - Course Schedule using Kahn's Algorithm
**Method:** `Problem08.canFinish(int numCourses, int[][] prerequisites)`

Kahn's algorithm uses indegrees. The indegree of a course is the number of prerequisites it still has. All zero-indegree courses enter a queue. Each removed course is counted as completed, and its outgoing edges are removed by decreasing neighboring indegrees. A neighbor enters the queue when its indegree becomes zero. If the number processed equals `numCourses`, there is no cycle; otherwise, a cycle blocks some courses.

**Time complexity:** `O(V + E)`, as each vertex is queued at most once and each edge is processed once.  
**Space complexity:** `O(V + E)` for the graph, indegree array, and queue.

## Problem 9 - Course Schedule II using DFS reverse finishing order
**Method:** `Problem09.findOrder(int numCourses, int[][] prerequisites)`

This uses the same three-state DFS cycle detection as Problem 7. When a course has no remaining outgoing neighbors to explore, it is appended to the result. Reversing this finishing order produces a topological ordering consistent with the edges `prerequisite -> course`. If a back edge reveals a cycle, the method returns an empty array, as required by Course Schedule II.

**Time complexity:** `O(V + E)`.  
**Space complexity:** `O(V + E)` for the graph, state array, result, and explicit DFS stack.

## Problem 10 - Course Schedule II using Kahn's Algorithm
**Method:** `Problem10.findOrder(int numCourses, int[][] prerequisites)`

Kahn's algorithm builds a topological ordering. It starts with all courses whose indegree is zero, repeatedly appends a queued course to the result, and decreases the indegrees of its dependent courses. If all courses are appended, the result is a valid order. If fewer than `numCourses` courses are processed, the graph contains a cycle and the method returns an empty array.

**Time complexity:** `O(V + E)`.  
**Space complexity:** `O(V + E)` for the graph, indegree array, queue, and result.

package com.smartcity.graph;

import java.util.*;

public class CondensationGraph {
    public static class Result {
        public final int compCount;
        public final int[] compOf;
        public final List<List<Integer>> dag;

        public Result(int compCount, int[] compOf, List<List<Integer>> dag) {
            this.compCount = compCount;
            this.compOf = compOf;
            this.dag = dag;
        }
    }

    public static Result build(List<List<Integer>> graph, List<List<Integer>> sccs) {
        int n = graph.size();

        int[] compOf = new int[n];
        for (int cid = 0; cid < sccs.size(); cid++) {
            for (int v : sccs.get(cid)) compOf[v] = cid;
        }

        int c = sccs.size();
        List<Set<Integer>> dagSet = new ArrayList<>(c);
        for (int i = 0; i < c; i++) dagSet.add(new HashSet<>());

        for (int u = 0; u < n; u++) {
            int cu = compOf[u];
            for (int v : graph.get(u)) {
                int cv = compOf[v];
                if (cu != cv) dagSet.get(cu).add(cv);
            }
        }

        List<List<Integer>> dag = new ArrayList<>(c);
        for (int i = 0; i < c; i++) dag.add(new ArrayList<>(dagSet.get(i)));

        return new Result(c, compOf, dag);
    }
}



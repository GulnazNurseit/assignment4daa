package com.smartcity.graph;

import java.util.*;

public class TopologicalSort {
    private List<List<Integer>> graph;

    public TopologicalSort(List<List<Integer>> graph) {
        this.graph = graph;
    }

    public List<Integer> kahnTopologicalSort() {
        int n = graph.size();
        int[] inDegree = new int[n];
        for (int u = 0; u < n; u++) {
            for (int v : graph.get(u)) inDegree[v]++;
        }

        ArrayDeque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) if (inDegree[i] == 0) q.add(i);

        List<Integer> order = new ArrayList<>(n);
        while (!q.isEmpty()) {
            int u = q.poll();
            order.add(u);
            for (int v : graph.get(u)) {
                if (--inDegree[v] == 0) q.add(v);
            }
        }

        return order;
    }
}

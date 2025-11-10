package com.smartcity.graph;

import java.util.*;

public class DAGShortestLongestPaths {
    private List<List<Integer>> graph;
    private int n;

    public DAGShortestLongestPaths(List<List<Integer>> graph) {
        this.graph = graph;
        this.n = graph.size();
    }

    public int[] shortestPaths(int source, List<Integer> topoOrder) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        for (int u : topoOrder) {
            if (dist[u] != Integer.MAX_VALUE) {
                for (int v : graph.get(u)) {
                    if (dist[v] > dist[u] + 1) {
                        dist[v] = dist[u] + 1;
                    }
                }
            }
        }
        return dist;
    }

    public int[] longestPaths(int source, List<Integer> topoOrder) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MIN_VALUE);
        dist[source] = 0;

        for (int u : topoOrder) {
            if (dist[u] != Integer.MIN_VALUE) {
                for (int v : graph.get(u)) {
                    if (dist[v] < dist[u] + 1) {
                        dist[v] = dist[u] + 1;
                    }
                }
            }
        }
        return dist;
    }
}



package com.smartcity;

import com.google.gson.Gson;
import com.smartcity.graph.*;
import java.io.FileReader;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        GraphData g = load("src/main/resources/data/tasks.json");

        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < g.n; i++) adj.add(new ArrayList<>());
        for (Edge e : g.edges) adj.get(e.u).add(e.v);

        TarjanSCC tarjan = new TarjanSCC(adj);
        List<List<Integer>> sccs = tarjan.findSCCs();
        System.out.println("SCC count: " + sccs.size());
        System.out.println("SCCs: " + sccs);

        CondensationGraph.Result cr = CondensationGraph.build(adj, sccs);

        TopologicalSort topo = new TopologicalSort(cr.dag);
        List<Integer> compOrder = topo.kahnTopologicalSort();
        System.out.println("Topological order of components: " + compOrder);

        DAGShortestLongestPaths dagPaths = new DAGShortestLongestPaths(cr.dag);
        int source = g.source;
        int[] shortestDist = dagPaths.shortestPaths(source, compOrder);
        int[] longestDist = dagPaths.longestPaths(source, compOrder);

        System.out.println("Shortest distances from source " + source + ": " + Arrays.toString(shortestDist));
        System.out.println("Longest distances from source " + source + ": " + Arrays.toString(longestDist));
    }

    static GraphData load(String path) {
        try (FileReader r = new FileReader(path)) {
            return new Gson().fromJson(r, GraphData.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static class GraphData {
        boolean directed;
        int n;
        List<Edge> edges;
        int source;
        String weight_model;
    }

    static class Edge {
        int u, v, w;
    }
}

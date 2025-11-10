package com.smartcity.graph;

import java.util.*;

public class TarjanSCC {
    private List<List<Integer>> graph;
    private int[] lowLink;
    private int[] ids;
    private boolean[] onStack;
    private Stack<Integer> stack;
    private List<List<Integer>> sccs;
    private int id;

    public TarjanSCC(List<List<Integer>> graph) {
        this.graph = graph;
        this.lowLink = new int[graph.size()];
        this.ids = new int[graph.size()];
        this.onStack = new boolean[graph.size()];
        this.stack = new Stack<>();
        this.sccs = new ArrayList<>();
        this.id = 0;
    }

    public List<List<Integer>> findSCCs() {
        for (int i = 0; i < graph.size(); i++) {
            if (ids[i] == 0) {
                dfs(i);
            }
        }
        return sccs;
    }

    private void dfs(int at) {
        stack.push(at);
        onStack[at] = true;
        ids[at] = lowLink[at] = ++id;

        for (int to : graph.get(at)) {
            if (ids[to] == 0) {
                dfs(to);
                lowLink[at] = Math.min(lowLink[at], lowLink[to]);
            } else if (onStack[to]) {
                lowLink[at] = Math.min(lowLink[at], ids[to]);
            }
        }

        if (ids[at] == lowLink[at]) {
            List<Integer> scc = new ArrayList<>();
            while (true) {
                int node = stack.pop();
                onStack[node] = false;
                scc.add(node);
                if (node == at) break;
            }
            sccs.add(scc);
        }
    }
}

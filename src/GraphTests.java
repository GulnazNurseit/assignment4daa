package com.smartcity;

import com.smartcity.graph.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class GraphTests {

    private List<List<Integer>> sampleGraph() {
        int n = 8;
        List<List<Integer>> g = new ArrayList<>();
        for (int i = 0; i < n; i++) g.add(new ArrayList<>());
        g.get(0).add(1);
        g.get(1).add(2);
        g.get(2).add(3);
        g.get(3).add(1); // цикл 1-2-3
        g.get(4).add(5);
        g.get(5).add(6);
        g.get(6).add(7);
        return g;
    }

    @Test
    public void testTarjanSCC() {
        List<List<Integer>> g = sampleGraph();
        TarjanSCC tarjan = new TarjanSCC(g);
        List<List<Integer>> sccs = tarjan.findSCCs();

        assertEquals(6, sccs.size());
        boolean found = sccs.stream().anyMatch(c -> new HashSet<>(c).equals(new HashSet<>(Arrays.asList(1,2,3))));
        assertTrue(found);
    }

    @Test
    public void testTopologicalOnCondensation() {
        List<List<Integer>> g = sampleGraph();
        TarjanSCC tarjan = new TarjanSCC(g);
        List<List<Integer>> sccs = tarjan.findSCCs();

        CondensationGraph.Result cr = CondensationGraph.build(g, sccs);
        TopologicalSort topo = new TopologicalSort(cr.dag);
        List<Integer> order = topo.kahnTopologicalSort();

        assertEquals(cr.compCount, order.size());
        assertEquals(new HashSet<>(order).size(), order.size());
    }
}




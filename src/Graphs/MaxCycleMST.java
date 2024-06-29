package graphs;

import java.util.ArrayList;
import java.util.Stack;

class CycleDetector {

    private boolean[] visited;
    private boolean hasCycle;
    private UndirectedEdge heaviestEdge;
    private UndirectedWeightedGraph graph;

    public CycleDetector(UndirectedWeightedGraph g) {
        this.graph = g;
        this.visited = new boolean[g.vCount()];
    }

    private ArrayList<UndirectedEdge> findCycleEdges(Stack<Integer> visitedVertices, Stack<UndirectedEdge> visitedEdges, int v) {
        ArrayList<UndirectedEdge> cycleEdges = new ArrayList<>();
        cycleEdges.add(visitedEdges.pop());
        while (true) {
            if (visitedEdges.peek().v1() == v || visitedEdges.peek().v2() == v) {
                cycleEdges.add(visitedEdges.pop());
                break;
            }
            cycleEdges.add(visitedEdges.pop());
        }
        return cycleEdges;
    }

    private UndirectedEdge findHeaviestEdge(Stack<Integer> visitedVertices, Stack<UndirectedEdge> visitedEdges, int v) {
        ArrayList<UndirectedEdge> cycleEdges = findCycleEdges(visitedVertices, visitedEdges, v);
        UndirectedEdge heaviest = null;
        for (UndirectedEdge var : cycleEdges) {
            if (heaviest == null) heaviest = var;
            else if (var.weight() > heaviest.weight()) heaviest = var;
        }
        return heaviest;
    }

    private void visit(int v, Stack<Integer> visitedVertices, Stack<UndirectedEdge> visitedEdges) {
        this.visited[v] = true;

        for (UndirectedEdge var : graph.adj(v)) {
            if (this.hasCycle) return;
            else if (!this.visited[var.other(v)]) {
                visitedVertices.push(v);
                visitedEdges.push(var);
                visit(var.other(v), visitedVertices, visitedEdges);
            } else if (visitedVertices.peek() != var.other(v)) {
                this.hasCycle = true;
                visitedEdges.push(var);
                this.heaviestEdge = findHeaviestEdge(visitedVertices, visitedEdges, var.other(v));
                return;
            }
        }

        if (!visitedVertices.isEmpty())
            visitedVertices.pop();

        // if this.hascycle = false pop edge
        if (!visitedEdges.isEmpty() && !this.hasCycle)
            visitedEdges.pop();
    }

    public void search() {
        Stack<Integer> visitedVertices = new Stack<>();
        Stack<UndirectedEdge> visitedEdges = new Stack<>();
        hasCycle = false;

        for (int i = 0; i < this.graph.vCount(); i++)
            this.visited[i] = false;


        for (int i = 0; i < this.graph.vCount(); i++) {
            if (!this.visited[i]) visit(i, visitedVertices, visitedEdges);
            if (this.hasCycle) return;
        }
    }

    public boolean hasCycle() {
        return hasCycle;
    }

    public UndirectedEdge heaviestEdge() {
        return this.heaviestEdge;
    }
}

public class MaxCycleMST {

    private UndirectedWeightedGraph MST;
    private UndirectedWeightedGraph original;
    private boolean MSTDone = false;

    public MaxCycleMST(UndirectedWeightedGraph g) {
        this.MST = new UndirectedWeightedGraph(g.vCount());
        this.original = g;
    }

    public UndirectedEdge determineMaxInCycle(UndirectedWeightedGraph g) {
        if (g.eCount() < 2)
            return null;
        CycleDetector b = new CycleDetector(g);
        b.search();
        return b.heaviestEdge();
    }

    public UndirectedWeightedGraph buildMST() {
        for (UndirectedEdge var : this.original.allEdges()) {
            MST.addEdge(var);
            UndirectedEdge cycle = determineMaxInCycle(MST);
            if (cycle != null)
                MST.removeEdge(cycle);
        }
        this.MSTDone = true;
        return this.MST;
    }

    public UndirectedWeightedGraph getMST() {
        if (this.MSTDone)
            return this.MST;
        return null;
    }

    public static void main(String[] args) {
        UndirectedWeightedGraph g = new UndirectedWeightedGraph(9);
        g.addEdge(new UndirectedEdge(0, 2, 1));
        g.addEdge(new UndirectedEdge(1, 3, 2));
        g.addEdge(new UndirectedEdge(2, 4, 1));
        g.addEdge(new UndirectedEdge(2, 5, 3));
        g.addEdge(new UndirectedEdge(3, 6, 1));
        g.addEdge(new UndirectedEdge(3, 4, 2));
        g.addEdge(new UndirectedEdge(5, 6, 4));
        g.addEdge(new UndirectedEdge(6, 7, 7));
        g.addEdge(new UndirectedEdge(7, 6, 7));
        CycleDetector b = new CycleDetector(g);
        b.search();
        System.out.println(b.hasCycle());
        System.out.println(b.heaviestEdge());
        System.out.println(g.toString());
    }
}
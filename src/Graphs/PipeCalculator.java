package graphs;

public class PipeCalculator {
    private UndirectedWeightedGraph graph, MST;
    private boolean finish = false;

    public PipeCalculator(int n, float[] well, float[][] costs) {
        this.graph = createGraph(n, well, costs);
    }

    public UndirectedWeightedGraph createGraph(int n, float[] well, float[][] costs) {
        UndirectedWeightedGraph build  = new UndirectedWeightedGraph(n+1);

        // well connection
        for (int i = 0; i < n; i++) {
            build.addEdge(new UndirectedEdge(i, n, well[i]));
        }

        // houses connections
        for (int i = 0; i < n; i++) {
            for (int j = i; j >= 0; j--) {
                if (costs[i][j] != 0)
                    build.addEdge(new UndirectedEdge(j, i, costs[i][j]));
            }
        }
        return build;
    }

    public UndirectedWeightedGraph calculateSolution(UndirectedWeightedGraph g) {
        MaxCycleMST graph = new MaxCycleMST(g);
        UndirectedWeightedGraph MST = graph.buildMST();
        return MST;
    }

    public UndirectedWeightedGraph calculateSolution () {
        this.finish = true;
        this.MST = calculateSolution(this.graph);
        return MST;
    }

    public UndirectedWeightedGraph getMST() {
        if (this.finish)
            return this.MST;
        return null;
    }

    public static void main(String[] args) {
        float[] x = {1,2,3,4};
        float[][] y = {{1,2,3,4}, {1,2,3,4}, {1,2,3,4}, {1,2,3,4}};
        PipeCalculator p = new PipeCalculator(5, x, y);
        p.createGraph(4, x, y);
        System.out.println(p.calculateSolution().toString());
    }
}
package finlab;

import java.util.List;

public class Graph {
    private int[][] adjacencyMatrix;
    private List<String> vertexList;

    Graph(int[][] adjacencyMatrix, List<String> vertexList) {
        this.adjacencyMatrix = adjacencyMatrix;
        this.vertexList = vertexList;
    }

    public List<String> getVertexList() {
        return vertexList;
    }

    public int[][] getMatrix() {
        return adjacencyMatrix;
    }

    public void setVertexList(List<String> vertexList) {
        this.vertexList = vertexList;
    }

    public void setMatrix(int[][] adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
    }
}

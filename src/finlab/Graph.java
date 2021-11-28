package finlab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Graph {
    private Integer[][] adjacencyMatrix;
    private List<String> vertexList = new ArrayList<>();
    private boolean isDirected;
    private int numEdges;

    Graph(Integer[][] adjacencyMatrix, List<String> vertexList, boolean isDirected) {
        this.adjacencyMatrix = adjacencyMatrix;
        this.vertexList = vertexList;
        this.isDirected = isDirected;
    }

    Graph() {
        this(null, null, false);
    }

    public List<String> getVertexList() {
        return vertexList;
    }

    public Integer[][] getMatrix() {
        return adjacencyMatrix;
    }

    public void setVertexList(List<String> vertexList) {
        this.vertexList = vertexList;
    }

    public void setMatrix(Integer[][] adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
    }

    public boolean isDirected() {
        return isDirected;
    }

    public void setDirected(boolean directed) {
        isDirected = directed;
    }

    public int getNumberOfVertices() {
        return vertexList.size();
    }

    private boolean isValidVertex(String vertex) {
        return vertexList.contains(vertex);
    }

    public void addEdge(String vertex1, int weight1, String vertex2, int weight2) {
        if (!isValidVertex(vertex1) || !isValidVertex(vertex2))
            throw new InvalidVertexException("Invalid vertices.");
        addEdge(vertexList.indexOf(vertex1), vertexList.indexOf(vertex2), weight1, weight2);
    }

    public void addEdge(String vertex1, int weight, String vertex2) {
        if (!isValidVertex(vertex1) || !isValidVertex(vertex2))
            throw new InvalidVertexException("Invalid vertices.");
        addEdge(vertexList.indexOf(vertex1), vertexList.indexOf(vertex2), weight, weight);
    }

    private void addEdge(int index1, int index2, int weight1, int weight2) {
        adjacencyMatrix[index1][index2] = weight1;
        adjacencyMatrix[index2][index1] = weight2;
    }

    public void removeEdge(String vertex1, String vertex2) {
        if(!isValidVertex(vertex1) || !isValidVertex(vertex2))
            throw new InvalidVertexException("Invalid vertices.");
        addEdge(vertexList.indexOf(vertex1), vertexList.indexOf(vertex2), -1, -1);
    }

    public void addVertex(String vertex) {
        if ((getNumberOfVertices() + 1) == adjacencyMatrix.length) expandCapacity();
        if (vertexList.contains(vertex))
            throw new InvalidVertexException("Vertex " + vertex + " exists.");

        vertexList.add(vertex);

        for (int i = 0, v = getNumberOfVertices(); i < v; i++)
            addEdge(v, i, -1, -1);

    }

    public void removeVertex(String vertex) {
        if (!vertexList.contains(vertex))
            throw new InvalidVertexException("Vertex " + vertex + "does not exist.");

        vertexList.remove(vertex);
        for (int i = 0, v = getNumberOfVertices(); i < v; i++)
            addEdge(v, i, 0, 0);
    }


    private void expandCapacity() {
        int size = vertexList.size() * 2;
        Integer[][] largerAdjacencyMatrix = new Integer[size][size];

        for (int i = 0; i < getNumberOfVertices(); i++) {
            for (int j = 0; i < getNumberOfVertices(); i++) {
                largerAdjacencyMatrix[i][j] = adjacencyMatrix[i][j];
            }
        }
        adjacencyMatrix = largerAdjacencyMatrix;
    }



}

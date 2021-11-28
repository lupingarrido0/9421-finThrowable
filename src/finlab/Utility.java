package finlab;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Utility {

    public Graph parseCSV(File file) throws InvalidDataFileException {
        Graph graph = new Graph();
        Integer[][] adjacencyMatrix;
        List<String> vertices = new ArrayList<>();
        List<List<Integer>> csv = new ArrayList<>();
        List<Integer> csvIntTemp;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String[] values;

            // Segment to determine if Graph is Directed or Undirected
            // New Graph Instances are Undirected by default
            String line = br.readLine();    // read first line
            if (line.contains("DIRECTED") || line.contains("directed")) graph.setDirected(true);

            line = br.readLine();   // read second line
            values = line.split(",");   // read the vertex list
            vertices = Arrays.asList(values);

            // succeeding lines in csv are now the adjacency matrix itself
            while((line = br.readLine()) != null) {
                csvIntTemp = new ArrayList<>();
                values = line.split(",");
                for (String x : values) csvIntTemp.add(Integer.parseInt(x));
                csv.add(csvIntTemp);
            }

        } catch (Exception ignored) {

        }

        // Check for errors
        try {
            if (vertices.size() != csv.get(0).size());
        } catch (Exception e) {
            throw new InvalidDataFileException("Invalid CSV File.");
        }

        // Construct the matrix
        List<Integer[]> list = new ArrayList<>();
        for (List<Integer> l : csv) {
            Integer[] integers = l.toArray(Integer[]::new);
            list.add(integers);
        }
        adjacencyMatrix = list.toArray(new Integer[0][]);

        graph.setMatrix(adjacencyMatrix);
        graph.setVertexList(vertices);

        return graph;
    }

    private StringBuilder depthTraversal (String startingVertex, int[][] matrix, List<String> vertices) throws InvalidVertexException {
        List<String> vertexList = new ArrayList<>();
        StringBuilder result = new StringBuilder();
        Stack<String> stack = new Stack<>();
        List<String> tempVerList = new ArrayList<>();
        
        if(startingVertex.isEmpty()) {
            System.out.println("Vertex is empty.");
        }else{
            stack.push(startingVertex);
        }

        for (int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[i].length;j++){
                if(Double.isFinite(matrix[i][j])){
                    if(tempVerList.get(i)!=vertexList.get(j)){
                        stack.push(vertexList.get(j));
                    }
                    stack.push(vertexList.get(i));
                }
            }
        }
        String s = stack.toString();
        result.append(s);
        return result;
    }

    private StringBuilder breadthTraversal
            (String startingVertex, int[][] matrix, List<String> vertices)
            throws InvalidVertexException {

        return null;
    }

     void determineShortestPath(Integer[][] matrix, int index, List<String> vertices) {
        StringBuilder output = new StringBuilder();
        /*
        Scanner a = new Scanner(System.in);
        System.out.println("Enter the starting vertex");
        String input = a.nextLine();
        int c = 0;
        int index =0;
        for (String v: vertices){
            if (input.equals(v)){
                index = c;
            }
            c++;
        }

         */


        int nVertices = matrix[0].length;

        // shortestDistances[i] will hold the
        // shortest distance from src to i
        int[] shortestDistances = new int[nVertices];

        // added[i] will true if vertex i is
        // included / in shortest path tree
        // or shortest distance from src to
        // i is finalized
        boolean[] added = new boolean[nVertices];

        // Initialize all distances as
        // INFINITE and added[] as false
        for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
            shortestDistances[vertexIndex] = Integer.MAX_VALUE;
            added[vertexIndex] = false;
        }

        // Distance of source vertex from
        // itself is always 0
        shortestDistances[index] = 0;

        // Parent array to store shortest
        // path tree
        int[] parents = new int[nVertices];

        // The starting vertex does not
        // have a parent
        parents[index] = -1;

        // Find shortest path for all
        // vertices
        for (int i = 1; i < nVertices; i++) {

            // Pick the minimum distance vertex
            // from the set of vertices not yet
            // processed. nearestVertex is
            // always equal to startNode in
            // first iteration.
            int nearestVertex = -1;
            int shortestDistance = Integer.MAX_VALUE;
            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
                if (!added[vertexIndex] && shortestDistances[vertexIndex] < shortestDistance) {
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }

            // Mark the picked vertex as
            // processed
            added[nearestVertex] = true;

            // Update dist value of the
            // adjacent vertices of the
            // picked vertex.
            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
                int edgeDistance = matrix[nearestVertex][vertexIndex];

                if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[vertexIndex])) {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = shortestDistance +
                            edgeDistance;
                }
            }
        }

        int nVertices1 = shortestDistances.length;
        for (int vertexIndex = 0;
             vertexIndex < nVertices1;
             vertexIndex++)
        {
            if (vertexIndex != index)
            {
                printPath(vertexIndex, parents, vertices);
                // System.out.println();
            }
        }
    }

/*
    // Function to print shortest path
    // from source to currentVertex
    // using parents array
    private static StringBuilder getPath(int currentVertex, int[] parents, List<String> vertices) {
        StringBuilder result = new StringBuilder();
        // Base case : Source node has
        // been processed
        if (currentVertex == -1)
        {
            return result;
        }
        getPath(parents[currentVertex], parents, vertices);
        result.append(vertices.get(currentVertex));
        result.append(" ");
    }

 */
    private static void printPath(int currentVertex, int[] parents, List<String> vertices) {
        // Base case : Source node has
        // been processed
        if (currentVertex == -1)
        {
            return;
        }
        printPath(parents[currentVertex], parents, vertices);
        System.out.print(currentVertex + " ");
    }
    // Driver Code
    public static void main(String[] args)
    {
        List<String> vertices = new ArrayList<>();
        vertices.add("A");
        vertices.add("B");
        vertices.add("C");
        vertices.add("D");
        vertices.add("E");
        vertices.add("F");
        int[][] adjacencyMatrix = { { 0, 4, 3, 0, 0, 0},
                { 4, 0, 1, 2, 0, 0},
                { 3, 1, 0, 4, 0, 0},
                { 0, 2, 4, 0, 2, 0},
                { 0, 0, 0, 2, 0, 6},
                { 0, 0, 0, 0, 6, 0},};
        // System.out.print(determineShortestPath(adjacencyMatrix, 0, vertices ));
    }
}





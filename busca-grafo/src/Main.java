import entities.Edge;
import entities.Graph;
import entities.Vertex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        List<Vertex> vertices = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        buildGraph(vertices, edges);

        Graph graph = new Graph(vertices, edges);
        graph.print();

    }

    private static void buildGraph(List<Vertex> vertices, List<Edge> edges) throws IOException {

        String filepath = "graphs/graph-test-100.txt";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
        String input = bufferedReader.readLine(); /* Descarta linha inicial */

        while ((input = bufferedReader.readLine()) != null) {
            String[] arrInput = input.trim().split(" ");
            Vertex origin = new Vertex(Integer.parseInt(arrInput[0]));
            Vertex destiny = new Vertex(Integer.parseInt(arrInput[arrInput.length - 1]));
            vertices.add(origin);
            edges.add(new Edge(origin, destiny));
        }
    }
}

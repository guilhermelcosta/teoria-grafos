package utils;

import entities.Edge;
import entities.Vertex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class GraphUtil {

    public static void buildGraph(List<Vertex> vertices, List<Edge> edges, String filepath) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
        String input = bufferedReader.readLine(); // Descarta linha inicial

        while ((input = bufferedReader.readLine()) != null) {

            String[] arrInput = input.trim().split(" ");
            Vertex origin = new Vertex(Integer.parseInt(arrInput[0]));
            Vertex destiny = new Vertex(Integer.parseInt(arrInput[arrInput.length - 1]));

            if (vertices.stream().noneMatch(vertex -> vertex.getId() == origin.getId()))
                vertices.add(origin);

            edges.add(new Edge(origin, destiny));
        }
    }
}

package entities;

import java.util.List;

public class Graph {

    List<Vertex> vertices;
    List<Edge> edges;

    public Graph(List<Vertex> vertices, List<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    public void print() {
        edges.forEach(edge -> System.out.println(edge.getOrigin().getId() + " -> " + edge.getDestiny().getId()));
    }

}

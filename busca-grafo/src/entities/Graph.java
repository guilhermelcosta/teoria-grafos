package entities;

import java.util.ArrayList;
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

    public void dfsAux() {

        /*todo: melhorar isso aqui*/
        int t = 0;
        int[] startTime = new int[vertices.size() + 1];
        int[] endTime = new int[vertices.size() + 1];
        Vertex[] father = new Vertex[vertices.size() + 1];

        /*todo: melhorar isso aqui*/
        for (Vertex vertex : vertices) {
            startTime[vertex.getId()] = 0;
            endTime[vertex.getId()] = 0;
            father[vertex.getId()] = null;
        }

        for (Vertex vertex : vertices)
            if (startTime[vertex.getId()] == 0)
                dfs(t, startTime, endTime, father, vertex);
    }

    private void dfs(int t, int[] td, int[] tt, Vertex[] father, Vertex vertex) {

        t++;
        td[vertex.getId()] = t;
        List<Edge> outgoingEdges = getOutgoingEdges(vertex);
        List<Vertex> vertexSuccessors = outgoingEdges
                .stream()
                .map(Edge::getDestiny)
                .toList();

        /*todo: melhorar isso de count*/
        int count = 0;

        for (Vertex w : vertexSuccessors) {
            count++;
            if (td[w.getId()] == 0) {
                Edge edge =  outgoingEdges.get(count);
                edge.setClassification("Arvore");
                father[w.getId()] = vertex;
                dfs(t, td, tt, father, w);
            }
        }


    }

    private Edge getCurrentEdge(Vertex vertex, List<Edge> outgoingEdges) {
        for (Edge edge : outgoingEdges)
            if (edge.getDestiny().getId() == vertex.getId())
                return edge;

        return null;
    }

    private List<Edge> getOutgoingEdges(Vertex vertex) {

        List<Edge> outgoingEdges = new ArrayList<>();

        for (Edge edge : edges)
            if (edge.getOrigin().getId() == vertex.getId())
                outgoingEdges.add(edge);

        return outgoingEdges;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }
}

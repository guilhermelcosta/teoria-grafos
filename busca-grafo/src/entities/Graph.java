package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Graph {

    List<Vertex> vertices;
    List<Edge> edges;

    /**
     * Construtor padrao do grafo
     *
     * @param vertices vertices do grafo
     * @param edges    arestas do grafo
     */
    public Graph(List<Vertex> vertices, List<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    /**
     * Imprime todas as arestas do grafo no formato "[origem] -> [destino]"
     */
    public void print() {
        edges.forEach(edge -> System.out.println(edge.getOrigin().getId() + " -> " + edge.getDestiny().getId()));
    }

    /**
     * Busca em profundidade (metodo publico chamado pelo usuario)
     */
    public void dfs() {

        int t = 0;
        int tableSize = vertices.size() + 1;
        var startTime = new int[tableSize];
        var endTime = new int[tableSize];
        var father = new Vertex[tableSize];

        Arrays.fill(startTime, 0);
        Arrays.fill(endTime, 0);
        Arrays.fill(father, null);

        for (Vertex vertex : vertices)
            if (startTime[vertex.getId()] == 0)
                dfs(t, startTime, endTime, father, vertex);
    }

    /**
     * Busca em profundidade (algoritmo de busca)
     * Foi utilizado o algoritmo disponibilizado pelo prof. Zenilton no material da disciplina
     *
     * @param t         tempo
     * @param startTime array com o tempo de inicio de cada vertice
     * @param endTime   array com o tempo de termino de cada vertice
     * @param father    array com o pai (predecessor) de cada vertice
     * @param vertex    raiz da busca
     */
    private void dfs(int t, int[] startTime, int[] endTime, Vertex[] father, Vertex vertex) {

        startTime[vertex.getId()] = ++t;

        List<Edge> outgoingEdges = getOutgoingEdges(vertex)
                .stream()
                .sorted(Comparator.comparingInt(edge -> edge.getDestiny().getId()))
                .toList();
        List<Vertex> vertexSuccessors = outgoingEdges
                .stream()
                .map(Edge::getDestiny)
                .toList();

        for (Vertex w : vertexSuccessors) {
            if (startTime[w.getId()] == 0) {
                classifyEdge(w, outgoingEdges, "Arvore");
                father[w.getId()] = vertex;
                dfs(t, startTime, endTime, father, w);
            } else {
                if (endTime[w.getId()] == 0)
                    classifyEdge(w, outgoingEdges, "Retorno");
                else {
                    if (startTime[vertex.getId()] < endTime[w.getId()])
                        classifyEdge(w, outgoingEdges, "Avanco");
                    else
                        classifyEdge(w, outgoingEdges, "Cruzamento");
                }
            }
        }
        endTime[vertex.getId()] = ++t;

        //        if (outgoingEdges.stream().allMatch(edge -> edge.getClassification() != null)) {
        //            List<Edge> outgoingEdgesInitialVertex = getOutgoingEdges(initialVertex)
        //                    .stream()
        //                    .sorted(Comparator.comparingInt(edge -> edge.getDestiny().getId()))
        //                    .toList();
        //            if (outgoingEdges.stream().allMatch(edge -> edge.getClassification() != null))
        //                return;
        //        }
    }

    /**
     * Classifica uma aresta do grafo
     *
     * @param vertex         vertice do grafo
     * @param outgoingEdges  lista de sucessores do vertice
     * @param classification classificacao da aresta
     */
    private void classifyEdge(Vertex vertex, List<Edge> outgoingEdges, String classification) {
        outgoingEdges.forEach(edge -> {
            if (edge.getDestiny().getId() == vertex.getId())
                edge.setClassification(classification);
        });
    }

    /**
     * Obtem lista de sucessores de um determinado vertice
     *
     * @param vertex vertice do grafo
     * @return lista de sucessores de um determinado vertice
     */
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

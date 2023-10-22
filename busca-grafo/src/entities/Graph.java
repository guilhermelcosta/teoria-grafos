package entities;

import java.util.Arrays;

public class Graph {

    Vertex[] vertices;
    Edge[] edges;
    int[] pointer;

    /**
     * Construtor padrao do grafo
     *
     * @param vertices vertices do grafo
     * @param edges    arestas do grafo
     */
    public Graph(Vertex[] vertices, Edge[] edges) {
        this.vertices = vertices;
        this.edges = edges;
        this.pointer = createPointer(this.edges);
    }

    /**
     * Construtor do apontador do grafo
     *
     * @param edges arestas do grafo
     * @return array de apontador
     */
    private int[] createPointer(Edge[] edges) {

        System.out.println("Construindo apontador...");

        int[] pointer = new int[vertices.length];
        pointer[1] = 1;
        pointer[pointer.length - 1] = edges.length;

        for (int i = 2; i < pointer.length - 1; i++)
            pointer[i] = getOriginPosition(i, edges, pointer);

        System.out.println("Apontador construido!");

        return pointer;
    }

    /**
     * Obtem a primeira posicao em que um vertice aparece na lista de arestas (desconsiderando a posicao 0 do array)
     * Por exemplo, no array {1, 1, 1, 2, 2, 3}, o id 2 aparece na posicao 4
     *
     * @param id      id do vertice
     * @param edges   arestas do grafo
     * @param pointer apontador do grafo
     * @return posicao em que um vertice aparece na lista de arestas
     */
    private int getOriginPosition(int id, Edge[] edges, int[] pointer) {
        int index = pointer[id - 1];
        while (edges[++index].getOrigin().getId() != id)
            ;
        return index;
    }

    /**
     * Imprime todas as arestas do grafo no formato "[origem] → [destino]"
     */
    public void print() {
        for (int i = 1; i < edges.length; i++)
            System.out.println(edges[i].getOrigin().getId() + " -> " + edges[i].getDestiny().getId());
    }

    /**
     * Busca em profundidade (metodo publico chamado pelo usuario)
     */
    public void depthFirstSearch() {

        int t = 0;
        int tableSize = vertices.length;
        var startTime = new int[tableSize];
        var endTime = new int[tableSize];

        for (int i = 1; i < vertices.length - 1; i++)
            if (startTime[vertices[i].getId()] == 0)
                depthFirstSearch(t, startTime, endTime, vertices[i]);
    }

    /**
     * Busca em profundidade (algoritmo de busca)
     * Foi utilizado o algoritmo disponibilizado pelo prof. Zenilton no material da disciplina
     *
     * @param t         tempo
     * @param startTime array com o tempo de inicio de cada vertice
     * @param endTime   array com o tempo de termino de cada vertice
     * @param vertex    raiz da busca
     */
    private void depthFirstSearch(int t, int[] startTime, int[] endTime, Vertex vertex) {

        startTime[vertex.getId()] = ++t;

        Edge[] outgoingEdges = getOutgoingEdges(vertex);
        sortArray(outgoingEdges);
        Vertex[] vertexSuccessors = getVertexSuccessors(outgoingEdges);

        for (Vertex w : vertexSuccessors) {
            if (startTime[w.getId()] == 0) {
                classifyEdge(w, outgoingEdges, "Arvore");
                depthFirstSearch(t, startTime, endTime, w);
            } else if (endTime[w.getId()] == 0)
                classifyEdge(w, outgoingEdges, "Retorno");
            else if (startTime[vertex.getId()] < endTime[w.getId()])
                classifyEdge(w, outgoingEdges, "Avanco");
            else
                classifyEdge(w, outgoingEdges, "Cruzamento");
        }
        endTime[vertex.getId()] = ++t;
    }

    /**
     * Ordena um array lexicograficamente
     * Durante os meus testes, percebi que utilizar estruturas convencionais como Arrays.sort() eram mais pesadas
     * do que um metodo a parte
     *
     * @param outgoingEdges array de vizinhos do vertice
     */
    private void sortArray(Edge[] outgoingEdges) {
        for (int i = 0; i < outgoingEdges.length - 1; i++) {
            for (int j = 0; j < outgoingEdges.length - i - 1; j++) {
                if (outgoingEdges[j].getDestiny().getId() > outgoingEdges[j + 1].getDestiny().getId()) {
                    Edge temp = outgoingEdges[j];
                    outgoingEdges[j] = outgoingEdges[j + 1];
                    outgoingEdges[j + 1] = temp;
                }
            }
        }
    }

    /**
     * Classifica uma aresta do grafo
     *
     * @param vertex         vertice do grafo
     * @param outgoingEdges  lista de sucessores do vertice
     * @param classification classificacao da aresta
     */
    private void classifyEdge(Vertex vertex, Edge[] outgoingEdges, String classification) {
        Arrays.asList(outgoingEdges).forEach(edge -> {
            if (edge.getDestiny().getId() == vertex.getId())
                edge.setClassification(classification);
        });
    }

    /**
     * Obtem array de arestas que saem de um determinado vertice
     *
     * @param vertex vertice do grafo
     * @return lista de arestas que saem de um determinado vertice
     */
    private Edge[] getOutgoingEdges(Vertex vertex) {

        int startingPosition = pointer[vertex.getId()];
        int lastPosition = pointer[vertex.getId() + 1];
        Edge[] outgoingEdges = new Edge[lastPosition - startingPosition];

        int count = 0;
        for (int i = startingPosition; i < lastPosition; i++)
            outgoingEdges[count++] = edges[i];

        return outgoingEdges;
    }

    /**
     * Obtem array de vertices vizinhos a um determinado vertice
     *
     * @param outgoingEdges lista de arestas que saem de um determinado vertice
     * @return array de vertices vizinhos a um determinado vertice
     */
    private Vertex[] getVertexSuccessors(Edge[] outgoingEdges) {
        Vertex[] vertexSuccessors = new Vertex[outgoingEdges.length];
        for (int i = 0; i < vertexSuccessors.length; i++)
            vertexSuccessors[i] = outgoingEdges[i].getDestiny();
        return vertexSuccessors;
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public void setVertices(Vertex[] vertices) {
        this.vertices = vertices;
    }

    public Edge[] getEdges() {
        return edges;
    }

    public void setEdges(Edge[] edges) {
        this.edges = edges;
    }

    public int[] getPointer() {
        return pointer;
    }

    public void setPointer(int[] pointer) {
        this.pointer = pointer;
    }
}

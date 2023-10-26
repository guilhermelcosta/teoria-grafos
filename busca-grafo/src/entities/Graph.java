package entities;

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
        this.pointer = createPointer();
    }

    /**
     * Construtor do apontador do grafo
     *
     * @return array de apontador
     */
    private int[] createPointer() {

        System.out.println("Construindo apontador...");

        int[] pointer = new int[vertices.length];
        pointer[1] = 1;
        pointer[pointer.length - 1] = edges.length;

        for (int i = 2; i < pointer.length - 1; i++)
            pointer[i] = getOriginPosition(i, pointer);

        System.out.println("Apontador construido! \n---");

        return pointer;
    }

    /**
     * Obtem a primeira posicao em que um vertice aparece na lista de arestas (desconsiderando a posicao 0 do array)
     * Por exemplo, no array {1, 1, 1, 2, 2, 3}, o id 2 aparece na posicao 4
     *
     * @param id      id do vertice
     * @param pointer apontador do grafo
     * @return posicao em que um vertice aparece na lista de arestas
     */
    private int getOriginPosition(int id, int[] pointer) {
        int index = pointer[id - 1];
        for (int i = index; i < edges.length; i++)
            if (edges[++index].getOrigin().getId() == id)
                break;
        return index;
    }

    /**
     * Imprime todas as arestas do grafo no formato "[origem] → [destino]"
     */
    public void print() {
        for (int i = 1; i < edges.length; i++)
            System.out.println(edges[i].getOrigin().getId() + " -> " + edges[i].getDestiny().getId());
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

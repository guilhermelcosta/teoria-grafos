package entities;

import enums.DfsEnum;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;

public class DepthFirstSearch {

    /**
     * Realiza busca em profundidade no grafo
     *
     * @param graph   grafo de referencia
     * @param dfsEnum enumeration de busca, pode ser DfsEnum.ITERATIVE ou DfsEnum.RECURSIVE
     */
    public static void run(Graph graph, DfsEnum dfsEnum) {

        Vertex[] vertices = graph.getVertices();
        int[] startTime = new int[graph.getVertices().length];
        int[] endTime = new int[graph.getVertices().length];
        int t = 0;

        for (int i = 1; i < vertices.length - 1; i++)
            if (startTime[vertices[i].getId()] == 0) {
                if (dfsEnum.equals(DfsEnum.RECURSIVE)) {
                    System.out.println("Chamada recursiva \n---");
                    recursiveDepthFirstSearch(t, startTime, endTime, vertices[i], graph);
                } else {
                    System.out.println("Chamada iterativa \n---");
                    iterativeDepthFirstSearch(t, startTime, endTime, vertices[i], graph);
                }
            }
    }

    /**
     * Busca em profundidade (algoritmo de busca)
     * Foi utilizado o algoritmo disponibilizado pelo prof. Zenilton no material da disciplina
     *
     * @param startTime array com o tempo de inicio de cada vertice
     * @param endTime   array com o tempo de termino de cada vertice
     * @param vertex    raiz da busca
     */
    private static int recursiveDepthFirstSearch(int t, int[] startTime, int[] endTime, Vertex vertex, Graph graph) {

        startTime[vertex.getId()] = ++t;

        Edge[] outgoingEdges = getOutgoingEdges(vertex, graph);
        sortAsArray(outgoingEdges);
        Vertex[] vertexSuccessors = getVertexSuccessors(outgoingEdges);

        for (Vertex w : vertexSuccessors) {
            if (startTime[w.getId()] == 0) {
                classifyEdge(w, outgoingEdges, "Arvore");
                t = recursiveDepthFirstSearch(t, startTime, endTime, w, graph);
            } else if (endTime[w.getId()] == 0)
                classifyEdge(w, outgoingEdges, "Retorno");
            else if (startTime[vertex.getId()] < endTime[w.getId()])
                classifyEdge(w, outgoingEdges, "Avanco");
            else
                classifyEdge(w, outgoingEdges, "Cruzamento");
        }
        endTime[vertex.getId()] = ++t;
        return t;
    }

    /**
     * Busca em profundidade (algoritmo de busca)
     * Nesta versão, foi utilizada uma abordagem iterativa. Foram usados como referência para construção desse metodo:
     * Graph.java: https://algs4.cs.princeton.edu/41graph/Graph.java.html
     * NonrecursiveDFS.java: https://algs4.cs.princeton.edu/41graph/NonrecursiveDFS.java.html
     * StackOverFlow: https://stackoverflow.com/questions/5278580/non-recursive-depth-first-search-algorithm
     *
     * @param startTime array com o tempo de inicio de cada vertice
     * @param endTime   array com o tempo de termino de cada vertice
     * @param vertex    raiz da busca
     */
    private static void iterativeDepthFirstSearch(int t, int[] startTime, int[] endTime, Vertex vertex, Graph graph) {

        boolean[] marked = new boolean[graph.getVertices().length];
        boolean[] finished = new boolean[graph.getVertices().length];
        Vertex[] father = new Vertex[graph.getVertices().length];
        Stack<Vertex> stack = new Stack<>();
        stack.push(vertex);

        while (!stack.isEmpty()) {

            Vertex v = stack.peek();

            if (!marked[v.getId()]) {
                startTime[v.getId()] = ++t;
                marked[v.getId()] = true;
            }

            Edge[] outgoingEdges = getOutgoingEdges(v, graph);
            sortAsStack(outgoingEdges);
            Vertex[] vertexSuccessors = getVertexSuccessors(outgoingEdges);
            Iterator<Vertex> iterator = Arrays.asList(vertexSuccessors).iterator();
            boolean allSuccessorsVisited = true;

            while (iterator.hasNext()) {
                Vertex w = iterator.next();
                if (!marked[w.getId()]) {
                    stack.push(w);
                    allSuccessorsVisited = false;
                    father[w.getId()] = v;
                }
            }

            if (allSuccessorsVisited) {
                stack.pop();
                endTime[v.getId()] = ++t;
                if (!finished[v.getId()]) {
                    finished[v.getId()] = true;
                    classifyEdges(v, outgoingEdges, startTime, endTime, father);
                }
            }
        }
    }

    /**
     * Classifica todas arestas divergentes de um vertice
     *
     * @param vertex        vertice de referencia
     * @param outgoingEdges arestas divergentes do vertice
     * @param startTime     tempo de descoberta
     * @param endTime       termo de termino
     * @param father        array que relacionada um vertice a outro como pai
     */
    private static void classifyEdges(Vertex vertex, Edge[] outgoingEdges, int[] startTime, int[] endTime, Vertex[] father) {
        for (Edge edge : outgoingEdges) {
            Vertex w = edge.getDestiny();
            if (father[w.getId()] == vertex) {
                classifyEdge(w, outgoingEdges, "Arvore");
            } else if (endTime[w.getId()] == 0) {
                classifyEdge(w, outgoingEdges, "Retorno");
            } else if (startTime[vertex.getId()] < endTime[w.getId()]) {
                classifyEdge(w, outgoingEdges, "Avanco");
            } else {
                classifyEdge(w, outgoingEdges, "Cruzamento");
            }
        }
    }

    /**
     * Obtem array de arestas que saem de um determinado vertice
     *
     * @param vertex vertice do grafo
     * @return lista de arestas que saem de um determinado vertice
     */
    private static Edge[] getOutgoingEdges(Vertex vertex, Graph graph) {

        int[] pointer = graph.getPointer();
        int startingPosition = pointer[vertex.getId()];
        int lastPosition = pointer[vertex.getId() + 1];
        Edge[] outgoingEdges = new Edge[lastPosition - startingPosition];

        int count = 0;
        for (int i = startingPosition; i < lastPosition; i++)
            outgoingEdges[count++] = graph.getEdges()[i];

        return outgoingEdges;
    }

    /**
     * Ordena um array lexicograficamente
     * Durante os meus testes, percebi que utilizar estruturas convencionais como Arrays.sort() eram mais pesadas
     * do que um metodo a parte
     *
     * @param outgoingEdges array de vizinhos do vertice ordenados
     */
    private static void sortAsArray(Edge[] outgoingEdges) {
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
     * Ordena uma pilha lexicograficamente inversa, para que o topo da pilha seja o menor elemento
     * Estrategia contraria ao metodo `sortAsArray()`
     *
     * @param outgoingEdges array de vizinhos do vertice ordenados
     */
    private static void sortAsStack(Edge[] outgoingEdges) {
        for (int i = 0; i < outgoingEdges.length - 1; i++) {
            for (int j = 0; j < outgoingEdges.length - i - 1; j++) {
                if (outgoingEdges[j].getDestiny().getId() < outgoingEdges[j + 1].getDestiny().getId()) {
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
    private static void classifyEdge(Vertex vertex, Edge[] outgoingEdges, String classification) {
        Arrays.asList(outgoingEdges).forEach(edge -> {
            if (edge.getDestiny().getId() == vertex.getId())
                edge.setClassification(classification);
        });
    }

    /**
     * Obtem array de vertices vizinhos a um determinado vertice
     *
     * @param outgoingEdges lista de arestas que saem de um determinado vertice
     * @return array de vertices vizinhos a um determinado vertice
     */
    private static Vertex[] getVertexSuccessors(Edge[] outgoingEdges) {
        Vertex[] vertexSuccessors = new Vertex[outgoingEdges.length];
        for (int i = 0; i < vertexSuccessors.length; i++)
            vertexSuccessors[i] = outgoingEdges[i].getDestiny();
        return vertexSuccessors;
    }
}

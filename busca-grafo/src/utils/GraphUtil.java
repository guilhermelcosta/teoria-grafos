package utils;

import entities.Edge;
import entities.Graph;
import entities.Vertex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GraphUtil {

    /**
     * Constroi o grafo a partir do arquivo fornecido
     *
     * @param filepath caminho do arquivo com as arestas do grafo
     * @return grafo construido
     * @throws IOException lanca excecao caso ocorra um erro de leitura com o arquivo
     */
    public static Graph buildGraph(String filepath) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
        String input = bufferedReader.readLine();
        Vertex[] vertices = new Vertex[Integer.parseInt(input.trim().split(" ")[0]) + 2];
        Edge[] edges = new Edge[Integer.parseInt(input.trim().split(" ")[2]) + 1];
        int vertexPos = 1, edgePos = 1;

        System.out.println("Construindo grafo...");

        while ((input = bufferedReader.readLine()) != null) {

            String[] arrInput = input.trim().split(" ");
            Vertex origin = new Vertex(Integer.parseInt(arrInput[0]));
            Vertex destiny = new Vertex(Integer.parseInt(arrInput[arrInput.length - 1]));

            if (!hasVertex(vertices, origin))
                vertices[vertexPos++] = origin;

            edges[edgePos++] = new Edge(origin, destiny);
        }
        System.out.println("Grafo construido! \n---");
        return new Graph(vertices, edges);
    }

    /**
     * Verifica se um vertice esta presente em um array
     *
     * @param vertices      lista de vertices
     * @param currentVertex vertice a ser verificado
     * @return boolean indicando se o vertice esta ou nao no array
     */
    private static boolean hasVertex(Vertex[] vertices, Vertex currentVertex) {
        for (int i = 1; i < vertices.length; i++)
            if (vertices[i] != null && vertices[i].getId() == currentVertex.getId())
                return true;
        return false;
    }
}

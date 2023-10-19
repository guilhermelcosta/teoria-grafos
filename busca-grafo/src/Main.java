import entities.Edge;
import entities.Graph;
import entities.Vertex;
import utils.GraphUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
Aluno: Guilherme Lage da Costa
Matricula: 792939
Data: 22 de outubro de 2023
Repositorio: https://github.com/guilhermelcosta/teoria-grafos
*/

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        List<Vertex> vertices = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        GraphUtil.buildGraph(vertices, edges, "graphs/graph-test-100.txt");
        Graph graph = new Graph(vertices, edges);
        graph.dfs();

        System.out.print("Digite o vértice para verificar classificação de suas arestas divergentes: ");
        int id = scanner.nextInt();

        graph.getEdges().forEach(edge -> {
            if (edge.getOrigin().getId() == id) {
                System.out.println();
                System.out.println("Origem: " + edge.getOrigin().getId());
                System.out.println("Destino: " + edge.getDestiny().getId());
                System.out.println("Classificacao: " + edge.getClassification());
            }
        });
    }
}

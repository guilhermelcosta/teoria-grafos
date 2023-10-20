import entities.Graph;
import utils.GraphUtil;

import java.io.IOException;
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
        Graph graph = GraphUtil.buildGraph("graphs/graph-test-100.txt");
        long start = System.currentTimeMillis();
        graph.dfs();

        System.out.println("Tempo de execucao -> " + (System.currentTimeMillis() - start) + "ms");
        System.out.print("Digite o vértice para verificar classificação de suas arestas divergentes: ");
        int id = scanner.nextInt();
        int[] pointer = graph.getPointer();
        int startingPosition = pointer[id];
        int lastPosition = pointer[id + 1];

        for (int i = startingPosition; i < lastPosition; i++)
            graph.getEdges()[i].print();

    }
}

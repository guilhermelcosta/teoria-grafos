import entities.Edge;
import entities.Graph;
import entities.Vertex;
import utils.GraphUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*Todos os codigo contidos neste programa foram desenvolvidos por Guilherme Costa*/
/*Aluno: Guilherme Lage da Costa*/
/*Matricula: 792939*/
/*Data: 22 de outubro de 2023*/
/*Repositorio: https://github.com/guilhermelcosta/teoria-grafos*/

public class Main {
    public static void main(String[] args) throws IOException {

        List<Vertex> vertices = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        GraphUtil.buildGraph(vertices, edges);
        Graph graph = new Graph(vertices, edges);
        graph.dfsAux();
        graph.getVertices().forEach(System.out::println);


    }
}

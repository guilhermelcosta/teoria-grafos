import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        String filepath = "forks/graph-test-50000.txt";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
        String input = bufferedReader.readLine();
        int vertices = Integer.parseInt(input.trim().split(" ")[0]);
        int edges = Integer.parseInt(input.trim().split(" ")[2]);
        int[] origin = new int[edges + 1];
        int[] destiny = new int[edges + 1];
        int[] pointer = new int[vertices + 2];

        int position = 1;
        while ((input = bufferedReader.readLine()) != null) {
            String[] arrInput = input.trim().split(" ");
            origin[position] = Integer.parseInt(arrInput[0]);
            destiny[position] = Integer.parseInt(arrInput[arrInput.length - 1]);
            position++;
        }

//      Compacta o array origin
        pointer[1] = 1;
        for (int i = 2; i < pointer.length - 2; i++) {
            pointer[i] = getOriginPosition(i, origin);
        }
        pointer[pointer.length - 2] = edges + 1;

        System.out.print("Informe o vertice: ");
        int vertex = scanner.nextInt();
        System.out.println("I   - Grau de saida do vertice");
        System.out.println("II  - Grau de entrada do vertice");
        System.out.println("III - Conjunto de sucessores do vertice");
        System.out.println("IV  - Conjunto de predecessores do vertice");
        System.out.print("Digite a operacao (I, II, III ou IV): ");
        String op = scanner.next();

        switch (op) {
            case "I" -> {
                System.out.println("Grau de saida: " + getVertexOutputDegree(vertex, pointer));
            }
            default -> throw new Exception("Opcao invalida!");
        }


    }

    public static int getOriginPosition(int i, int[] origin) {
        int count = 0;
        while (origin[count] != i) {
            count++;
        }
        return count;
    }

    public static int getVertexOutputDegree(int vertex, int[] pointer) {
        return pointer[vertex + 1] - pointer[vertex];
    }

    public static void printFork(int[] pointer, int[] destiny, int edges) {
//      Impressao dos vertices
        for (int i = 1; i < pointer.length - 1; i++) {
            System.out.printf("[%3d]", pointer[i]);
//          System.out.printf("%3d => [%3d]\n", i, pointer[i]);
        }

//      Impressao das arestas
        System.out.println();
        for (int i = 1; i <= edges; i++) {
            System.out.printf("[%3d]", destiny[i]);
        }
    }
}

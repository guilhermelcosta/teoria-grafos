import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
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
        String op = scanner.next().toUpperCase();

        switch (op) {
            case "I" -> {
                System.out.println("Grau de saida: " + getVertexOutputDegree(vertex, pointer));
            }
            case "II" -> {
                System.out.println("Grau de entrada: " + getVertexInputDegree(vertex, destiny));
            }
            default -> throw new Exception("Opcao invalida!");
        }
    }

    /**
     * Retorna a posicao inicial de um vertice no array origin
     *
     * @param i      posicao inicial de busca, comecando em 2, pois a posicao 0 e descartada e 1 pre definida como 1
     * @param origin array de busca
     * @return posicao inicial de um vertice no array origin
     */
    public static int getOriginPosition(int i, int[] origin) {
        int count = 0;
        while (origin[count] != i) {
            count++;
        }
        return count;
    }

    /**
     * Obtem o grau de saida de um determinado vertice
     *
     * @param vertex  vertice buscado
     * @param pointer array que aponta para a posicao inicial do vertice no array destiny
     * @return grau de saida de um determinado vertice
     */
    public static int getVertexOutputDegree(int vertex, int[] pointer) {
        return pointer[vertex + 1] - pointer[vertex];
    }

    /**
     * Obtem o grau de entrada de um determinado vertice
     *
     * @param vertex  vertice buscado
     * @param destiny array de destino de cada arco
     * @return grau de entrada de um determinado vertice
     */
    public static int getVertexInputDegree(int vertex, int[] destiny) {

        int vertexInputDegree = 0, position = 0;
        // Faz uma copia do array destiny, para nao modificar os valores originais
        int[] sortedDestiny = Arrays.copyOf(destiny, destiny.length);
        // Para ma
        quickSort(sortedDestiny, 1, sortedDestiny.length - 1);

        while (sortedDestiny[position] <= vertex) {
            if (sortedDestiny[position] == vertex)
                vertexInputDegree++;
            position++;
        }

        return vertexInputDegree;
    }

    /**
     * Imprime os arrays pointer e destiny
     *
     * @param pointer array apontador para destiny
     * @param destiny array que indica os vertices de destino
     * @param edges   quantidade de arestas
     */
    public static void printFork(int[] pointer, int[] destiny, int edges) {
        for (int i = 1; i < pointer.length - 1; i++)
            System.out.printf("[%3d]", pointer[i]);

        System.out.println();

        for (int i = 1; i <= edges; i++)
            System.out.printf("[%3d]", destiny[i]);
    }

    /**
     * Ordenacao por quicksort
     *
     * @param arr  array a ser ordenado
     * @param low  posicao inicial de ordenacao
     * @param high posicao final de ordenacao
     */
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    /**
     * Cria uma nova particao a ser ordenada pelo metodo quicksort
     *
     * @param arr  array a ser ordenado
     * @param low  posicao inicial de ordenacao
     * @param high posicao final de ordenacao
     * @return novo indice do pivo
     */
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    /**
     * Troca duas posicoes e define o novo pivo
     *
     * @param arr array a ser ordenado
     * @param i   posicao atual da particao
     * @param j   posicao atual do no array
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

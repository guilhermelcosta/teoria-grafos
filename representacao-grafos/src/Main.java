import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/*Todos os codigo contidos neste programa foram desenvolvidos por Guilherme Costa*/
/*Para ajustes pontuais, foi utilizado o AI Assistant do IntelliJ*/
/*Aluno: Guilherme Lage da Costa*/
/*Matricula: 792939*/
/*Data de entrega da atividade: 10/09/2023*/
/*Repositorio: https://github.com/guilhermelcosta/teoria-grafos*/

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        String filepath = "graphs/graph-test-50000.txt";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
        String input = bufferedReader.readLine();
        int vertices = Integer.parseInt(input.trim().split(" ")[0]);
        int edges = Integer.parseInt(input.trim().split(" ")[2]);
        int[] origin = new int[edges + 1];
        int[] destiny = new int[edges + 1];
        int[] pointer = new int[vertices + 2];

        fillOriginAndDestinyArrays(bufferedReader, origin, destiny);
        createPointer(pointer, origin, edges);

        System.out.print("Informe o vertice: ");
        int vertex = scanner.nextInt();
        System.out.println("I   - Grau de saida do vertice");
        System.out.println("II  - Grau de entrada do vertice");
        System.out.println("III - Conjunto de sucessores do vertice");
        System.out.println("IV  - Conjunto de predecessores do vertice");
        System.out.print("Digite a operacao (I, II, III ou IV): ");
        String op = scanner.next().toUpperCase();

        switch (op) {
            case "I" -> System.out.println("Grau de saida: " + getVertexOutputDegree(vertex, pointer));
            case "II" -> System.out.println("Grau de entrada: " + getVertexInputDegree(vertex, destiny));
            case "III" -> System.out.println("Conjunto de sucessores: " + Arrays.toString(getVertexSuccessors(vertex, pointer, destiny)));
            case "IV" -> System.out.println("Conjunto de predecessores: " + Arrays.toString(getVertexPredecessors(vertex, pointer, origin, destiny)));
            default -> throw new Exception("Opcao invalida!");
        }
    }

    /**
     * Insere os vertices do arquivo indicado em 'filepath' nos arrays origin e destiny
     *
     * @param bufferedReader bufferedReader com o filepath indicado
     * @param origin         array com os vertices de origem
     * @param destiny        array com os vertices de destino
     * @throws IOException excecao do bufferedReader caso nao tenha mais linhas para serem lidas
     */
    public static void fillOriginAndDestinyArrays(BufferedReader bufferedReader, int[] origin, int[] destiny) throws IOException {

        String input;
        int position = 1;

        while ((input = bufferedReader.readLine()) != null) {
            String[] arrInput = input.trim().split(" ");
            origin[position] = Integer.parseInt(arrInput[0]);
            destiny[position] = Integer.parseInt(arrInput[arrInput.length - 1]);
            position++;
        }
    }

    /**
     * Cria o array 'pointer', que aponta para as posicoes inicias de cada vertice em 'origin'
     *
     * @param pointer array que aponta para as posicoes inicias de cada vertice em 'origin'
     * @param origin  array com os vertices de origem
     * @param edges   numero de arestas do grafo
     */
    public static void createPointer(int[] pointer, int[] origin, int edges) {
        pointer[1] = 1;
        for (int i = 2; i < pointer.length - 1; i++) {
            pointer[i] = getOriginPosition(i, origin);
        }
        pointer[pointer.length - 1] = edges + 1;
    }

    /**
     * Retorna a primeira posicao na qual um vertice e verificado no array origin
     *
     * @param i      posicao busca
     * @param origin array com os vertices de origem
     * @return posicao inicial de um vertice no array origin
     */
    private static int getOriginPosition(int i, int[] origin) {
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
     * @param pointer array que aponta para as posicoes inicias de cada vertice em 'origin'
     * @return grau de saida de um determinado vertice
     */
    public static int getVertexOutputDegree(int vertex, int[] pointer) {
        return pointer[vertex + 1] - pointer[vertex];
    }

    /**
     * Obtem o grau de entrada de um determinado vertice
     *
     * @param vertex  vertice buscado
     * @param destiny array com os vertices de destino
     * @return grau de entrada de um determinado vertice
     */
    public static int getVertexInputDegree(int vertex, int[] destiny) {

//        Para nao modificar o array 'destiny' original durante o quicksort, optei por criar uma copia
        int vertexInputDegree = 0;
        int[] sortedDestiny = Arrays.copyOf(destiny, destiny.length);

        quickSort(sortedDestiny, 1, sortedDestiny.length - 1);

        for (int j : sortedDestiny)
            if (j == vertex)
                vertexInputDegree++;

        return vertexInputDegree;
    }

    /**
     * Retorna o conjunto de sucessores de um determinado vertice
     *
     * @param vertex  vertice buscado
     * @param pointer array que aponta para as posicoes inicias de cada vertice em 'origin'
     * @param destiny array com os vertices de destino
     * @return conjunto de sucessores de um determinado vertice
     */
    public static int[] getVertexSuccessors(int vertex, int[] pointer, int[] destiny) {

        int[] successors = new int[getVertexOutputDegree(vertex, pointer)];
        int startingPosition = pointer[vertex];

        for (int i = 0; i < successors.length; i++)
            successors[i] = destiny[startingPosition++];

        return successors;
    }

    /**
     * Retorna o conjunto de predecessores de um determinado vertice
     *
     * @param vertex  vertice buscado
     * @param pointer array que aponta para as posicoes inicias de cada vertice em 'origin'
     * @param origin  array com os vertices de origem
     * @param destiny array com os vertices de destino
     * @return conjunto de sucessores de um determinado vertice
     */
    public static int[] getVertexPredecessors(int vertex, int[] pointer, int[] origin, int[] destiny) {

        int inputDegree = getVertexInputDegree(vertex, destiny);
        int[] predecessors = new int[inputDegree];
        int predecessorsCount = 0;

        for (int i = 1; i < pointer.length - 1; i++) {
//            Se ja tiver encontrado a quantidade de predecessores previsto, finaliza o loop
            if (predecessorsCount == inputDegree)
                break;
//            Essa verificacao so pode ser inserida, pois o grafo e simples
//            Caso ele nao fosse simples, e tivesse ciclos, todos os vertices deveriam ser verificadas
            if (i == vertex)
                continue;

            int nextPosition = pointer[i];
            int lastPosition = pointer[i + 1];

            for (int j = nextPosition; j < lastPosition; j++) {
                if (destiny[j] == vertex) {
                    predecessors[predecessorsCount++] = origin[j];
//                    Uma vez que nao existem ciclos no grafo, um vertice nao pode ser predecessor mais de uma vez de outro
                    break;
                }
            }
        }
        return predecessors;
    }

    /**
     * Imprime os arrays pointer e destiny
     *
     * @param pointer array que aponta para as posicoes inicias de cada vertice em 'origin'
     * @param destiny array com os vertices de destino
     * @param edges   numero de arestas do grafo
     */
    public static void printGraph(int[] pointer, int[] destiny, int edges) {
        for (int i = 1; i < pointer.length - 1; i++)
            System.out.printf("[%3d]", pointer[i]);

        System.out.println();

        for (int i = 1; i <= edges; i++)
            System.out.printf("[%3d]", destiny[i]);
    }

    /**
     * Realiza a ordenacao de um array por Quicksort
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
     * Cria uma nova particao a ser ordenada pelo metodo
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
     * @param i   posicao atual da verificada pelo quicksort
     * @param j   posicao atual da particao
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

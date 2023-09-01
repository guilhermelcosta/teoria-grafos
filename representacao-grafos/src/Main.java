import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        String filepath = "forks/graph-test-100.txt";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
        String input = bufferedReader.readLine();
        byte vertices = Byte.parseByte(input.trim().split(" ")[0]);
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

        pointer[1] = 1;
        for (int i = 2; i < pointer.length - 2; i++) {
            pointer[i] = getPosition(i, origin);
        }
        pointer[pointer.length - 2] = edges + 1;

        for (int i = 1; i < pointer.length - 1; i++) {
//            System.out.printf("%3d => [%3d]\n", i, pointer[i]);
            System.out.printf("[%3d]", pointer[i]);
        }

        System.out.println();
        for (int i = 1; i <= edges; i++) {
//            System.out.println();
//            System.out.printf("[%3d]", origin[i]);
            System.out.printf("[%3d]", destiny[i]);
        }

    }

    public static int getPosition(int i, int[] origin) {
        int count = 0;
        while (origin[count] != i) {
            count++;
        }
        return count;
    }
}

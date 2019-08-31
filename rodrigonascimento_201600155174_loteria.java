import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

class Person {

    public String name;
    public int[] numbers;
    public int score;

    public Person(String name) {

        this.name = name;
        this.numbers = new int[15];
        this.score = 0;
    } 
}

public class rodrigonascimento_201600155174_loteria {

    private static int NUM_OF_WINNING_NUMBERS = 10;
    private static int NUM_OF_BET_NUMBERS = 15;

    /**
     * Generates a person's score
     * 
     * @param person            Person whose score will be generated.
     * @param winningNumbers    Numbers that won the lottery.
     */
    private static void generateScore(Person person, int[] winningNumbers) {

        for (int i = 0; i < NUM_OF_BET_NUMBERS; i++) {
            for (int j = 0; j < NUM_OF_WINNING_NUMBERS; j++) {

                if (person.numbers[i] == winningNumbers[j])
                    person.score++;
            }
        }
    }

    /**
     * Makes a person out of a String.
     * 
     * @param inputLine String with the person's info.
     * @return          Person with the input info.
     */
    private static Person makePerson(String inputLine) {

        String[] info = inputLine.split(" ");
        Person person = new Person(info[0]);
        for (int i = 0; i < 15; i++)
            person.numbers[i] = Integer.parseInt(info[i + 1]);
        return person;
    }

    /**
     * Gets the index of the left child of a heap tree.
     * 
     * @param index Index of the root node.
     * @return      Index of the left child.
     */
    private static int getLeft(int index) {
        return 2 * index + 1;
    }

    /**
     * Gets the index of the right child of a heap tree.
     * 
     * @param index Index of the root node.
     * @return      Index of the right child.
     */
    private static int getRight(int index) {
        return 2 * index + 2;
    }

    /**
     * Gets last root of the tree.
     * 
     * @param size  Number of elements in the tree.
     * @return      Index of the last root.
     */
    private static int getLastRoot(int size) {
        return ((size - 1) - 1) / 2;
    }

    /**
     * Swaps two elements of an array.
     * 
     * @param array         Array to have it's elements swaped.
     * @param firstIndex    First index to be swaped.
     * @param secondInex    Second index to be swaped.
     */
    private static void swap(Person[] array, int firstIndex, int secondInex) {

        Person aux = array[firstIndex];
        array[firstIndex] = array[secondInex];
        array[secondInex] = aux;
    }

    /**
     * Turns a binary tree into a max heap.
     * 
     * @param array Tree to be heapfied.
     * @param index Target index.
     * @return      Heapified array.
     */
    private static Person[] heapifyMax(Person[] array, int index) {

        int root = index;
        int left = getLeft(index);
        int right = getRight(index);
        if (left < array.length && array[left].score > array[root].score)
            root = left;
        if (right < array.length && array[right].score > array[root].score)
            root = right;
        if (root != index) {
            swap(array, root, index);
            array = heapifyMax(array, root);
        }

        return array;
    }

    /**
     * Turns a binary tree into a max heap.
     * 
     * @param array Tree to be heapfied.
     * @param index Target index.
     * @return      Heapified array.
     */
    private static Person[] heapifyMin(Person[] array, int index) {

        int root = index;
        int left = getLeft(index);
        int right = getRight(index);
        if (left < array.length && array[left].score < array[root].score)
            root = left;
        if (right < array.length && array[right].score < array[root].score)
            root = right;
        if (root != index) {
            swap(array, root, index);
            array = heapifyMin(array, root);
        }

        return array;
    }

    /**
     * Gets the  names of the gamblers with the highest score.
     * 
     * @param gamblers      List of all the gamblers.
     * @param rootIndex     Root of the subtree to be searched.
     * @param winnersNames  Names of all the winners.
     */
    private static void getWinners(Person[] gamblers, int rootIndex, StringBuilder winnersNames) {

        int left = getLeft(rootIndex);
        int right = getRight(rootIndex);

        if (winnersNames.length() > 0)
            winnersNames.append('\n' + gamblers[rootIndex].name);
        else
            winnersNames.append(gamblers[rootIndex].name);

        // Recurs down the tree
        if (left < gamblers.length && gamblers[rootIndex].score == gamblers[left].score)
            getWinners(gamblers, left, winnersNames);
        if (right < gamblers.length && gamblers[rootIndex].score == gamblers[right].score)
            getWinners(gamblers, right, winnersNames);
    }

    /**
     * Writes content to file.
     * 
     * @param fileName  Name of the file (with extension) to be writen.
     * @param content   Content to be writen on the file.
     * @throws FileNotFoundException
     */
    private static void writeToFile(String fileName, String content) throws FileNotFoundException {

        try(FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.print(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try (FileInputStream inputStream = new FileInputStream(args[0])) {
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    
            int prize;
            int numOfGamblers;
            int[] winningNumbers = new int[NUM_OF_WINNING_NUMBERS];

            // Reads the input
            prize = Integer.parseInt( reader.readLine() );
            numOfGamblers = Integer.parseInt(reader.readLine());
            String[] temp = reader.readLine().split(" ");

            for (int i = 0; i < NUM_OF_WINNING_NUMBERS; i++)
                winningNumbers[i] = Integer.parseInt(temp[i]);

            Person[] gamblers = new Person[numOfGamblers];

            for (int i = 0; i < numOfGamblers; i++)
                gamblers[i] = makePerson(reader.readLine());

            for (int i = 0; i < numOfGamblers; i++)
                generateScore(gamblers[i], winningNumbers);

            int lastRootIndex = getLastRoot(gamblers.length);

            // Turns the array into a max heap
            for (int i = lastRootIndex; i >= 0; i--)
                gamblers = heapifyMax(gamblers, i);

            // Gets the names of the winners with the greatest score
            StringBuilder topWinnersNames = new StringBuilder();
            getWinners(gamblers, 0, topWinnersNames);

            // Turns the array into a min heap
            for (int i = lastRootIndex; i >= 0; i--)
                gamblers = heapifyMin(gamblers, i);

            // Gets the names of the winners with the smallest score
            StringBuilder bottomWinnersNames = new StringBuilder();
            getWinners(gamblers, 0, bottomWinnersNames);
    
        } catch (Exception ex) {
            ex.printStackTrace();
        }    
    }
}
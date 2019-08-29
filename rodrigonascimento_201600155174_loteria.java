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
    
        } catch (Exception ex) {
            ex.printStackTrace();
        }    
    }
}
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Access the 2D array from ArrayHolder
        String[][] surveyQuestions = QuestionsArray.QuestionsArray;

        // Assign each question a specific point value; the order of this array is based on the order of the questions
        int[] questionValues = {5, 2, 5, 3, 4, 8};

        // Create variables to represent each of the 4 parties in the survey, using int for score keeping
        int republicanScore = 0;
        int democraticScore = 0;
        int libertarianScore = 0;
        int greenScore = 0;

        // Prompt user to answer survey questions, declare boolean for loop
        boolean surveyComplete = false;
        // initialize scanner for input
        Scanner userScan = new Scanner(System.in);
        // declare String for user's response input
        String userResponse;

        // LinkedHashMap for answer & question storage & to have it in ascending order
        Map<String, String> questionAndChoice = new LinkedHashMap<>();

        // Welcome message
        System.out.println("Welcome\n Let's see if we can guess your political party!\n");

        // Begin loop for survey, questions will come as the user inputs an answer to make it easier to read and understand the questions
        while(!surveyComplete) {
            for(int i = 0; i < surveyQuestions.length; i++) {
                String question = surveyQuestions[i][0];
                System.out.print("Question " + question);

                for(int j = 1; j < surveyQuestions[i].length; j++) {
                    String answer = surveyQuestions[i][j];
                    System.out.print(answer);
                }

                // Gather user's response from the multiple choice options, ignoring case sensitivity
                userResponse = userScan.nextLine().trim().toUpperCase();

                if (userResponse.matches("[A-Da-d](\\.)?")) {

                    // Process the user's response to make sure it is valid
                    if (userResponse.equalsIgnoreCase("A") || userResponse.equalsIgnoreCase("A.")) {
                        republicanScore += questionValues[i];
                        userResponse = surveyQuestions[i][1];
                        System.out.println("User selected " + surveyQuestions[i][1]);
                    } else if (userResponse.equalsIgnoreCase("B") || userResponse.equalsIgnoreCase("B.")) {
                        democraticScore += questionValues[i];
                        userResponse = surveyQuestions[i][2];
                        System.out.println("User selected: " + surveyQuestions[i][2]);
                    } else if (userResponse.equalsIgnoreCase("C") || userResponse.equalsIgnoreCase("C.")) {
                        libertarianScore += questionValues[i];
                        userResponse = surveyQuestions[i][3];
                        System.out.println("User selected: " + surveyQuestions[i][3]);
                    } else if (userResponse.equalsIgnoreCase("D") || userResponse.equalsIgnoreCase("D.")) {
                        greenScore += questionValues[i];
                        userResponse = surveyQuestions[i][4];
                        System.out.println("User selected: " + surveyQuestions[i][4]);
                    }

                    // Store questions and selected answers to the LinkedHashMap
                    int selectedQuestionIndex = i;
                    String selectedQuestion = surveyQuestions[selectedQuestionIndex][0];
                    String selectedChoice = surveyQuestions[selectedQuestionIndex][userResponse.charAt(0) - 'A' + 1];
                    questionAndChoice.put(selectedQuestion, selectedChoice);
                    surveyComplete = true;

                    // If user does not provide a valid answer, it will repeat the question so the user can enter a valid choice
                } else {
                    System.out.println("That is not a valid answer.\n Please enter the letter from the multiple choice selection.\n");
                    i--;
                }
            }
        }

        // Create HashMap to link score results to the party's name to help print out the winner once score is tallied up
        Map<Integer, String> partyResult = new HashMap<>();
        partyResult.put(republicanScore, "Republican Party");
        partyResult.put(democraticScore, "Democratic Party");
        partyResult.put(libertarianScore, "Libertarian Party");
        partyResult.put(greenScore, "Green Party");

        // Assign int value to capture the final score of the party with the most points scored using Math.max
        int partyWinner = Math.max(Math.max(republicanScore,democraticScore),Math.max(libertarianScore,greenScore));
        // assign the value to a String
        String winningPartyName = partyResult.get(partyWinner);
        // Print out final answer that determines which party the user is affiliated with
        System.out.println("Based on your answers, you are most likely affiliated with the " + winningPartyName);

        // Print out all the score totals for extra information
        System.out.println("Score for Republican Party is " + republicanScore + ".");
        System.out.println("Score for Democratic Party is " + democraticScore + ".");
        System.out.println("Score for Libertarian Party is " + libertarianScore + ".");
        System.out.println("Score for Green Party is " + greenScore + ".");

        // Save each set of responses in its own text file. Make a file and file writer for each party, whichever party has the highest score will have the responses saved in that text file
        File outputFile = new File(winningPartyName + ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            // Write responses to the winner's text file
            for(Map.Entry<String, String> entry : questionAndChoice.entrySet()){
                String selectedQuestion = entry.getKey();
                String selectedChoice = entry.getValue();

                writer.write(selectedQuestion);
                writer.write(selectedChoice);
                writer.newLine();
            }

            // Flush and close writer to write data successfully, catch if error
            writer.flush();
        } catch (IOException e) {
            System.out.println("Error occurred while writing to the file: " + e.getMessage());
        }


    }
}

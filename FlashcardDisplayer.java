import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
  *@author Thando Tsela
  */

  public class FlashcardDisplayer {

      private FlashcardPriorityQueue flashcards;

      /**
      * Creates a flashcard displayer with the flashcards in file.
      * File has one flashcard per line. On each line, the date the flashcard
      * should next be shown is first (format: YYYY-MM-DDTHH-MM), followed by a tab,
      * followed by the text for the front of the flashcard, followed by another tab.
      * followed by the text for the back of the flashcard. You can assume that the
      * front/back text does not itself contain tabs. (I.e., a properly formatted file
      * has exactly 2 tabs per line.)
      * The time may be more precise (e.g., seconds may be included). The parse method
      * in LocalDateTime can deal with this situation without any changes to your code.
      */
      public FlashcardDisplayer(String filePath) {
          if (filePath != null) {
              flashcards = new FlashcardPriorityQueue();
              Scanner fileData = null;
              try {
                  fileData = new Scanner(new File(filePath));
                  while (fileData.hasNextLine()) {
                          String line = fileData.nextLine();
                          String[] splitted = line.split("\t");
                          String dateTime = splitted[0].trim();
                          String front = splitted[1].trim();
                          String back = splitted[2].trim();
                          Flashcard flashcard = new Flashcard(dateTime, front, back);
                          flashcards.add(flashcard);
                      }
              }
              catch (FileNotFoundException e) {
                  System.out.println("File not found.");
              }
          } //End if statement
          else {
              System.out.println("Please input a filepath.");
          }
      }

      /**
      * Writes out all flashcards to a file so that they can be loaded
      * by the FlashcardDisplayer(String filePath) constructor. Returns true
      * if the file could be written. The FlashcardDisplayer should still
      * have all of the same flashcards after this method is called as it
      * did before the method was called. However, flashcards with the same
      * exact same next display date may later be displayed in a different order.
      */
      public boolean saveFlashcards(String outFilePath) {
          boolean done = true;
          FlashcardPriorityQueue copyQueue = new FlashcardPriorityQueue();
          PrintWriter toFile = null;
          try {
              toFile = new PrintWriter(outFilePath);

              while (!flashcards.isEmpty()) {
                  Flashcard copiedCard = flashcards.poll();
                  toFile.print(copiedCard + "\n");
                  copyQueue.add(copiedCard);
              }
              toFile.close();
              flashcards = copyQueue;
          }
          catch (FileNotFoundException e) {
              done = false; // Error opening the file
          }

          return done;
      }

      /**
      * Displays any flashcards that are currently due to the user, and
      * asks them to report whether they got each card correct. If the
      * card was correct (if the user entered "1"), it is added back to the
      * deck of cards with a new due date that is one day later than the current
      * date and time; if the card was incorrect (if the user entered "2"), it is
      * added back to the card with a new due date that is one minute later than
      * that the current date and time.
      */
      public void displayFlashcards() {
          LocalDateTime currentTime = LocalDateTime.now();
          Scanner scanner = new Scanner(System.in);
          String guess = "";

          while (flashcards.peek().getDueDate().compareTo(currentTime) <= 0) {
              Flashcard currentCard = flashcards.poll();
              System.out.println(currentCard.getFrontText());
              System.out.println("[Press return for back of card]");
              String enter = scanner.nextLine();
              System.out.println(currentCard.getBackText());
              System.out.println("Press 1 if you got the card correct and 2 if you got the card incorrect.");
              guess = scanner.nextLine();
              if (guess.equals("1")) {
                  LocalDateTime updatedTime = currentCard.getDueDate().minusDays(-1);
                  String stringTime = updatedTime.toString();
                  Flashcard newFlashcard = new Flashcard(stringTime, currentCard.getFrontText(),currentCard.getBackText());
                  flashcards.add(newFlashcard);
              }
              else if (guess.equals("2")) {
                  LocalDateTime updatedTime = currentCard.getDueDate().minusMinutes(-1);
                  String stringTime = updatedTime.toString();
                  Flashcard newFlashcard = new Flashcard(stringTime, currentCard.getFrontText(),currentCard.getBackText());
                  flashcards.add(newFlashcard);
              }
          }
          System.out.println("No cards are waiting to be studied!");

      }

      private void playGame() {
          Scanner scanner = new Scanner(System.in);
          System.out.println("Enter a command: ");
          String input = scanner.nextLine();
          if (input.equals("quiz")) {
              System.out.println("Card: ");
              displayFlashcards();
              playGame();
          }
          else if (input.equals("save")) {
              System.out.println("Type a filename where you'd like to save the flashcards: ");
              String outFilePath = scanner.nextLine();
              saveFlashcards(outFilePath);
              playGame();
          }
          else if (input.equals("exit")) {
              System.out.println("Goodbye!");
              System.exit(0);
          }
      }

      public static void main(String[] args) throws FileNotFoundException {
          if (args[0].equals(null)) {
              System.out.println("Please enter a filepath.");
              System.exit(0);
          }
          System.out.println("Time to practice flashcards! The computer will display your flashcards, \n"
                            + "you generate the response in your head, and then see if you got it right. \n"
                            + "The computer will show you cards that you miss more often than those you know!");
          FlashcardDisplayer test = new FlashcardDisplayer(args[0]);
          test.playGame();
      }

  }

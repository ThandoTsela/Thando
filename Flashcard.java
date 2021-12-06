import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 *@author Thando Tsela
 */
 public class Flashcard implements Comparable<Flashcard>
 {
     private LocalDateTime dueDate;
     private String frontText;
     private String backText;

     public int compareTo(Flashcard flashcard) { //returns NEGATIVE if dueDate has passed
         LocalDateTime flashcardDueDate = flashcard.getDueDate();
         return dueDate.compareTo(flashcardDueDate);
     } //end compareTo

    /**
     * Creates a new flashcard with the given dueDate, text for the front
     * of the card (front), and text for the back of the card (back).
     * dueDate must be in the format YYYY-MM-DDTHH:MM. For example,
     * 2019-11-04T13:03 represents 1:03PM on November 4, 2019. It's
     * okay if this method crashes if the date format is incorrect.
     * In the format above, the time may be more precise (e.g., seconds
     * or milliseconds may be included). The parse method in LocalDateTime
     * can deal with these situations without any changes to your code.
     */
    public Flashcard(String currentDueDate, String front, String back) {
        dueDate = LocalDateTime.parse(currentDueDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        frontText = front;
        backText = back;
    }

    /**
     * Gets the text for the front of this flashcard.
     */
    public String getFrontText() {
        return frontText;
    }

    /**
     * Gets the text for the Back of this flashcard.
     */
    public String getBackText() {
        return backText;
    }

    /**
     * Gets the time when this flashcard is next due.
     */
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public String toString() {
        return dueDate + "\t" + frontText + "\t" + backText;
    }

} // end Flashcard class

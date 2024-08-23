import java.util.ArrayList;
import java.util.List;

/**
 * The AbstractTextAligner class serves as an abstract base class for text aligners,
 * providing common functionalities useful for various types of text alignment.
 * Implementing classes should override methods to provide specific alignment logic.
 */
public abstract class AbstractTextAligner implements TextFormatter {

    /**
     * Takes a given paragraph and breaks it into multiple lines such that each line
     * adheres to the specified maximum line length. The method splits the paragraph
     * by spaces, then iteratively builds lines by appending words.
     *
     * Two conditions are checked:
     * 1) If adding a new word to the current line will perfectly fill the line to its max length.
     * 2) If adding a new word to the current line will exceed its max length.
     *
     * If either condition is met, the current line is saved and a new line starts.
     *
     * @param paragraph  The paragraph text to be broken into lines.
     * @param lineLength The maximum length of each line.
     * @return An array of lines obtained by breaking the original paragraph.
     */
    protected String[] breakIntoLines(String paragraph, int lineLength) {
        List<String> lines = new ArrayList<>();
        String[] words = paragraph.split(" ");
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            int additionalSpace = (line.length() > 0) ? 1 : 0;

            // Check if adding the new word will perfectly fill the line
            boolean willPerfectlyFill = (line.length() + word.length() + additionalSpace == lineLength);

            // Check if adding the new word will exceed the line length
            boolean willExceed = (line.length() + word.length() + additionalSpace > lineLength);

            if (willExceed && !willPerfectlyFill) {
                lines.add(line.toString().trim());
                line.setLength(0);  // Reset the StringBuilder for the new line
            }

            // Add a space if this isn't the first word in the line and the line has content
            if (additionalSpace > 0 && line.length() > 0) {
                line.append(" ");
            }

            line.append(word);

            // If the line is perfectly filled, add it to the list and reset the StringBuilder
            if (willPerfectlyFill) {
                lines.add(line.toString());
                line.setLength(0);
            }
        }

        // Handle any remaining content in the StringBuilder
        if (line.length() > 0) {
            lines.add(line.toString().trim());
        }

        return lines.toArray(new String[0]);
    }
}

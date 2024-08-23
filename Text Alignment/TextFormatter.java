/**
 * This interface defines the contract for text formatting classes.
 */
public interface TextFormatter {
    /**
     * Formats the given paragraphs according to a specific alignment and line length.
     *
     * @param paragraphs The paragraphs to format.
     * @param lineLength The maximum line length.
     * @return The formatted text.
     */
    String formatText(String[] paragraphs, int lineLength);
}
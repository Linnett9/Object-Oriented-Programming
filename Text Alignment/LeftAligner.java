/**
 * The LeftAligner class handles the text alignment for left-aligned text.
 * It extends the AbstractTextAligner class to inherit common text formatting functionalities.
 */
public class LeftAligner extends AbstractTextAligner {

    /**
     * Formats an array of paragraphs to be left-aligned, based on the given line length.
     * Each paragraph is broken down into lines of the specified length, then left-aligned.
     *
     * @param paragraphs An array of paragraphs to be left-aligned.
     * @param lineLength The maximum length for each line of text.
     * @return A string containing the left-aligned text with appropriate line breaks.
     */
    @Override
    public String formatText(String[] paragraphs, int lineLength) {
        StringBuilder alignedText = new StringBuilder();

        for (String paragraph : paragraphs) {
            String[] lines = breakIntoLines(paragraph, lineLength);
            for (String line : lines) {
                alignLeft(alignedText, line);
                alignedText.append("\n");
            }
        }
        return alignedText.toString();
    }

    /**
     * Appends a left-aligned paragraph to the StringBuilder object holding the aligned text.
     *
     * @param alignedText The StringBuilder object to which the paragraph is appended.
     * @param paragraph   The paragraph to be appended in a left-aligned manner.
     */
    private void alignLeft(StringBuilder alignedText, String paragraph) {
        alignedText.append(paragraph);
    }
}

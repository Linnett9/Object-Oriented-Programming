/**
 * The RightAligner class is responsible for aligning text to the right.
 * This class extends the AbstractTextAligner to inherit common text formatting utilities.
 */
public class RightAligner extends AbstractTextAligner {

    /**
     * Formats an array of paragraphs to be right-aligned according to a specified line length.
     * Each paragraph is split into lines of the given length and then aligned to the right.
     *
     * @param paragraphs An array of paragraphs to be right-aligned.
     * @param lineLength The maximum length for each line of text.
     * @return A string containing the right-aligned text, separated by appropriate line breaks.
     */
    @Override
    public String formatText(String[] paragraphs, int lineLength) {
        StringBuilder alignedText = new StringBuilder();

        for (String paragraph : paragraphs) {
            String[] lines = breakIntoLines(paragraph, lineLength);
            for (String line : lines) {
                alignRight(alignedText, line, lineLength);
                alignedText.append("\n");
            }
        }
        return alignedText.toString();
    }

    /**
     * Appends a right-aligned paragraph to the StringBuilder object that accumulates the aligned text.
     * Spaces are added at the beginning of the paragraph to align it to the right.
     *
     * @param alignedText The StringBuilder object to which the right-aligned paragraph is appended.
     * @param paragraph   The paragraph to be appended in a right-aligned manner.
     * @param lineLength  The maximum line length, used to calculate the padding.
     */
    private void alignRight(StringBuilder alignedText, String paragraph, int lineLength) {
        int padding = lineLength - paragraph.length();
        for (int i = 0; i < padding; i++) {
            alignedText.append(" ");
        }
        alignedText.append(paragraph);
    }
}

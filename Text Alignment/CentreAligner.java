/**
 * The CentreAligner class is responsible for center-aligning text.
 * It extends the AbstractTextAligner to inherit common text formatting functionalities.
 */
public class CentreAligner extends AbstractTextAligner {

    /**
     * Formats an array of paragraphs to be center-aligned, according to a given line length.
     * Each paragraph is broken into lines which are then individually center-aligned.
     *
     * @param paragraphs An array of paragraphs to format.
     * @param lineLength The maximum length of each line.
     * @return A string containing the center-aligned text.
     */
    @Override
    public String formatText(String[] paragraphs, int lineLength) {
        StringBuilder alignedText = new StringBuilder();

        for (String paragraph : paragraphs) {
            String[] lines = breakIntoLines(paragraph, lineLength);
            for (String line : lines) {
                // If the line is already at the maximum length, append as is
                if (line.length() == lineLength) {
                    alignedText.append(line);
                } else {
                    alignCentre(alignedText, line, lineLength);
                }
                alignedText.append("\n");
            }
        }
        return alignedText.toString();
    }

    /**
     * Aligns a single line of text to be center-aligned within a specified line length.
     * It pads the line with leading and trailing spaces to achieve center alignment.
     *
     * @param alignedText The StringBuilder where the aligned text will be appended.
     * @param line        The line of text to align.
     * @param lineLength  The maximum length of each line.
     */
    private void alignCentre(StringBuilder alignedText, String line, int lineLength) {
        int padding = (lineLength - line.length()) / 2;
        int extraPadding = (lineLength - line.length()) % 2;

        // Add leading spaces
        for (int i = 0; i < padding + extraPadding; i++) {
            alignedText.append(" ");
        }

        // Add the line content
        alignedText.append(line);

        // Add trailing spaces
        for (int i = 0; i < padding; i++) {
            alignedText.append(" ");
        }
    }
}

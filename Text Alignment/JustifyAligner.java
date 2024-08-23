/**
 * The JustifyAligner class is responsible for justifying text to fit a given line length.
 * It extends the AbstractTextAligner to inherit common text formatting capabilities.
 */
public class JustifyAligner extends AbstractTextAligner {
    
    /**
     * Justifies a given array of paragraphs according to the specified line length.
     * The method breaks down each paragraph into lines and words, then iteratively constructs
     * justified lines. Hyphenation is also considered if a word does not fit in the remaining space.
     *
     * @param paragraphs An array of paragraphs to be justified.
     * @param lineLength The maximum length for each line of text.
     * @return A justified text as a single string with line breaks.
     */
    @Override
    public String formatText(String[] paragraphs, int lineLength) {
        // Validate line length
        if (lineLength <= 0) {
            return "Line length must be greater than zero.";
        }

        StringBuilder alignedText = new StringBuilder();
        for (String paragraph : paragraphs) {
            String[] words = paragraph.split(" ");
            StringBuilder line = new StringBuilder();
            int lineWordLength = 0;
            boolean addSpace = false;

            for (int i = 0; i < words.length; i++) {
                String word = words[i];
                int spaceNeeded = word.length() + (addSpace ? 1 : 0);

                if (lineWordLength + spaceNeeded > lineLength) {
                    int remainingSpace = lineLength - lineWordLength - 2;

                    // Handle hyphenation
                    if (remainingSpace >= 1 && word.length() > remainingSpace) {
                        line.append(" ").append(word.substring(0, remainingSpace)).append("-");
                        words[i] = word.substring(remainingSpace);
                        
                        alignedText.append(line.toString()).append("\n");
                        line.setLength(0);
                        lineWordLength = 0;
                        addSpace = false;
                        i--;
                        continue;
                    }

                    alignedText.append(line.toString()).append("\n");
                    line.setLength(0);
                    lineWordLength = 0;
                    addSpace = false;
                }

                if (addSpace) {
                    line.append(" ");
                    lineWordLength++;
                }

                line.append(word);
                lineWordLength += word.length();
                addSpace = true;
            }

            // Add the last line of the paragraph if present
            if (line.length() > 0) {
                alignedText.append(line.toString()).append("\n");
            }
        }
        return alignedText.toString();
    }
}

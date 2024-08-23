//
/**
 * The TextAlignment class is the start of the text alignment program.
 * It reads a text file and aligns its content based on the specified alignment type and line length.
 * The class validates command line arguments, reads the file into an array of strings,
 * and then coordinates with the other text aligner classes. It also catches and reports errors.
 */
public class TextAlignment {

    public static void main(String[] args) {
        if (!validateArgs(args)) {
            System.out.println("usage: java TextAlignment <filename> <alignmentType> <lineLength>");
            return;
        }

        String filename = args[0];
        String alignmentType = args[1].toLowerCase();  // Convert to lowercase for comparison
        int lineLength;

        // Check for invalid alignment type
        if (!("left".equals(alignmentType) || "right".equals(alignmentType) || "centre".equals(alignmentType) || "justify".equals(alignmentType))) {
            System.out.println("usage: java TextAlignment <filename> <alignmentType> <lineLength>");
            return;
        }

        try {
            lineLength = Integer.parseInt(args[2]);
            if (lineLength <= 0) {
                System.out.println("usage: java TextAlignment <filename> <alignmentType> <lineLength>");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("usage: java TextAlignment <filename> <alignmentType> <lineLength>");
            return;
        }

        String[] paragraphs = FileUtil.readFile(filename);

        if (paragraphs == null || paragraphs.length == 0) {
            System.out.println("File not found or empty: " + filename);
            return;
        }

        TextFormatter aligner = null;
        try {
            // Dynamically create an instance of the appropriate aligner class
            String className = alignmentType.substring(0, 1).toUpperCase() + alignmentType.substring(1).toLowerCase() + "Aligner";
            Class<?> clazz = Class.forName(className);
            aligner = (TextFormatter) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.out.println("usage: java TextAlignment <filename> <alignmentType> <lineLength>");
            e.printStackTrace();  // For debugging
            return;
        }

        String alignedText = aligner.formatText(paragraphs, lineLength);

        if (alignedText != null && !alignedText.isEmpty()) {
            System.out.println(alignedText);
        } // Removed the else part that printed the extra error message
    }

    private static boolean validateArgs(String[] args) {
        return args.length == 3;
    }
}
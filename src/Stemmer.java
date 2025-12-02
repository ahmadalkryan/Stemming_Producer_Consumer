public class Stemmer {

    /**
     * Stems an English word by removing common suffixes
     * This is a simplified version - can be replaced with a more advanced library
     *
     * @param word the word to stem
     * @return the stemmed word
     */
    public String stem(String word) {
        // Check for null or empty input
        if (word == null || word.trim().isEmpty()) {
            return word;
        }

        // Convert to lowercase for consistent processing
        String lowerWord = word.toLowerCase();

        // Common English suffixes to remove
        String[] suffixes = {
                "ing", "ed", "ly", "es", "s",
                "ment", "ness", "tion", "sion", "able", "ible"
        };

        // Try to remove each suffix
        for (String suffix : suffixes) {
            // Check if word ends with this suffix and is long enough
            if (lowerWord.endsWith(suffix) && lowerWord.length() > suffix.length() + 2) {
                // Remove the suffix
                return lowerWord.substring(0, lowerWord.length() - suffix.length());
            }
        }

        // Special case: replace "ies" with "y"
        if (lowerWord.endsWith("ies") && !lowerWord.endsWith("eies") && !lowerWord.endsWith("aies")) {
            return lowerWord.substring(0, lowerWord.length() - 3) + "y";
        }

        // Special case: replace "ied" with "y"
        if (lowerWord.endsWith("ied") && !lowerWord.endsWith("eied") && !lowerWord.endsWith("aied")) {
            return lowerWord.substring(0, lowerWord.length() - 3) + "y";
        }

        // Special case: replace "ier" with "y"
        if (lowerWord.endsWith("ier") && !lowerWord.endsWith("eier") && !lowerWord.endsWith("aier")) {
            return lowerWord.substring(0, lowerWord.length() - 3) + "y";
        }

        // Return the original word if no suffix was removed
        return lowerWord;
    }
}
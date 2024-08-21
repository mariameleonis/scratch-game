package outcome;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Arrays;

public class MatrixSerializer extends JsonSerializer<String[][]> {

    /**
     * Custom serializer for formatting a 2D matrix as a JSON array.
     * This serializer transforms the matrix into a JSON string with proper indentation and formatting.
     * <p>
     * Example output:
     * <pre>
     * {
     *   "matrix": [
     *     ["E", "F", "D"],
     *     ["E", "10X", "A"],
     *     ["F", "C", "B"]
     *   ]
     * }
     * </pre>
     * The matrix is serialized with each row on a new line and elements enclosed in quotes.
     */
    @Override
    public void serialize(String[][] matrix, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String matrixAsString = Arrays.deepToString(matrix);

        String formattedMatrix = formatMatrixString(matrixAsString);

        gen.writeRawValue(formattedMatrix);
    }

    private String formatMatrixString(String matrixAsString) {
        return matrixAsString
                .replaceFirst("\\[\\[", "[\n    [") // Add newline and indent for the first row
                .replaceFirst("]]", "]\n]") // Add newline for the closing bracket
                .replaceAll("], \\[", "],\n    [") // Add newline and indent between rows
                .replaceAll("([a-zA-Z0-9+]+)", "\"$1\""); // Add quotes around matrix elements
    }
}

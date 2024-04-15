import com.fasterxml.jackson.databind.ObjectMapper;
import model.Matrix;
import org.junit.jupiter.api.Test;


import java.io.InputStreamReader;
import java.io.Reader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class JsonParsingTest {
    private ClassLoader cl = FilesParsingTest.class.getClassLoader();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Test
    void checkJsonParsingTest() throws Exception{
        try (Reader reader = new InputStreamReader(
                cl.getResourceAsStream("matrix.json")
        )) {
            Matrix matrix = objectMapper.readValue(reader, Matrix.class);

            assertAll(
                    () -> assertThat(matrix.getUuid()).isNotEmpty(),
                    () -> assertThat(matrix.getStatusCode()).isEqualTo(0),
                    () -> assertThat(matrix.getCardHolder()).isEqualTo("John Master"),
                    () -> assertThat(matrix.getCards()).hasSize(2),
                    () -> assertThat(matrix.getCards().get(0).getType()).isEqualTo("visa"),
                    () -> assertThat(matrix.getCards().get(0).getNumber()).isEqualTo("***1111"),
                    () -> assertThat(matrix.getCards().get(0).getExpirationsDate()).isEqualTo("10/17"),
                    () -> assertThat(matrix.getCards().get(1).getType()).isEqualTo("masterCard"),
                    () -> assertThat(matrix.getCards().get(1).getNumber()).isEqualTo("***2222"),
                    () -> assertThat(matrix.getCards().get(1).getExpirationsDate()).isEqualTo("12/22")
            );
        }
    }
}

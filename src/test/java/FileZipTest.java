import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class FileZipTest {
    private final ClassLoader cl = FilesParsingTest.class.getClassLoader();
    private final List<String> list = Arrays.asList("data.csv", "results.pdf", "testResults.xlsx");
    private final ZipFile file = new ZipFile(new File("src/test/resources/documents.zip"));

    public FileZipTest() throws Exception {
    }

    @Test
    @DisplayName("Проверка всех документов в архиве")
    void checkZipFileAllTest() throws Exception {

        try (ZipInputStream zis = new ZipInputStream(
                cl.getResourceAsStream("documents.zip")
        )) {
            ZipEntry entry;

            List<String> actual = new ArrayList<>();
            while ((entry = zis.getNextEntry()) != null) {
                actual.add(entry.getName());
            }
            assertThat(actual).containsAll(list);

            String name = null;
            while ((entry = zis.getNextEntry()) != null) {
                name = entry.getName();
                switch (name) {
                    case ("results.pdf"):
                        try (InputStream is = file.getInputStream(entry)) {
                            PDF pdf = new PDF(is);
                            assertEquals("Результаты теста", pdf.title);
                        }
                        break;

                    case ("testResults.xlsx"):
                        try (InputStream is = file.getInputStream(entry)) {
                            XLS xls = new XLS(is);
                            assertAll(
                                    () -> assertEquals("№", xls.excel
                                            .getSheetAt(0).getRow(0).getCell(0).getStringCellValue()),
                                    () -> assertEquals("Кейс", xls.excel
                                            .getSheetAt(0).getRow(0).getCell(1).getStringCellValue()),
                                    () -> assertEquals("Результат", xls.excel
                                            .getSheetAt(0).getRow(0).getCell(2).getStringCellValue()),
                                    () -> assertEquals("Описание ошибки", xls.excel
                                            .getSheetAt(0).getRow(0).getCell(3).getStringCellValue())
                            );
                        }
                        break;

                    case ("data.csv"):
                        try (InputStream is = file.getInputStream(entry)) {
                            CSVReader csvReader = new CSVReader(new InputStreamReader(is));
                            List<String[]> data = csvReader.readAll();
                            assertEquals(3, data.size());
                            assertAll(
                                    () -> assertArrayEquals(
                                            new String[]{"login1", "pass1"},
                                            data.get(0)),
                                    () -> assertArrayEquals(
                                            new String[]{"login2", "pass2"},
                                            data.get(1)),
                                    () -> assertArrayEquals(
                                            new String[]{"login3", "pass3"},
                                            data.get(2))
                            );
                        }
                        break;
                }
            }
        }
    }

    @Test
    @DisplayName("Проверка наличия документов в архиве")
    void checkZipFileTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                cl.getResourceAsStream("documents.zip")
        )) {
            ZipEntry entry;

            List<String> actual = new ArrayList<>();
            while ((entry = zis.getNextEntry()) != null) {
                actual.add(entry.getName());
            }
            assertThat(actual).containsAll(list);
        }
    }

    @Test
    @DisplayName("Проверка данных PDF документа в архиве")
    void checkZipFilePdfTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                cl.getResourceAsStream("documents.zip")
        )) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals("results.pdf")) {
                    try (InputStream is = file.getInputStream(entry)) {
                        PDF pdf = new PDF(is);
                        assertEquals("Результаты теста", pdf.title);
                    }
                }
            }
        }
    }

    @Test
    @DisplayName("Проверка данных XLSX документа в архиве")
    void checkZipFileXlsxTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                cl.getResourceAsStream("documents.zip")
        )) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals("testResults.xlsx")) {
                    try (InputStream is = file.getInputStream(entry)) {
                        XLS xls = new XLS(is);
                        assertAll(
                                () -> assertEquals("№", xls.excel
                                        .getSheetAt(0).getRow(0).getCell(0).getStringCellValue()),
                                () -> assertEquals("Кейс", xls.excel
                                        .getSheetAt(0).getRow(0).getCell(1).getStringCellValue()),
                                () -> assertEquals("Результат", xls.excel
                                        .getSheetAt(0).getRow(0).getCell(2).getStringCellValue()),
                                () -> assertEquals("Описание ошибки", xls.excel
                                        .getSheetAt(0).getRow(0).getCell(3).getStringCellValue())
                        );
                    }
                }
            }
        }
    }

    @Test
    @DisplayName("Проверка данных CSV документа в архиве")
    void checkZipFileCsvTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                cl.getResourceAsStream("documents.zip")
        )) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals("data.csv")) {
                    try (InputStream is = file.getInputStream(entry)) {
                        CSVReader csvReader = new CSVReader(new InputStreamReader(is));
                        List<String[]> data = csvReader.readAll();
                        assertEquals(3, data.size());
                        assertAll(
                                () -> assertArrayEquals(
                                        new String[]{"login1", "pass1"},
                                        data.get(0)),
                                () -> assertArrayEquals(
                                        new String[]{"login2", "pass2"},
                                        data.get(1)),
                                () -> assertArrayEquals(
                                        new String[]{"login3", "pass3"},
                                        data.get(2))
                        );
                    }
                }
            }
        }
    }
}

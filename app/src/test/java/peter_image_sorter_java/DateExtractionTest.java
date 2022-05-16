package peter_image_sorter_java;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.drew.imaging.ImageProcessingException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import peter_image_sorter_java.DateExtraction;

public class DateExtractionTest {

    DateExtraction dateExtraction;
    // String test_jpg_location = "C:\\Users\\treki\\source\\Java\\Peter_Image_Sorter_java\\app\\src\\test\\resources\\test_jpg.jpg";
    String test_jpg_location = "src/test/resources/test_jpg.jpg";
    String dateJpgWasTaken = "27-02-2019 13:13:35";
    
    String test_heic_location = "src/test/resources/test_heic.heic";
    String dateHeicWasTaken = "27-02-2019 13:13:35";

    @BeforeEach
    void setup(){
        dateExtraction = new DateExtraction();
    }
    
    @Test void whenJpgHasMetadataThenDateShouldBeExtracted() throws ImageProcessingException, IOException, ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");   
        Date expected = formatter.parse(dateJpgWasTaken);

        var actual = dateExtraction.getImageDateTaken(test_jpg_location);

        assertEquals(expected, actual);
    }

    @Test void whenHeicHasMetadataThenDateShouldBeExtracted() throws ImageProcessingException, IOException, ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");   
        Date expected = formatter.parse(dateHeicWasTaken);

        var actual = dateExtraction.getImageDateTaken(test_heic_location);

        assertEquals(expected, actual);
    }
}

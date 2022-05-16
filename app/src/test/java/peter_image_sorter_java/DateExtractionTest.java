package peter_image_sorter_java;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.drew.imaging.ImageProcessingException;

import org.junit.jupiter.api.BeforeEach;

import peter_image_sorter_java.DateExtraction;

public class DateExtractionTest {

    DateExtraction dateExtraction;
    String test_jpg_location = "src/test/resources/test_jpg.jpg";
    String dateJpgWasTaken = "27-02-2019 13:13:35";
    
    String test_heic_location = "src/test/resources/test_heic.heic";
    String dateHeicWasTaken = "22-01-2022 12:08:40 pm";
    
    String test_mp4_location = "src/test/resources/test_mp4.mp4";
    String dateMp4WasTaken = "27-02-2019 13:13:35";
    @BeforeEach
    void setup(){
        dateExtraction = new DateExtraction();
    }
    
    @Test void whenJpgHasMetadataThenDateShouldBeExtracted() throws ImageProcessingException, IOException, ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");   
        Date expected = formatter.parse(dateJpgWasTaken);

        var actual = dateExtraction.getDateTaken(test_jpg_location);

        assertEquals(expected, actual);
    }

    @Test void whenHeicHasMetadataThenDateShouldBeExtracted() throws ImageProcessingException, IOException, ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa");   
        Date expected = formatter.parse(dateHeicWasTaken);

        var actual = dateExtraction.getDateTaken(test_heic_location);

        assertEquals(expected, actual);
    }

    @Test void whenMp4HasMetadataThenDateShouldBeExtracted() throws ImageProcessingException, IOException, ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");   
        Date expected = formatter.parse(dateMp4WasTaken);

        var actual = dateExtraction.getDateTaken(test_mp4_location);

        assertEquals(expected, actual);
    }
}

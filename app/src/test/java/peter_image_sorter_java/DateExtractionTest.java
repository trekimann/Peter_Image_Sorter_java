package peter_image_sorter_java;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.drew.imaging.ImageProcessingException;

import org.junit.jupiter.api.BeforeEach;

import peter_image_sorter_java.DateExtraction;

public class DateExtractionTest {

    DateExtraction dateExtraction;
    String test_jpg_location = "src/test/resources/test_jpg.jpg";
    String dateJpgWasTaken = "27-02-2019";
    
    String test_heic_location = "src/test/resources/test_heic.heic";
    String dateHeicWasTaken = "12-10-2021";
    
    String test_mp4_location = "src/test/resources/test_mp4.mp4";
    String dateMp4WasTaken = "29-03-2021";

    Calendar expected;

    @BeforeEach
    void setup(){
        dateExtraction = new DateExtraction();
        expected = Calendar.getInstance();
    }
    
    @Test void whenJpgHasMetadataThenDateShouldBeExtracted() throws ImageProcessingException, IOException, ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");   
        expected.setTime(formatter.parse(dateJpgWasTaken));

        var actual = dateExtraction.getDateTakenForImage(test_jpg_location);

        assertEquals(expected, actual);
    }

    @Test void whenHeicHasMetadataThenDateShouldBeExtracted() throws ImageProcessingException, IOException, ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");   
        expected.setTime(formatter.parse(dateHeicWasTaken));

        var actual = dateExtraction.getDateTakenForImage(test_heic_location);

        assertEquals(expected, actual);
    }

    @Test void whenMp4HasMetadataThenDateShouldBeExtracted() throws ImageProcessingException, IOException, ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");   
        expected.setTime(formatter.parse(dateMp4WasTaken));

        var actual = dateExtraction.getDateTakenForMp4(test_mp4_location);

        assertEquals(expected, actual);
    }
}

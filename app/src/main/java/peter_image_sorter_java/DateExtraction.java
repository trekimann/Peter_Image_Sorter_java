package peter_image_sorter_java;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.mp4.Mp4Directory;

public class DateExtraction {
    public Calendar getFileDateTaken(String fileLocation){
        var fileType = fileLocation.substring(fileLocation.lastIndexOf(".") + 1);
        Calendar toReturn = null;
        switch (fileType) {
            case "jpg":
            case "heic":
                // image files, use image date getter
                try {
                    toReturn = getDateTakenForImage(fileLocation);
                } catch (ImageProcessingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "mp4":
                try {
                    toReturn = getDateTakenForMp4(fileLocation);
                } catch (ImageProcessingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // mp4 file, use video data
                break;
            default:
                break;
        }
        return toReturn;
    }

    public Calendar getDateTakenForImage(String fileLocation) throws ImageProcessingException, IOException{
        String imageToRead = new File(fileLocation).getAbsolutePath();
        InputStream inputStream = new FileInputStream(imageToRead);
        Metadata imageMetaData = ImageMetadataReader.readMetadata(inputStream);

        var directory = imageMetaData.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        
        Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // only care about the date, not the time so force that here
        return setToMidnight(calendar);
    }

    public Calendar getDateTakenForMp4(String fileLocation) throws ImageProcessingException, IOException{
        String videoToRead = new File(fileLocation).getAbsolutePath();
        InputStream inputStream = new FileInputStream(videoToRead);
        Metadata imageMetaData = ImageMetadataReader.readMetadata(inputStream);

        var directory = imageMetaData.getFirstDirectoryOfType(Mp4Directory.class);
        
        Date date = directory.getDate(Mp4Directory.TAG_CREATION_TIME);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // only care about the date, not the time so force that here
        return setToMidnight(calendar);
    }

    private Calendar setToMidnight(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);

        return calendar;
    }
}

package peter_image_sorter_java;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.mp4.Mp4Directory;

public class DateExtraction {
    public Calendar getFileDateTaken(String fileLocation) throws ImageProcessingException, IOException{
        var fileType = fileLocation.substring(fileLocation.lastIndexOf(".") + 1);
        Metadata fileMetadata = getFileMetadata(fileLocation);
        Calendar toReturn = null;
        switch (fileType) {
            case "jpg":
            case "heic":
                // image files, use image date getter
                toReturn = getDateTakenForImage(fileMetadata, toReturn);       
                break;
            case "mp4":
                // mp4 file, use video data                
                toReturn = getDateTakenForMp4(fileMetadata, toReturn);
                break;
            default:
                break;
        }
        return toReturn;
    }

    public Metadata getFileMetadata(String fileLocation) throws ImageProcessingException, IOException{
        String fileToRead = new File(fileLocation).getAbsolutePath();
        InputStream inputStream = new FileInputStream(fileToRead);
        return ImageMetadataReader.readMetadata(inputStream);
    }

    public Calendar getDateTakenForImage(Metadata fileMetadata, Calendar calendar){
        var directory = fileMetadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        
        Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
        calendar.setTime(date);

        // only care about the date, not the time so force that here
        return setToMidnight(calendar);
    }

    public Calendar getDateTakenForMp4(Metadata fileMetadata, Calendar calendar){
        var directory = fileMetadata.getFirstDirectoryOfType(Mp4Directory.class);
        
        Date date = directory.getDate(Mp4Directory.TAG_CREATION_TIME);
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

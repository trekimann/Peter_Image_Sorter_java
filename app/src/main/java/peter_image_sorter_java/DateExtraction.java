package peter_image_sorter_java;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;

public class DateExtraction {
    public Date getFileDateTaken(String fileLocation){
        var fileType = fileLocation.substring(fileLocation.lastIndexOf(".") + 1);
        Date toReturn = null;
        switch (fileType) {
            case "jpg":
            case "heic":
                // image files, use image date getter
                try {
                    toReturn = getDateTaken(fileLocation);
                } catch (ImageProcessingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "mp4":
                // mp4 file, use video data
                break;
            default:
                break;
        }
        return toReturn;
    }

    public Date getDateTaken(String fileLocation) throws ImageProcessingException, IOException{
        String imageToRead = new File(fileLocation).getAbsolutePath();
        InputStream imageStream = new FileInputStream(imageToRead);
        Metadata imageMetaData = ImageMetadataReader.readMetadata(imageStream);

        var directory = imageMetaData.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);

        return date;
    }
}

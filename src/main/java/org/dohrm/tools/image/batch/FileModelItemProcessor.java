package org.dohrm.tools.image.batch;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import org.dohrm.tools.image.model.FileModel;
import org.joda.time.LocalDateTime;
import org.springframework.batch.item.ItemProcessor;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @author MDO
 * @since 17/08/2015.
 */
public class FileModelItemProcessor implements ItemProcessor<File, FileModel> {

    @Override
    public FileModel process(File item) throws Exception {
        if (item.exists() && item.isFile()) {
            String year = "not-def";
            String month = "not-def";
            try {
                final Metadata metadata = ImageMetadataReader.readMetadata(item);
                if (metadata != null) {
                    final ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
                    final Date creationDate = directory == null ? null : directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
                    if (creationDate != null) {
                        final String[] split = LocalDateTime.fromDateFields(creationDate).toString("yyyy/MM").split("\\/");
                        if (split.length > 0) {
                            year = split[0];
                            if (split.length > 1) {
                                month = split[1];
                            }
                        }
                    }
                }
            } catch (ImageProcessingException e) {
            } catch (IOException e) {
                return null;
            }
            return new FileModel(item, year + "/" + month, null);
        }
        return null;
    }
}

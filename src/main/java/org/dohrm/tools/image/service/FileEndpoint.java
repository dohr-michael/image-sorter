package org.dohrm.tools.image.service;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.google.common.collect.ImmutableMap;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @author MDO
 * @since 15/08/2015.
 */
@MessageEndpoint(value = "fileEndpoint")
public class FileEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(FileEndpoint.class);


    @ServiceActivator
    public Message<File> serviceActivator(File payload) {
        if (payload.exists() && payload.isFile()) {
            String year = "not-def";
            String month = "not-def";
            try {
                final Metadata metadata = ImageMetadataReader.readMetadata(payload);
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
                throw new IllegalArgumentException("Error when parsing image.");
            } catch (IOException e) {
                throw new IllegalArgumentException("Error when parsing image.");
            }
            return new GenericMessage<File>(payload, ImmutableMap.<String, Object>builder()
                    .put("year", year)
                    .put("month", month)
                    .put("name", payload.getName()).build());

        }
        throw new IllegalArgumentException("File not found or not correctly formatted");
    }

}

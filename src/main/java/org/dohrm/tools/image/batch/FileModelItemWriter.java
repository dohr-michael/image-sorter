package org.dohrm.tools.image.batch;

import com.google.common.io.Files;
import org.dohrm.tools.image.model.FileModel;
import org.springframework.batch.item.ItemWriter;

import java.io.File;
import java.util.List;

/**
 * @author MDO
 * @since 17/08/2015.
 */
public class FileModelItemWriter implements ItemWriter<FileModel> {

    private final File directory;

    /**
     * Constructor.
     *
     * @param directory output directory
     */
    public FileModelItemWriter(File directory) {
        if (directory == null || !directory.isDirectory()) {
            throw new IllegalArgumentException("Bad directory passed in parameter.");
        }
        this.directory = directory;
    }

    @Override
    public void write(List<? extends FileModel> items) throws Exception {
        if (items != null && items.size() > 0) {
            for (FileModel item : items) {
                if (item != null && item.getOrigin() != null && item.getTargetFolder() != null) {
                    final File targetFile = new File(directory.getAbsolutePath() + "/" + item.getTargetFolder() + "/" + item.getOrigin().getName());
                    if (!targetFile.getParentFile().exists()) {
                        targetFile.getParentFile().mkdirs();
                    }
                    if (!targetFile.exists()) {
                        Files.copy(item.getOrigin(), targetFile);
                    }
                }
            }
        }
    }
}

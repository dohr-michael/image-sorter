package org.dohrm.tools.image.batch;

import org.springframework.batch.item.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author MDO
 * @since 17/08/2015.
 */
public class DirectoryItemReader implements ItemReader<File>, ItemStream {
    /**
     * Base directory.
     */
    private final File directory;
    /**
     * Batch key
     */
    private final String key = "file.in.directory.count";
    /**
     * List of files.
     */
    private File[] files;
    /**
     * Current index.
     */
    private int currentCount;

    public DirectoryItemReader(File directory) {
        if (directory == null || !directory.isDirectory()) {
            throw new IllegalArgumentException("Bad directory passed in parameter.");
        }
        this.directory = directory;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        this.files = listFiles(directory);
        currentCount = executionContext.getInt(key, 0);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        executionContext.putInt(key, currentCount);
    }

    @Override
    public void close() throws ItemStreamException {
    }

    @Override
    public File read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        int index = currentCount++;
        if (index >= files.length) {
            return null;
        }
        return files[index];
    }

    private File[] listFiles(File rootDirectory) {
        File[] rootFiles = rootDirectory.listFiles();
        List<File> files = new ArrayList<File>(rootFiles.length);
        for (File rootFile : rootFiles) {
            if (rootFile.isDirectory()) {
                files.addAll(Arrays.asList(listFiles(rootFile)));
            } else {
                files.add(rootFile);
            }
        }
        return files.toArray(new File[files.size()]);
    }
}

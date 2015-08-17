package org.dohrm.tools.image.model;

import java.io.File;

/**
 * @author MDO
 * @since 17/08/2015.
 */
public class FileModel {
    /**
     * Origin file.
     */
    private File origin;
    /**
     * Target folder.
     */
    private String targetFolder;
    /**
     * Hash of file.
     */
    private String hash;

    /**
     * Model.
     *
     * @param origin       base file.
     * @param targetFolder target folder
     * @param hash         hash.
     */
    public FileModel(File origin, String targetFolder, String hash) {
        this.origin = origin;
        this.targetFolder = targetFolder;
        this.hash = hash;
    }

    public File getOrigin() {
        return origin;
    }

    public String getTargetFolder() {
        return targetFolder;
    }

    public String getHash() {
        return hash;
    }
}

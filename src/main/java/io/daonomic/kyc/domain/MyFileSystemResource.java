package io.daonomic.kyc.domain;

import org.springframework.core.io.FileSystemResource;

import java.io.File;

public class MyFileSystemResource extends FileSystemResource {
    private final String filename;

    public MyFileSystemResource(File file, String filename) {
        super(file);
        this.filename = filename;
    }

    @Override
    public String getFilename() {
        return filename;
    }
}

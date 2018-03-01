package io.daonomic.kyc.common;

import org.springframework.core.io.ByteArrayResource;

public class TestResource extends ByteArrayResource {
    private final String filename;

    public TestResource(byte[] byteArray, String filename) {
        super(byteArray);
        this.filename = filename;
    }

    public TestResource(byte[] byteArray, String description, String filename) {
        super(byteArray, description);
        this.filename = filename;
    }

    @Override
    public String getFilename() {
        return filename;
    }
}

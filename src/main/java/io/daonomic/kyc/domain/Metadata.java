package io.daonomic.kyc.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Metadata {
    private final String filename;
    private final String contentType;

    @JsonCreator
    public Metadata(@JsonProperty("filename") String filename, @JsonProperty("contentType") String contentType) {
        this.filename = filename;
        this.contentType = contentType;
    }

    public String getFilename() {
        return filename;
    }

    public String getContentType() {
        return contentType;
    }
}

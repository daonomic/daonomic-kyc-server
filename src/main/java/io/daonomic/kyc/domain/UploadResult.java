package io.daonomic.kyc.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UploadResult {
    private final String id;

    @JsonCreator
    public UploadResult(@JsonProperty("id") String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

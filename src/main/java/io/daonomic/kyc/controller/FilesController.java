package io.daonomic.kyc.controller;

import org.springframework.web.bind.annotation.RestController;

import java.nio.file.FileSystems;

@RestController
public class FilesController {
    public void upload() {
        FileSystems.getDefault().getPath();
    }
}

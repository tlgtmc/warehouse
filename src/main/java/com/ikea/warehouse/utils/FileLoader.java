package com.ikea.warehouse.utils;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Slf4j
public abstract class FileLoader {

    /**
     * Finds file by the given fileName and returns in requested object type
     */
    protected final <T> T loadFileAsObject(String fileName, TypeReference<T> typeReference) throws IOException {
        log.info("Loading file: {}", fileName);

        File file = ResourceUtils.getFile(fileName);
        if (!file.exists()) {
            file = ResourceUtils.getFile("classpath:" + fileName);
        }
        return new ObjectMapper().readValue(new String(Files.readAllBytes(file.toPath())), typeReference);
    }
}

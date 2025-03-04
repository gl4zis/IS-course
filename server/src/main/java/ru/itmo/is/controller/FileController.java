package ru.itmo.is.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itmo.is.dto.OneFieldDto;
import ru.itmo.is.dto.response.FileResponse;
import ru.itmo.is.service.FileService;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/upload")
    public OneFieldDto<String> upload(@RequestParam MultipartFile file) {
        return new OneFieldDto<>(fileService.upload(file));
    }

    @PostMapping("/download/{filename}")
    public ResponseEntity<Resource> download(@PathVariable("filename") String filename) {
        FileResponse file = fileService.get(filename);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.name() + "\"")
                .body(file.data());
    }
}

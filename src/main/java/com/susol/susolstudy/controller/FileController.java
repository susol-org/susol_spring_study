package com.susol.susolstudy.controller;

import com.susol.susolstudy.model.entity.PostFile;
import com.susol.susolstudy.service.PostFileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.action.internal.EntityActionVetoException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Controller
@RequestMapping("/file")
public class FileController {

    private final PostFileService service;

    @GetMapping("/{renamedFileName}")
    public ResponseEntity<Resource> filedDownload(@PathVariable String renamedFileName,
                                                 @AuthenticationPrincipal UserDetails user) {
        PostFile postFile = service.fileDownload(user.getUsername(), renamedFileName);

        Path filePath = Paths.get("C:/project-workspace/Spring_Project/backend/upload/", renamedFileName);
        Resource resource = new FileSystemResource(filePath);

        if(!resource.exists()) throw new EntityNotFoundException("파일이 존재하지 않습니다.");

        String encodedName = URLEncoder.encode(postFile.getPostOriginalFileName(), StandardCharsets.UTF_8)
                                                                        .replace("+", "%20");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedName)
                .body(resource);
    }
}

package study.springstudy.file;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.plaf.multi.MultiListUI;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RestController
@RequestMapping("/files")
public class FileController {

    private final Path uploadDirectory =
            Paths.get("uploads").toAbsolutePath().normalize();

    // 단일 파일 업로드
    @PostMapping
    public FileUploadResponse uploadFile(
            @RequestParam("file")MultipartFile file
    ) throws IOException {
        SavedFile savedFile = saveFile(file);

        return new FileUploadResponse(
                savedFile.originalFileName(),
                savedFile.storedFileName(),
                savedFile.size()
        );
    }

    // JSON 데이터와 파일을 함께 업로드
    @PostMapping("/with-data")
    public FileWithDataResponse uploadWithData(
            @Valid @RequestPart("data") FileDataRequest request,
            @RequestPart("file") MultipartFile file
    ) throws IOException {
        SavedFile savedFile = saveFile(file);

        return new FileWithDataResponse(
                request.title(),
                request.description(),
                savedFile.originalFileName(),
                savedFile.storedFileName(),
                savedFile.size()
        );
    }

    private SavedFile saveFile(MultipartFile file) throws IOException {
        validateFile(file);

        Files.createDirectories(uploadDirectory);

        String originalFileName =
                extractSafeOriginalFileName(file);

        String extension =
                extractExtension(originalFileName);

        String storedFileName =
                UUID.randomUUID() + extension;

        Path destination =
                uploadDirectory.resolve(storedFileName);

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(
                    inputStream,
                    destination,
                    StandardCopyOption.REPLACE_EXISTING
            );
        }

        return new SavedFile(
                originalFileName,
                storedFileName,
                file.getSize()
        );
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException(
                    "업로드할 파일이 비어 있습니다."
            );
        }

        /*
        // 파일 검증 추가(최대 파일 크기, 파일 타입(ex: 이미지만)
        long maxSize = 5 * 1024 * 1024;

        if (file.getSize() > maxSize) {
            throw new IllegalArgumentException(
                    "파일 크기는 5MB 이하여야 합니다."
            );
        }
        String contentType = file.getContentType();

        if (contentType == null
                || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException(
                    "이미지 파일만 업로드할 수 있습니다."
            );
        }
        */
    }

    private String extractSafeOriginalFileName(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();

        if (originalFileName == null || originalFileName.isBlank()) {
            throw new IllegalArgumentException(
                    "파일 이름이 없습니다."
            );
        }
        return Paths.get(originalFileName)
                .getFileName()
                .toString();
    }

    private String extractExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");

        if (dotIndex == -1) {
            return "";
        }
        return fileName.substring(dotIndex);
    }

    private record SavedFile(
            String originalFileName,
            String storedFileName,
            long size
    ){
    }
}

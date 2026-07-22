package study.springstudy.file;

public record FileUploadResponse(
        String originalFileName,
        String storedFiledName,
        long size
) {
}

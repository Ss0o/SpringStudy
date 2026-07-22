package study.springstudy.file;

public record FileWithDataResponse(
        String title,
        String description,
        String originalFileName,
        String storedFileName,
        long size
) {
}

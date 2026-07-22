package study.springstudy.file;

import jakarta.validation.constraints.NotBlank;

public record FileDataRequest(
        @NotBlank(message = "제목은 필수입니다.")
        String title,

        @NotBlank(message = "설명은 필수입니다.")
        String description
) {
}

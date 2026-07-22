package study.springstudy.file;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FileControllerTest {

    @Test
    void uploadWithData() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new FileController())
                .build();

        MockMultipartFile data = new MockMultipartFile(
                "data",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                """
                {
                  "title": "Spring 파일 업로드",
                  "description": "JSON과 파일을 함께 전송합니다."
                }
                """.getBytes()
        );

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "sample.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Spring file upload test".getBytes()
        );

        mockMvc.perform(
                        multipart("/files/with-data")
                                .file(data)
                                .file(file)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Spring 파일 업로드"))
                .andExpect(jsonPath("$.description").value("JSON과 파일을 함께 전송합니다."))
                .andExpect(jsonPath("$.originalFileName").value("sample.txt"))
                .andExpect(jsonPath("$.storedFileName").isNotEmpty())
                .andExpect(jsonPath("$.size").value(23));
    }
}

package kr.co.fifoBack.dto.grade;

import lombok.*;

@Getter
@NoArgsConstructor
@ToString
@Builder
public class CodeExecutionResponseDTO {
    private String output;

    public CodeExecutionResponseDTO(String output) {
        this.output = output;
    }

    // Getter
}

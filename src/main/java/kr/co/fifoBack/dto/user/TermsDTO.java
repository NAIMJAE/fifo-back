package kr.co.fifoBack.dto.user;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TermsDTO {
    private String terms;
    private String privacy;
    private String sms;
}

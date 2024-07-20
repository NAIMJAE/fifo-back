package kr.co.fifoBack.dto.user;

import lombok.*;
import org.eclipse.jdt.internal.compiler.env.IBinaryNestedType;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegionDTO {
    private int urno;
    private int userno;
    private String regionname;
    private int countpeople;
}

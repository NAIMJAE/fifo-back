package kr.co.fifoBack.dto.gathering.page;


import kr.co.fifoBack.dto.gathering.GatheringDTO;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class GathPageRequestDTO {

    @Builder.Default
    private int no = 1;

    @Builder.Default
    private int pg = 1;

    @Builder.Default
    private int size = 12;

    // 모집중 조회
    private boolean gathState;

    // 전체 조회
    private String sort;
    private GatheringDTO gatheringDTO;

    // 내 모임 조회
    private int userno;
    private int gathcate;
    private String type;
    private String keyword;

    public Pageable getPageable(String sort){
        return PageRequest.of(this.pg - 1, this.size, Sort.by(sort).descending());
    }

}


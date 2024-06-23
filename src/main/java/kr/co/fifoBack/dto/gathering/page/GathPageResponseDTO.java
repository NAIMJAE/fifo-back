package kr.co.fifoBack.dto.gathering.page;


import kr.co.fifoBack.dto.gathering.GatheringDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GathPageResponseDTO {

        private List<?> dtoList;

        private GatheringDTO gatheringDTO;
        private int pg;
        private int size;
        private int total;
        private int startNo;
        private int start, end;
        private boolean prev, next;

        @Builder
        public GathPageResponseDTO(GathPageRequestDTO pageRequestDTO, List<?> dtoList, int total){
            this.gatheringDTO = pageRequestDTO.getGatheringDTO();
            this.pg = pageRequestDTO.getPg();
            this.size = pageRequestDTO.getSize() > 0 ? pageRequestDTO.getSize() : 16; // 기본값 16
            this.total = total;
            this.dtoList = dtoList;

            this.startNo = total - ((pg - 1) * size);
            this.end = (int) (Math.ceil(this.pg / 10.0)) * 10;
            this.start = this.end - 9;

            int last = (int) (Math.ceil(total / (double) this.size));
            this.end = end > last ? last : end;
            this.prev = this.start > 1;
            this.next = total > this.end * this.size;
        }

}

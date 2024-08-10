package kr.co.fifoBack.repository.gathering;

import kr.co.fifoBack.entity.gathering.Mooim;
import kr.co.fifoBack.repository.mooim.MooimRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class MooimRepositoryTest {

    @Autowired
    private MooimRepository mooimRepository;

    @Test
    void save() {
        // given
        Mooim mooim = new Mooim();
        mooim.setGathno(1);
        mooim.setMooimcate(2);
        mooim.setMooimstate(2);
        mooim.setUserno(1);
        mooim.setMooimtitle("새 모임임");
        // when
        Mooim savedMooim = mooimRepository.save(mooim);
        // then
        Assertions.assertThat(savedMooim).isNotNull();
    }

    void findMooimsByUserno() {
    }

    void findMooimsByUsernoAndMooimstate() {
    }
}
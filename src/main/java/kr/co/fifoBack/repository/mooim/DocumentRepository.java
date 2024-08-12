package kr.co.fifoBack.repository.mooim;

import kr.co.fifoBack.entity.mooim.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
    public List<Document> findByMooimnoOrderByDocnoAsc(int mooimno);
}

package kr.co.fifoBack.entity.post;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QHeart is a Querydsl query type for Heart
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHeart extends EntityPathBase<Heart> {

    private static final long serialVersionUID = -1772261533L;

    public static final QHeart heart = new QHeart("heart");

    public final DateTimePath<java.time.LocalDateTime> cDate = createDateTime("cDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> hNo = createNumber("hNo", Integer.class);

    public final NumberPath<Integer> pno = createNumber("pno", Integer.class);

    public final NumberPath<Integer> userNo = createNumber("userNo", Integer.class);

    public QHeart(String variable) {
        super(Heart.class, forVariable(variable));
    }

    public QHeart(Path<? extends Heart> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHeart(PathMetadata metadata) {
        super(Heart.class, metadata);
    }

}


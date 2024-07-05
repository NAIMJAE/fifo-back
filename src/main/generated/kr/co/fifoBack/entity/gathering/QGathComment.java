package kr.co.fifoBack.entity.gathering;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGathComment is a Querydsl query type for GathComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGathComment extends EntityPathBase<GathComment> {

    private static final long serialVersionUID = -301363393L;

    public static final QGathComment gathComment = new QGathComment("gathComment");

    public final NumberPath<Integer> commentno = createNumber("commentno", Integer.class);

    public final StringPath content = createString("content");

    public final NumberPath<Integer> gathno = createNumber("gathno", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> rdate = createDateTime("rdate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> updateDate = createDateTime("updateDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> userno = createNumber("userno", Integer.class);

    public QGathComment(String variable) {
        super(GathComment.class, forVariable(variable));
    }

    public QGathComment(Path<? extends GathComment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGathComment(PathMetadata metadata) {
        super(GathComment.class, metadata);
    }

}


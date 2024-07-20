//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package kr.co.fifoBack.entity.gathering;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import java.time.LocalDateTime;

public class QGathComment extends EntityPathBase<GathComment> {
    private static final long serialVersionUID = -301363393L;
    public static final QGathComment gathComment = new QGathComment("gathComment");
    public final NumberPath<Integer> commentno = this.createNumber("commentno", Integer.class);
    public final StringPath content = this.createString("content");
    public final NumberPath<Integer> gathno = this.createNumber("gathno", Integer.class);
    public final DateTimePath<LocalDateTime> rdate = this.createDateTime("rdate", LocalDateTime.class);
    public final NumberPath<Integer> state = this.createNumber("state", Integer.class);
    public final DateTimePath<LocalDateTime> updateDate = this.createDateTime("updateDate", LocalDateTime.class);
    public final NumberPath<Integer> userno = this.createNumber("userno", Integer.class);

    public QGathComment(String variable) {
        super(GathComment.class, PathMetadataFactory.forVariable(variable));
    }

    public QGathComment(Path<? extends GathComment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGathComment(PathMetadata metadata) {
        super(GathComment.class, metadata);
    }
}
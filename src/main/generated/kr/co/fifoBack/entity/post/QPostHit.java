package kr.co.fifoBack.entity.post;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPostHit is a Querydsl query type for PostHit
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostHit extends EntityPathBase<PostHit> {

    private static final long serialVersionUID = 771713712L;

    public static final QPostHit postHit = new QPostHit("postHit");

    public final StringPath address = createString("address");

    public final NumberPath<Integer> hno = createNumber("hno", Integer.class);

    public final NumberPath<Integer> pno = createNumber("pno", Integer.class);

    public QPostHit(String variable) {
        super(PostHit.class, forVariable(variable));
    }

    public QPostHit(Path<? extends PostHit> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPostHit(PathMetadata metadata) {
        super(PostHit.class, metadata);
    }

}


package kr.co.fifoBack.entity.post;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPostTag is a Querydsl query type for PostTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostTag extends EntityPathBase<PostTag> {

    private static final long serialVersionUID = 771724983L;

    public static final QPostTag postTag = new QPostTag("postTag");

    public final NumberPath<Integer> pno = createNumber("pno", Integer.class);

    public final NumberPath<Integer> ptNo = createNumber("ptNo", Integer.class);

    public final NumberPath<Integer> tno = createNumber("tno", Integer.class);

    public QPostTag(String variable) {
        super(PostTag.class, forVariable(variable));
    }

    public QPostTag(Path<? extends PostTag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPostTag(PathMetadata metadata) {
        super(PostTag.class, metadata);
    }

}


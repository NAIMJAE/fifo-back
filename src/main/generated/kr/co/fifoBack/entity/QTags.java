package kr.co.fifoBack.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTags is a Querydsl query type for Tags
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTags extends EntityPathBase<Tags> {

    private static final long serialVersionUID = 239185540L;

    public static final QTags tags = new QTags("tags");

    public final StringPath tag = createString("tag");

    public final NumberPath<Integer> tno = createNumber("tno", Integer.class);

    public QTags(String variable) {
        super(Tags.class, forVariable(variable));
    }

    public QTags(Path<? extends Tags> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTags(PathMetadata metadata) {
        super(Tags.class, metadata);
    }

}


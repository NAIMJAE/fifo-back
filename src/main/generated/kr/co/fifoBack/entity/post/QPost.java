package kr.co.fifoBack.entity.post;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = -472563229L;

    public static final QPost post = new QPost("post");

    public final NumberPath<Integer> cateNo = createNumber("cateNo", Integer.class);

    public final NumberPath<Integer> comNum = createNumber("comNum", Integer.class);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> heartNum = createNumber("heartNum", Integer.class);

    public final NumberPath<Integer> hit = createNumber("hit", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> modiDate = createDateTime("modiDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> pno = createNumber("pno", Integer.class);

    public final StringPath title = createString("title");

    public final NumberPath<Integer> userNo = createNumber("userNo", Integer.class);

    public QPost(String variable) {
        super(Post.class, forVariable(variable));
    }

    public QPost(Path<? extends Post> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPost(PathMetadata metadata) {
        super(Post.class, metadata);
    }

}


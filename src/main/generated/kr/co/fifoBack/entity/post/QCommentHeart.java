package kr.co.fifoBack.entity.post;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCommentHeart is a Querydsl query type for CommentHeart
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommentHeart extends EntityPathBase<CommentHeart> {

    private static final long serialVersionUID = 1827793770L;

    public static final QCommentHeart commentHeart = new QCommentHeart("commentHeart");

    public final NumberPath<Integer> cheartno = createNumber("cheartno", Integer.class);

    public final NumberPath<Integer> cno = createNumber("cno", Integer.class);

    public final NumberPath<Integer> pno = createNumber("pno", Integer.class);

    public final NumberPath<Integer> userNo = createNumber("userNo", Integer.class);

    public QCommentHeart(String variable) {
        super(CommentHeart.class, forVariable(variable));
    }

    public QCommentHeart(Path<? extends CommentHeart> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCommentHeart(PathMetadata metadata) {
        super(CommentHeart.class, metadata);
    }

}


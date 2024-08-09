package kr.co.fifoBack.entity.mooim;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChat is a Querydsl query type for Chat
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChat extends EntityPathBase<Chat> {

    private static final long serialVersionUID = 1041984608L;

    public static final QChat chat = new QChat("chat");

    public final DateTimePath<java.time.LocalDateTime> chatdate = createDateTime("chatdate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> chatno = createNumber("chatno", Integer.class);

    public final StringPath message = createString("message");

    public final NumberPath<Integer> mooimno = createNumber("mooimno", Integer.class);

    public final NumberPath<Integer> userno = createNumber("userno", Integer.class);

    public QChat(String variable) {
        super(Chat.class, forVariable(variable));
    }

    public QChat(Path<? extends Chat> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChat(PathMetadata metadata) {
        super(Chat.class, metadata);
    }

}


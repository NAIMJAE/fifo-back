package kr.co.fifoBack.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 239232566L;

    public static final QUser user = new QUser("user");

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final StringPath email = createString("email");

    public final StringPath gender = createString("gender");

    public final StringPath hp = createString("hp");

    public final DateTimePath<java.time.LocalDateTime> leaveDate = createDateTime("leaveDate", java.time.LocalDateTime.class);

    public final StringPath name = createString("name");

    public final StringPath nick = createString("nick");

    public final StringPath pass = createString("pass");

    public final DateTimePath<java.time.LocalDateTime> rdate = createDateTime("rdate", java.time.LocalDateTime.class);

    public final StringPath region = createString("region");

    public final StringPath role = createString("role");

    public final StringPath thumb = createString("thumb");

    public final NumberPath<Integer> userNo = createNumber("userNo", Integer.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}


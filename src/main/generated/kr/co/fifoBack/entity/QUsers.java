package kr.co.fifoBack.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsers is a Querydsl query type for Users
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsers extends EntityPathBase<Users> {

    private static final long serialVersionUID = -1173724931L;

    public static final QUsers users = new QUsers("users");

    public final DateTimePath<java.util.Date> birth = createDateTime("birth", java.util.Date.class);

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

    public final NumberPath<Integer> userno = createNumber("userno", Integer.class);

    public QUsers(String variable) {
        super(Users.class, forVariable(variable));
    }

    public QUsers(Path<? extends Users> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsers(PathMetadata metadata) {
        super(Users.class, metadata);
    }

}


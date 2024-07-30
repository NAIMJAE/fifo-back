<<<<<<< HEAD
package kr.co.fifoBack.entity.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserRegion is a Querydsl query type for UserRegion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserRegion extends EntityPathBase<UserRegion> {

    private static final long serialVersionUID = 193640855L;

    public static final QUserRegion userRegion = new QUserRegion("userRegion");

    public final StringPath regionname = createString("regionname");

    public final NumberPath<Integer> urno = createNumber("urno", Integer.class);

    public final NumberPath<Integer> userno = createNumber("userno", Integer.class);

    public QUserRegion(String variable) {
        super(UserRegion.class, forVariable(variable));
    }

    public QUserRegion(Path<? extends UserRegion> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserRegion(PathMetadata metadata) {
        super(UserRegion.class, metadata);
    }

}

=======
package kr.co.fifoBack.entity.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserRegion is a Querydsl query type for UserRegion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserRegion extends EntityPathBase<UserRegion> {

    private static final long serialVersionUID = 193640855L;

    public static final QUserRegion userRegion = new QUserRegion("userRegion");

    public final StringPath regionname = createString("regionname");

    public final NumberPath<Integer> urno = createNumber("urno", Integer.class);

    public final NumberPath<Integer> userno = createNumber("userno", Integer.class);

    public QUserRegion(String variable) {
        super(UserRegion.class, forVariable(variable));
    }

    public QUserRegion(Path<? extends UserRegion> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserRegion(PathMetadata metadata) {
        super(UserRegion.class, metadata);
    }

}

>>>>>>> 2cb1ccf63ecd4698d19edcce98ff1e3e62ce8bc0

package kr.co.fifoBack.entity.gathering;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRecruit is a Querydsl query type for Recruit
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecruit extends EntityPathBase<Recruit> {

    private static final long serialVersionUID = 1994799052L;

    public static final QRecruit recruit = new QRecruit("recruit");

    public final NumberPath<Integer> gathno = createNumber("gathno", Integer.class);

    public final NumberPath<Integer> recruitno = createNumber("recruitno", Integer.class);

    public final StringPath recruitstate = createString("recruitstate");

    public final NumberPath<Integer> userno = createNumber("userno", Integer.class);

    public QRecruit(String variable) {
        super(Recruit.class, forVariable(variable));
    }

    public QRecruit(Path<? extends Recruit> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRecruit(PathMetadata metadata) {
        super(Recruit.class, metadata);
    }

}


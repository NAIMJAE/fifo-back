package kr.co.fifoBack.entity.gathering;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGathering is a Querydsl query type for Gathering
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGathering extends EntityPathBase<Gathering> {

    private static final long serialVersionUID = -920782571L;

    public static final QGathering gathering = new QGathering("gathering");

    public final StringPath gathcate = createString("gathcate");

    public final StringPath gathdetail = createString("gathdetail");

    public final StringPath gathlanguage = createString("gathlanguage");

    public final StringPath gathmode = createString("gathmode");

    public final NumberPath<Integer> gathno = createNumber("gathno", Integer.class);

    public final NumberPath<Integer> gathnowmember = createNumber("gathnowmember", Integer.class);

    public final StringPath gathrecruitfield = createString("gathrecruitfield");

    public final StringPath gathstate = createString("gathstate");

    public final StringPath gathsupport = createString("gathsupport");

    public final StringPath gathtitle = createString("gathtitle");

    public final NumberPath<Integer> gathtotalmember = createNumber("gathtotalmember", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> projectend = createDateTime("projectend", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> projectstart = createDateTime("projectstart", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> recruitend = createDateTime("recruitend", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> recruitstart = createDateTime("recruitstart", java.time.LocalDateTime.class);

    public final NumberPath<Integer> userno = createNumber("userno", Integer.class);

    public QGathering(String variable) {
        super(Gathering.class, forVariable(variable));
    }

    public QGathering(Path<? extends Gathering> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGathering(PathMetadata metadata) {
        super(Gathering.class, metadata);
    }

}


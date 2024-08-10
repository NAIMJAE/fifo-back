package kr.co.fifoBack.entity.gathering;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMooim is a Querydsl query type for Mooim
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMooim extends EntityPathBase<Mooim> {

    private static final long serialVersionUID = -306142977L;

    public static final QMooim mooim = new QMooim("mooim");

    public final NumberPath<Integer> gathno = createNumber("gathno", Integer.class);

    public final NumberPath<Integer> mooimcate = createNumber("mooimcate", Integer.class);

    public final DatePath<java.time.LocalDate> mooimend = createDate("mooimend", java.time.LocalDate.class);

    public final StringPath mooimintro = createString("mooimintro");

    public final NumberPath<Integer> mooimno = createNumber("mooimno", Integer.class);

    public final DatePath<java.time.LocalDate> mooimstart = createDate("mooimstart", java.time.LocalDate.class);

    public final NumberPath<Integer> mooimstate = createNumber("mooimstate", Integer.class);

    public final StringPath mooimtitle = createString("mooimtitle");

    public final NumberPath<Integer> progress = createNumber("progress", Integer.class);

    public final StringPath thumb = createString("thumb");

    public final NumberPath<Integer> userno = createNumber("userno", Integer.class);

    public QMooim(String variable) {
        super(Mooim.class, forVariable(variable));
    }

    public QMooim(Path<? extends Mooim> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMooim(PathMetadata metadata) {
        super(Mooim.class, metadata);
    }

}


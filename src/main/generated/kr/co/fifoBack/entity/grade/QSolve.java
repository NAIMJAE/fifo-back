package kr.co.fifoBack.entity.grade;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSolve is a Querydsl query type for Solve
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSolve extends EntityPathBase<Solve> {

    private static final long serialVersionUID = 459359901L;

    public static final QSolve solve = new QSolve("solve");

    public final StringPath code = createString("code");

    public final NumberPath<Integer> questionno = createNumber("questionno", Integer.class);

    public final StringPath solved = createString("solved");

    public final DateTimePath<java.time.LocalDateTime> solveddate = createDateTime("solveddate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> solveid = createNumber("solveid", Integer.class);

    public final NumberPath<Integer> userno = createNumber("userno", Integer.class);

    public QSolve(String variable) {
        super(Solve.class, forVariable(variable));
    }

    public QSolve(Path<? extends Solve> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSolve(PathMetadata metadata) {
        super(Solve.class, metadata);
    }

}


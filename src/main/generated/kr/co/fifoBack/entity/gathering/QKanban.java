package kr.co.fifoBack.entity.gathering;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QKanban is a Querydsl query type for Kanban
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QKanban extends EntityPathBase<Kanban> {

    private static final long serialVersionUID = -970722071L;

    public static final QKanban kanban = new QKanban("kanban");

    public final StringPath content = createString("content");

    public final NumberPath<Integer> kanno = createNumber("kanno", Integer.class);

    public final StringPath kanstatus = createString("kanstatus");

    public final NumberPath<Integer> mooimno = createNumber("mooimno", Integer.class);

    public QKanban(String variable) {
        super(Kanban.class, forVariable(variable));
    }

    public QKanban(Path<? extends Kanban> path) {
        super(path.getType(), path.getMetadata());
    }

    public QKanban(PathMetadata metadata) {
        super(Kanban.class, metadata);
    }

}


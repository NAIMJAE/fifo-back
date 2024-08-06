package kr.co.fifoBack.entity.gathering;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMooimMember is a Querydsl query type for MooimMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMooimMember extends EntityPathBase<MooimMember> {

    private static final long serialVersionUID = -1119544199L;

    public static final QMooimMember mooimMember = new QMooimMember("mooimMember");

    public final NumberPath<Integer> memberno = createNumber("memberno", Integer.class);

    public final NumberPath<Integer> mooimno = createNumber("mooimno", Integer.class);

    public final NumberPath<Integer> userno = createNumber("userno", Integer.class);

    public QMooimMember(String variable) {
        super(MooimMember.class, forVariable(variable));
    }

    public QMooimMember(Path<? extends MooimMember> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMooimMember(PathMetadata metadata) {
        super(MooimMember.class, metadata);
    }

}


package kr.co.fifoBack.entity.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSkill is a Querydsl query type for Skill
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSkill extends EntityPathBase<Skill> {

    private static final long serialVersionUID = -1788915623L;

    public static final QSkill skill = new QSkill("skill");

    public final StringPath languagename = createString("languagename");

    public final NumberPath<Integer> level = createNumber("level", Integer.class);

    public final NumberPath<Integer> sno = createNumber("sno", Integer.class);

    public final NumberPath<Integer> userno = createNumber("userno", Integer.class);

    public QSkill(String variable) {
        super(Skill.class, forVariable(variable));
    }

    public QSkill(Path<? extends Skill> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSkill(PathMetadata metadata) {
        super(Skill.class, metadata);
    }

}


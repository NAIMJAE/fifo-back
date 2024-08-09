package kr.co.fifoBack.entity.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QExperience is a Querydsl query type for Experience
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExperience extends EntityPathBase<Experience> {

    private static final long serialVersionUID = -1441245086L;

    public static final QExperience experience = new QExperience("experience");

    public final NumberPath<Integer> exeno = createNumber("exeno", Integer.class);

    public final StringPath job = createString("job");

    public final StringPath period = createString("period");

    public final StringPath skill = createString("skill");

    public final NumberPath<Integer> userno = createNumber("userno", Integer.class);

    public QExperience(String variable) {
        super(Experience.class, forVariable(variable));
    }

    public QExperience(Path<? extends Experience> path) {
        super(path.getType(), path.getMetadata());
    }

    public QExperience(PathMetadata metadata) {
        super(Experience.class, metadata);
    }

}


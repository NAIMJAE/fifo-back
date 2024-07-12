package kr.co.fifoBack.entity.grade;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QQuestion is a Querydsl query type for Question
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuestion extends EntityPathBase<Question> {

    private static final long serialVersionUID = -1633183544L;

    public static final QQuestion question = new QQuestion("question");

    public final BooleanPath compiler = createBoolean("compiler");

    public final StringPath explanation = createString("explanation");

    public final StringPath input = createString("input");

    public final StringPath languagename = createString("languagename");

    public final NumberPath<Integer> level = createNumber("level", Integer.class);

    public final StringPath output = createString("output");

    public final NumberPath<Integer> questionno = createNumber("questionno", Integer.class);

    public final StringPath title = createString("title");

    public final NumberPath<Integer> userno = createNumber("userno", Integer.class);

    public QQuestion(String variable) {
        super(Question.class, forVariable(variable));
    }

    public QQuestion(Path<? extends Question> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQuestion(PathMetadata metadata) {
        super(Question.class, metadata);
    }

}


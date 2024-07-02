package kr.co.fifoBack.entity.grade;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QQuestionExample is a Querydsl query type for QuestionExample
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuestionExample extends EntityPathBase<QuestionExample> {

    private static final long serialVersionUID = -697950334L;

    public static final QQuestionExample questionExample = new QQuestionExample("questionExample");

    public final StringPath answer = createString("answer");

    public final NumberPath<Integer> exampleno = createNumber("exampleno", Integer.class);

    public final StringPath input = createString("input");

    public final NumberPath<Integer> questionno = createNumber("questionno", Integer.class);

    public QQuestionExample(String variable) {
        super(QuestionExample.class, forVariable(variable));
    }

    public QQuestionExample(Path<? extends QuestionExample> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQuestionExample(PathMetadata metadata) {
        super(QuestionExample.class, metadata);
    }

}


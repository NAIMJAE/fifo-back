package kr.co.fifoBack.entity.grade;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QQuestionIOData is a Querydsl query type for QuestionIOData
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuestionIOData extends EntityPathBase<QuestionIOData> {

    private static final long serialVersionUID = 1300188344L;

    public static final QQuestionIOData questionIOData = new QQuestionIOData("questionIOData");

    public final StringPath input = createString("input");

    public final NumberPath<Integer> no = createNumber("no", Integer.class);

    public final StringPath output = createString("output");

    public final NumberPath<Integer> questionno = createNumber("questionno", Integer.class);

    public QQuestionIOData(String variable) {
        super(QuestionIOData.class, forVariable(variable));
    }

    public QQuestionIOData(Path<? extends QuestionIOData> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQuestionIOData(PathMetadata metadata) {
        super(QuestionIOData.class, metadata);
    }

}


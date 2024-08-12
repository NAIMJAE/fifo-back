package kr.co.fifoBack.entity.mooim;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDocument is a Querydsl query type for Document
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDocument extends EntityPathBase<Document> {

    private static final long serialVersionUID = -1918507485L;

    public static final QDocument document = new QDocument("document");

    public final StringPath content = createString("content");

    public final NumberPath<Integer> docno = createNumber("docno", Integer.class);

    public final NumberPath<Integer> mooimno = createNumber("mooimno", Integer.class);

    public final StringPath title = createString("title");

    public QDocument(String variable) {
        super(Document.class, forVariable(variable));
    }

    public QDocument(Path<? extends Document> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDocument(PathMetadata metadata) {
        super(Document.class, metadata);
    }

}


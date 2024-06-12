package kr.co.fifoBack.entity.post;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFile is a Querydsl query type for File
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFile extends EntityPathBase<File> {

    private static final long serialVersionUID = -472867137L;

    public static final QFile file = new QFile("file");

    public final DateTimePath<java.time.LocalDateTime> cDate = createDateTime("cDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> fno = createNumber("fno", Integer.class);

    public final NumberPath<Integer> pno = createNumber("pno", Integer.class);

    public final StringPath sName = createString("sName");

    public QFile(String variable) {
        super(File.class, forVariable(variable));
    }

    public QFile(Path<? extends File> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFile(PathMetadata metadata) {
        super(File.class, metadata);
    }

}


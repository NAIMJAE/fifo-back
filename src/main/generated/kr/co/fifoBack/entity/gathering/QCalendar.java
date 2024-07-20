package kr.co.fifoBack.entity.gathering;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCalendar is a Querydsl query type for Calendar
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCalendar extends EntityPathBase<Calendar> {

    private static final long serialVersionUID = -1967694736L;

    public static final QCalendar calendar = new QCalendar("calendar");

    public final StringPath bgcolor = createString("bgcolor");

    public final StringPath calendarid = createString("calendarid");

    public final NumberPath<Integer> calno = createNumber("calno", Integer.class);

    public final StringPath color = createString("color");

    public final DateTimePath<java.time.LocalDateTime> end = createDateTime("end", java.time.LocalDateTime.class);

    public final StringPath id = createString("id");

    public final BooleanPath isallday = createBoolean("isallday");

    public final BooleanPath isreadonly = createBoolean("isreadonly");

    public final StringPath location = createString("location");

    public final NumberPath<Integer> mooinno = createNumber("mooinno", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> start = createDateTime("start", java.time.LocalDateTime.class);

    public final StringPath state = createString("state");

    public final StringPath title = createString("title");

    public QCalendar(String variable) {
        super(Calendar.class, forVariable(variable));
    }

    public QCalendar(Path<? extends Calendar> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCalendar(PathMetadata metadata) {
        super(Calendar.class, metadata);
    }

}


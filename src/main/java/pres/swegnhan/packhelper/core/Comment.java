package pres.swegnhan.packhelper.core;

import lombok.*;
import org.joda.time.DateTime;


@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of={"uid"})
@ToString
public class Comment {

    private String uid;

    private String ip;

    private String content;

    private DateTime createAt;

    private String packUid;

}

package pres.swegnhan.packhelper.core;

import lombok.*;
import org.joda.time.DateTime;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "userIp")
@ToString
public class PushUserPackages {
   private String userIp;
   private String packUid;
   private String userMac;
   private String packName;
   private String taskName;
   private String description;
   private DateTime create_at;
}

package pres.swegnhan.packhelper.core;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "ip")
public class PushUserPackages {
    String iP;
    String packUid;
    String userMac;
    String packName;
}

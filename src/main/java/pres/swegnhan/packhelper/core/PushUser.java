package pres.swegnhan.packhelper.core;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "mac")
public class PushUser {
    private String mac;
    private String userName;
    private String ip;
}

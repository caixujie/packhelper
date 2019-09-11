package pres.swegnhan.packhelper.core;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = {"mac"})
public class User {
    private String ip;
    private String mac;
    private String name;
    private String level1;
    private String level2;
    private String level3;
}

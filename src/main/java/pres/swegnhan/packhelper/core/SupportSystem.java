package pres.swegnhan.packhelper.core;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Data
@EqualsAndHashCode
public class SupportSystem {

    @EqualsAndHashCode.Exclude
    private String uid;

    private String name;

    private String version;

    public SupportSystem(String input){
        this.name = input.substring(0, input.indexOf('|'));
        this.version = input.substring(input.indexOf('|') + 1);
    }

    public SupportSystem(String name, String version){
        this.uid = UUID.randomUUID().toString();
        this.name = name;
        this.version = version;
    }
}

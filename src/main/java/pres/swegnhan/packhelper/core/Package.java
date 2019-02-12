package pres.swegnhan.packhelper.core;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = {"uid"})
public class Package {

    private String uid;

    private String name;

    private String version;

    private int category;

    private String url;

    private String filetype;

    private List<SupportSystem> supsList;

    public Package(String name, String version, String category, String filetype ,String[] supsList){
        this.uid = UUID.randomUUID().toString();
        this.name = name;
        this.version = version;
        this.category = Integer.valueOf(category);
        this.filetype = filetype;
        this.supsList = Arrays.stream(supsList).collect(toSet()).stream().map(SupportSystem::new).collect(toList());
    }

    public Package(String name, String version, String category, String filetype ,SupportSystem[] supsList){
        this.uid = UUID.randomUUID().toString();
        this.name = name;
        this.version = version;
        this.category = Integer.valueOf(category);
        this.filetype = filetype;
        this.supsList = Arrays.asList(supsList);
    }

    public Package(String name, String version, String category, String url, String filetype ,String[] supsList){
        this.uid = UUID.randomUUID().toString();
        this.name = name;
        this.version = version;
        this.category = Integer.valueOf(category);
        this.url = url;
        this.filetype = filetype;
        this.supsList = Arrays.stream(supsList).collect(toSet()).stream().map(SupportSystem::new).collect(toList());
    }

}

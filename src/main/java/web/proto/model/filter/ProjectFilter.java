package web.proto.model.filter;

import java.time.LocalDate;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;
import web.proto.model.enums.Area;
import web.proto.model.enums.State;

@Getter
@Setter
@ToString
public class ProjectFilter {

    private String name;

    private String description;

    private Area area;

    private State state;

    private LocalDate dateStart;
    private LocalDate dateEnd;

}

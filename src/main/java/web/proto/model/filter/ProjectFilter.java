package web.proto.model.filter;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import web.proto.model.Associate;
import web.proto.model.enums.Area;
import web.proto.model.enums.State;

@Getter
@Setter
@ToString
public class ProjectFilter {
    
    private Long id;
    private Associate owner;
    private String name;
    private String description;
    private Area area;
    private State state;
    private LocalDate date;

}

package web.proto.model.filter;

import java.time.LocalDate;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;
import web.proto.model.enums.Role;

@Getter
@Setter
@ToString
public class AssociateFilter {
    
    private Long id;
    private String name;
    private String email;
    private String login;
    private Role role;
    private Boolean relevancy;
    private LocalDate dateCreated;

}

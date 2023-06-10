package web.proto.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.proto.model.enums.Area;
import web.proto.model.enums.Role;
import web.proto.model.enums.Status;

@Entity
@Data
@NoArgsConstructor
@Table(name = "associates")
public class Associate {

    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name="gerador1", sequenceName="associates_id_seq", allocationSize=1)
	@GeneratedValue(generator="gerador1", strategy=GenerationType.SEQUENCE)
    private long id;
    
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Login is required")
    private String login;

    @NotBlank(message = "Password is required")
    private String password;
    
    @Enumerated(EnumType.STRING)
    private Role role;
    
    private boolean relevancy;
    
    @ElementCollection
    @CollectionTable(name = "associate_interests", joinColumns = @JoinColumn(name = "associate_id"))
    @Size(min = 1, message = "Associate must have at least one role")
    @Enumerated(EnumType.STRING)
    private List<Area> interests;
    
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Enumerated(EnumType.STRING)
	private Status status = Status.ACTIVE;

}

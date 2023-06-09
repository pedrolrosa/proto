package web.proto.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import web.proto.model.enums.Area;
import web.proto.model.enums.License;
import web.proto.model.enums.State;

@Entity
@Data
@Table(name = "projects")
public class Project {

    @Id
    @SequenceGenerator(name="gerador3", sequenceName="usuario_codigo_seq", allocationSize=1)
	@GeneratedValue(generator="gerador3", strategy=GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Associate owner;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @Enumerated(EnumType.STRING)
    private Area area;

    @Enumerated(EnumType.STRING)
    private State state;

    @Enumerated(EnumType.STRING)
    private License license;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private List<Phase> phases;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate data;

}


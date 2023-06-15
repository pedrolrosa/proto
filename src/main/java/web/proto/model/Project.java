package web.proto.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import web.proto.model.enums.Area;
import web.proto.model.enums.License;
import web.proto.model.enums.State;

@Entity
@Data
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "associate_id")
    @NotNull
    private Associate associate;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Area area;

    @NotNull
    @Enumerated(EnumType.STRING)
    private State state;

    @NotNull
    @Enumerated(EnumType.STRING)
    private License license;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private List<Phase> phases;

    @DateTimeFormat
    @Column(name = "date_created")
    private LocalDate dateCreated;

    private boolean active;

}


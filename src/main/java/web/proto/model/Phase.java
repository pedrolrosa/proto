package web.proto.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import web.proto.model.enums.Status;

@Entity
@Data
@Table(name = "phases")
public class Phase {

    @Id
    @SequenceGenerator(name="gerador2", sequenceName="usuario_codigo_seq", allocationSize=1)
	@GeneratedValue(generator="gerador2", strategy=GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @Lob
    private byte[] content;

    @Enumerated(EnumType.STRING)
	private Status status = Status.ATIVO;

}


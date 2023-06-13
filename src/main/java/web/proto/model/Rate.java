package web.proto.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "rates")
public class Rate {

    @Id
    @SequenceGenerator(name="gerador4", sequenceName="rates_id_seq", allocationSize=1)
	@GeneratedValue(generator="gerador4", strategy=GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    @JoinColumn(name = "associate_id")
    private Associate associate;

    @OneToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private Integer score;

}


package web.proto.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import web.proto.model.id.RateId;

@Entity
@Data
@IdClass(RateId.class)
@Table(name = "rates")
public class Rate {

    @Id
    @ManyToOne
    @JoinColumn(name = "associate_id")
    private Associate associate;

    @Id
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private Integer score;

}

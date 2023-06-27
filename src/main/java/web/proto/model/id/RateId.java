package web.proto.model.id;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RateId implements Serializable {
    private Long associate;
    private Long project;

    // Construtores, getters e setters
}

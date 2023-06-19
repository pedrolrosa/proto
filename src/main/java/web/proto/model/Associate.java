package web.proto.model;

import java.time.LocalDate;
import java.util.List;

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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import web.proto.model.enums.Area;
import web.proto.model.enums.Role;
import web.proto.service.associates.AssociateLoginService;
import web.proto.validation.UniqueValueAttribute;

@Entity
@Data
@Table(name = "associates", uniqueConstraints = @UniqueConstraint(columnNames =  "login", name = "unique_login"))
@UniqueValueAttribute(attribute = "login", service = AssociateLoginService.class, message = "Já existe um nome de usuário igual a este cadastrado")
public class Associate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Name is required")
    private String name;

    @Email
    @NotBlank(message = "Email is required")
    private String email;

    @Column(unique = true)
    @NotBlank(message = "Login is required")
    private String login;

    @NotBlank(message = "Password is required")
    private String password;
    
    @Enumerated(EnumType.STRING)
    private Role role;
    
    private boolean relevancy;
    
    // Lista de áreas de interesse do associado, persistida em uma tabela separada (nao eh tabela principal)
    // *interests nao possui entidade* //
    @ElementCollection
    @CollectionTable(name = "associate_interests", joinColumns = @JoinColumn(name = "associate_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "interest")
    private List<Area> interests;
    
    @Column(name = "date_created")
    private LocalDate dateCreated;

    private boolean active;

}

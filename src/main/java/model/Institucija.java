package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Index;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@Entity
@Table(name = "Institucija")
public class Institucija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InstitucijaID")
    private int id;

    @Column(name = "Ime", nullable = false, unique = true)
    @NotNull
    private String ime;

    @Column(name = "Grad")
    @Index(name = "idx_institucija_grad")
    private String grad;

    @Column(name = "Drzava")
    private String drzava;

}

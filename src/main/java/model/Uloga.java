package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.hibernate.annotations.Index;

@Getter
@Setter
@Entity
@Table(name = "Uloga")
public class Uloga {

    public Uloga() {}


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UlogaID")
    private int id;

    @Column(name = "Naziv", nullable = false, unique = true)
    @NotNull
    private String naziv;

    @ManyToOne(optional = false)
    @JoinColumn(name = "KategorijaID", nullable = false)
    @NotNull
    @Index(name = "idx_uloga_kategorija")
    private KategorijaIstrazivaca kategorija;
}

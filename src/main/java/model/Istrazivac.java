package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.hibernate.annotations.Index;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Istrazivac")
public class Istrazivac {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IstrazivacID")
    private int id;

    @Column(name = "Ime", nullable = false)
    private String ime;

    @Column(name = "Prezime", nullable = false)
    private String prezime;

    @Column(name = "GradRodjenja")
    @Index(name = "idx_istrazivac_grad")
    private String gradRodjenja;

    @Column(name = "DrzavaRodjenja")
    private String drzavaRodjenja;

    @Column(name = "NivoObrazovanja")
    private String nivoObrazovanja;

    @ManyToOne(optional = false)
    @JoinColumn(name = "InstitucijaID")
    private Institucija institucija;

    @OneToMany(mappedBy = "istrazivac", fetch = FetchType.EAGER)
    private Set<MisijaIstrazivac> misije;

    @ManyToOne(optional = true)
    @JoinColumn(name = "KategorijaID")
    private KategorijaIstrazivaca kategorija;

}

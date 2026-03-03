package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Index;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "NebeskoTelo")
public class NebeskoTelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TeloID")
    private int id;

    @Column(name = "Ime", nullable = false, unique = true)
    @NotNull
    @Index(name = "idx_telo_ime")
    private String ime;

    @Column(name = "Tip")
    private String tip;

    @Column(name = "ProsecniRadijus")
    private Double prosecniRadijus;

    @Column(name = "EkvatorijalniRadijus")
    private Double ekvatorijalniRadijus;

    @Column(name = "PolarniRadijus")
    private Double polarniRadijus;

    @Column(name = "Zapremina")
    private Double zapremina;

    @Column(name = "Masa")
    private Double masa;

    @Column(name = "MinTemp")
    private Double minTemp;

    @Column(name = "MaxTemp")
    private Double maxTemp;

    @Column(name = "RotacijaTrajanje")
    private Double rotacijaTrajanje;

    @Column(name = "RevolucijaTrajanje")
    private Double revolucijaTrajanje;

    @ManyToOne(optional = true)
    @JoinColumn(name = "TeloOkretanjaID")
    private NebeskoTelo teloOkretanja;

    @ManyToOne(optional = false)
    @JoinColumn(name = "GalaksijaID", nullable = false)
    @NotNull
    private Galaksija galaksija;

    @ManyToMany(mappedBy = "istrazenaNebeskaTela")
    private Set<Misija> misije;

    @OneToMany(mappedBy = "teloOkretanja", fetch = FetchType.LAZY)
    private Set<NebeskoTelo> podredjenaTela;
}

package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Galaksija")
public class Galaksija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GalaksijaID")
    private int id;

    @Column(name = "Ime", nullable = false, unique = true)
    @org.hibernate.annotations.Index(name = "idx_galaksija_ime")
    @NotNull
    private String ime;

    @Column(name = "Masa")
    private Double masa;

    @Column(name = "ProsecniPrecnik")
    private Double prosecniPrecnik;

    @Column(name = "UgaoniMomenat")
    private Double ugaoniMomenat;

    @ManyToMany
    @JoinTable(
            name = "GalaksijaMorfoloskiTip",
            joinColumns = @JoinColumn(name = "GalaksijaID"),
            inverseJoinColumns = @JoinColumn(name = "MorfoloskiTipID")
    )
    private Set<MorfoloskiTip> morfoloskiTipovi;

    @OneToMany(mappedBy = "galaksija", fetch = FetchType.LAZY)
    private Set<NebeskoTelo> nebeskaTela;


}

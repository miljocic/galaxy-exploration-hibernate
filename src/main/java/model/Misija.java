package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;
import org.hibernate.annotations.Index;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@Entity
@Table(name = "Misija")
public class Misija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MisijaID")
    private int id;

    @Column(name = "MisijaIme", unique = true)
    @Index(name = "idx_misija_ime")
    private String ime;

    @Column(name = "Cilj")
    private String cilj;

    @Column(name = "Ishod")
    private String ishod;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    @NotNull
    private Status status;

    public enum Status {
        U_PLANU, ZAPOCETA, USPESNA, NEUSPESNA
    }

    @ManyToMany
    @JoinTable(
            name = "MisijaCilj",
            joinColumns = @JoinColumn(name = "MisijaID"),
            inverseJoinColumns = @JoinColumn(name = "TeloID")
    )
    private Set<NebeskoTelo> istrazenaNebeskaTela;

    @OneToMany(mappedBy = "misija")
    private Set<MisijaIstrazivac> ucesnici;


}

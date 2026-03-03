package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Index;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@Entity
@Table(name = "MisijaIstrazivac",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"MisijaID", "IstrazivacID", "UlogaID"})
        }
)
public class MisijaIstrazivac {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "MisijaID", nullable = false)
    @NotNull
    @Index(name = "idx_mi_misija")
    private Misija misija;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IstrazivacID", nullable = false)
    @NotNull
    @Index(name = "idx_mi_istrazivac")
    private Istrazivac istrazivac;

    @ManyToOne(optional = false)
    @JoinColumn(name = "UlogaID", nullable = false)
    @NotNull
    @Index(name = "idx_mi_uloga")
    private Uloga uloga;
}



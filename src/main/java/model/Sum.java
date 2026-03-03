package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Index;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@Entity
@Table(name = "Sum")
public class Sum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SumID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "MisijaID")
    @Index(name = "idx_sum_misija")
    private Misija misija;

    @ManyToOne
    @JoinColumn(name = "IstrazivacID")
    private Istrazivac istrazivac;

    @ManyToOne
    @JoinColumn(name = "TeloID")
    private NebeskoTelo telo;

    @Column(name = "Podatak")
    private String podatak;

}

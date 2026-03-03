package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "MorfoloskiTipovi")
public class MorfoloskiTip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MorfoloskiTipID")
    private int id;

    @Column(name = "TipIme", nullable = false, unique = true)
    @NotNull
    @org.hibernate.annotations.Index(name = "id_tipIme")
    private String tipIme;

    @ManyToMany(mappedBy = "morfoloskiTipovi")
    private Set<Galaksija> galaksije;

}

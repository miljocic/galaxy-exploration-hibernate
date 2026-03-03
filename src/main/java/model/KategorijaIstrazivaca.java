package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Index;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@Entity
@Table(name = "KategorijaIstrazivaca")
public class KategorijaIstrazivaca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KategorijaID")
    private int id;

    @Column(name = "KategorijaIme", nullable = false, unique = true)
    @NotNull
    @Index(name = "idx_kategorija_ime")
    private String kategorijaIme;

    @Column(name = "IdeUSvemir", nullable = false)
    @NotNull
    private boolean ideUSvemir;


}

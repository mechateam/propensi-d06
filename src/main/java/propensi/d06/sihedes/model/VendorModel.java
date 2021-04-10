package propensi.d06.sihedes.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name="vendor")
public class VendorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_vendor;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Column(name = "no_telp", nullable = false)
    private Integer no_telp;
}

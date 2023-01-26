package konex.innovation.medicine_administration.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
@Table(name = "medicine")
public class Medicine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotBlank
    @Size(min = 2, max = 150)
    private String name;

    @Column(name = "factory_laboratory")
    @NotBlank
    @Size(min = 2, max = 255)
    private String factoryLaboratory;

    @Column(name = "manufacturing_date")
    @PastOrPresent
    @NotNull
    private LocalDate manufacturingDate;

    @Column(name = "due_date")
    @FutureOrPresent
    @NotNull
    private LocalDate dueDate;

    @Column(name = "stock")
    @Min(value = 0L)
    @NotNull
    Integer stock;

    @Column(name = "unit_price")
    @DecimalMin(value = "0.0")
    @NotNull
    Double unitPrice;

    @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // Relations
    @OneToMany(mappedBy = "medicine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sale> sales;

}

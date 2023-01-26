package konex.innovation.medicine_administration.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @NotBlank
    @PastOrPresent
    private LocalDate manufacturingDate;

    @Column(name = "due_date")
    @NotBlank
    @PastOrPresent
    private LocalDate dueDate;

    @Column(name = "stock")
    @NotBlank
    @Min(value = 0L)
    Short stock;

    @Column(name = "unit_price")
    @NotBlank
    @DecimalMin(value = "0.0")
    Double unitPrice;

    @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}

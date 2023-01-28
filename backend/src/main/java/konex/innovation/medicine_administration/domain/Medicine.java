package konex.innovation.medicine_administration.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.apache.commons.math3.util.Precision;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "medicine")
@NoArgsConstructor
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
    @NotNull
    private Long manufacturingDate;

    @Column(name = "due_date")
    @NotNull
    private Long dueDate;

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

    // Custom Setters
    public void setUnitPrice(Double value) {
        this.unitPrice = Precision.round(value, 3);
    }

}

package konex.innovation.medicine_administration.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
@Table(name = "sale")
public class Sale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "sale_datetime")
    @NotBlank
    private LocalDateTime saleDateTime;

    @Column(name = "quantity")
    @Min(value = 0L)
    @NotBlank
    private Short quantity;

    @Column(name = "total_price")
    @NotBlank
    @DecimalMin(value = "0.0")
    Double totalPrice;

    @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // Relations
    @ManyToOne()
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

}
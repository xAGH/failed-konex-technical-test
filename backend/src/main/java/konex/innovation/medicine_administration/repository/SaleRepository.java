package konex.innovation.medicine_administration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import konex.innovation.medicine_administration.domain.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("select s from Sale s where s.deletedAt IS NULL AND s.id = ?1")
    public Optional<Sale> findById(Long id);
}

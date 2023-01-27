package konex.innovation.medicine_administration.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import konex.innovation.medicine_administration.domain.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("select s from Sale s where s.saleDateTime>=?1 AND s.saleDateTime<=?2")
    public Page<Sale> findBewteenDates(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
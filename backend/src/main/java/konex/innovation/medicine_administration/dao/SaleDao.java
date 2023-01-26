package konex.innovation.medicine_administration.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import konex.innovation.medicine_administration.domain.Sale;

public interface SaleDao extends JpaRepository<Sale, Long> {
}

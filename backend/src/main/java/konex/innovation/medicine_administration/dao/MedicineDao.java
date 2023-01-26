package konex.innovation.medicine_administration.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import konex.innovation.medicine_administration.domain.Medicine;

public interface MedicineDao extends JpaRepository<Medicine, Long> {
}

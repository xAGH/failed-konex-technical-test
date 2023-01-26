package konex.innovation.medicine_administration.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import konex.innovation.medicine_administration.domain.Medicine;

public interface MedicineDao extends JpaRepository<Medicine, Long> {
    @Query("select m from Medicine m where m.name = ?1")
    public Medicine findByName(String name);
}

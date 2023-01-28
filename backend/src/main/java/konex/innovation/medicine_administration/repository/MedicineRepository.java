package konex.innovation.medicine_administration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import konex.innovation.medicine_administration.domain.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Long>, JpaSpecificationExecutor<Medicine> {
    @Query("select m from Medicine m where m.name = ?1 AND m.factoryLaboratory = ?2")
    public Medicine matchByNameAndByLaboratory(String name, String laboratory);

}

package konex.innovation.medicine_administration.repository;

// import java.util.List;
// import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import konex.innovation.medicine_administration.domain.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Long>, JpaSpecificationExecutor<Medicine> {
    @Query("select m from Medicine m where m.name = ?1 AND m.factoryLaboratory = ?2")
    public Medicine matchByNameAndByLaboratory(String name, String laboratory);

    // @Query("select m from Medicine m where m.deletedAt IS NULL")
    // public List<Medicine> findAll();

    // @Query("select m from Medicine m where m.deletedAt IS NULL AND m.id = ?1")
    // public Optional<Medicine> findById(Long id);

}

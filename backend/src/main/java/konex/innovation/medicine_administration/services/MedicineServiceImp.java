package konex.innovation.medicine_administration.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import konex.innovation.medicine_administration.dao.MedicineDao;
import konex.innovation.medicine_administration.domain.Medicine;

@Service
public class MedicineServiceImp implements CrudService<Medicine> {

    @Resource
    private MedicineDao dao;

    @Override
    public List<Medicine> list() {
        ArrayList<Medicine> medicines = (ArrayList<Medicine>) dao.findAll();
        for (Medicine medicine : medicines) {
            // Verifica que los datos traidos no tengan fecha de eliminacion
            if (!medicine.getDeletedAt().isEqual(null)) {
                medicines.remove(medicine);
            }
        }
        return medicines;
    }

    @Override
    public Medicine findById(Long id) {
        return dao.findById(id).orElse(null);
    }

    @Override
    public Medicine save(Medicine entity) {
        return dao.save(entity);
    }

    @Override
    public Medicine delete(Medicine entity) {
        entity.setDeletedAt(LocalDateTime.now());
        return dao.save(entity);
    }

}

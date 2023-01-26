package konex.innovation.medicine_administration.services;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import konex.innovation.medicine_administration.dao.MedicineDao;
import konex.innovation.medicine_administration.domain.Medicine;

@Service
public class MedicineServiceImp implements MedicineService {

    @Resource
    private MedicineDao dao;

    @Transactional(readOnly = true)
    @Override
    public ArrayList<Medicine> list() {
        ArrayList<Medicine> medicines = (ArrayList<Medicine>) dao.findAll();
        return medicines;
    }

    @Override
    @Transactional(readOnly = true)
    public Medicine findById(Long id) {
        return dao.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Medicine findByName(String name) {
        return dao.findByName(name);
    }

    @Override
    @Transactional
    public Medicine save(Medicine entity) {
        return dao.save(entity);
    }

    @Override
    @Transactional
    public Medicine delete(Medicine entity) {
        entity.setDeletedAt(LocalDateTime.now());
        return dao.save(entity);
    }

}

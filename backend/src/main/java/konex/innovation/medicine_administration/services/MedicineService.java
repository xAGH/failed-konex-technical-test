package konex.innovation.medicine_administration.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.javafaker.Faker;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import konex.innovation.medicine_administration.domain.Medicine;
import konex.innovation.medicine_administration.repository.MedicineRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicineService {

    @Resource
    private MedicineRepository repository;

    // Paginacion Con Filtros
    @Transactional(readOnly = true)
    public Page<Medicine> list(Integer page, Integer offset, String sortBy, List<String> filterBy, List<Object> value) {
        Specification<Medicine> spec = (db, query, field) -> field.equal(db.get(filterBy.get(0)), value.get(0));

        if (filterBy.size() > 0) {
            Specification<Medicine> extras;
            for (Integer i = 0; i < filterBy.size(); i++) {
                final Integer index = i;
                extras = (db, query, field) -> field.equal(db.get(filterBy.get(index)),
                        value.get(index));
                spec = spec.and(extras);
            }
        }
        Pageable pagination = PageRequest.of(page, offset).withSort(Sort.by(sortBy));
        Page<Medicine> medicines = repository.findAll(spec, pagination);
        return medicines;
    }

    // Paginacion Sin Filtros
    @Transactional(readOnly = true)
    public Page<Medicine> list(Integer page, Integer offset, String sortBy) {
        return repository.findAll(PageRequest.of(page, offset).withSort(Sort.by(sortBy)));
    }

    @Transactional(readOnly = true)
    public Medicine findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Medicine matchByNameAndByLaboratory(String name, String laboratory) {
        return repository.matchByNameAndByLaboratory(name, laboratory);
    }

    @Transactional
    public Medicine save(Medicine entity) {
        return repository.save(entity);
    }

    @Transactional
    public Medicine delete(Medicine entity) {
        entity.setDeletedAt(LocalDateTime.now());
        return repository.save(entity);
    }

    @Transactional
    public void saveAll(List<Medicine> entities) {
        repository.saveAll(entities);
    }

    @PostConstruct
    public void createData() {
        Faker faker = new Faker();
        ArrayList<Medicine> data = new ArrayList<Medicine>();
        for (int i = 0; i < 50; i++) {
            Medicine medicine = new Medicine();
            medicine.setName(faker.name().lastName());
            medicine.setFactoryLaboratory(faker.company().name());
            medicine.setManufacturingDate(
                    faker.date().past(365, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            medicine.setDueDate(
                    faker.date().future(365, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            medicine.setStock(new Random().nextInt(1, 200));
            medicine.setUnitPrice(new Random().nextDouble(1000, 10000));
            data.add(medicine);
        }
        saveAll(data);
    }

}

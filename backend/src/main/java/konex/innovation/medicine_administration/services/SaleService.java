package konex.innovation.medicine_administration.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import konex.innovation.medicine_administration.domain.Sale;
import konex.innovation.medicine_administration.repository.SaleRepository;

@Service
public class SaleService {

    @Resource
    private SaleRepository repository;

    @Transactional(readOnly = true)
    public List<Sale> list() {
        ArrayList<Sale> sales = (ArrayList<Sale>) repository.findAll();
        for (Sale sale : sales) {
            // Verifica que los datos traidos no tengan fecha de eliminacion
            if (!sale.getDeletedAt().isEqual(null)) {
                sales.remove(sale);
            }
        }
        return sales;
    }

    @Transactional(readOnly = true)
    public Sale findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public Sale save(Sale entity) {
        return repository.save(entity);
    }

    @Transactional
    public Sale delete(Sale entity) {
        entity.setDeletedAt(LocalDateTime.now());
        return repository.save(entity);
    }

    public void saveAll(List<Sale> entities) {
        repository.saveAll(entities);
    }

}

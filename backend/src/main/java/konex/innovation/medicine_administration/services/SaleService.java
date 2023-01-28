package konex.innovation.medicine_administration.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public Page<Sale> list(Integer page, Integer offset, String sortBy) {
        Page<Sale> sales = repository.findAll(PageRequest.of(page, offset).withSort(Sort.by(sortBy)));
        return sales;
    }

    @Transactional(readOnly = true)
    public Sale findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public Page<Sale> findBewteenDates(
            Integer page,
            Integer offset,
            String sortBy,
            Long start,
            Long end) {
        return repository.findBewteenDates(start, end, PageRequest.of(page, offset).withSort(Sort.by(sortBy)));
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

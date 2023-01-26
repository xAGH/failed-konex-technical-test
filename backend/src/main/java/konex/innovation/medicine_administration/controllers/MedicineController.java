package konex.innovation.medicine_administration.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import konex.innovation.medicine_administration.domain.Medicine;
import konex.innovation.medicine_administration.dto.web.ResponseDto;
import konex.innovation.medicine_administration.services.MedicineServiceImp;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v01/medicine")
public class MedicineController extends ExceptionHandlerController {

    @Resource
    private MedicineServiceImp service;

    @GetMapping()
    public ResponseEntity<ResponseDto> getAll() {
        ArrayList<Medicine> medicines = service.list();
        log.info("Medicine created: " + medicines.toString());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(true, "Medicines List", medicines));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getOne(@PathVariable Long id) {
        Medicine medicine = service.findById(id);
        if (medicine == null) {
            return notFound(id);
        }

        log.info(String.format("Medicine found with id %d: %s", id, medicine.toString()));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(true, null, medicine));
    }

    @PostMapping
    public ResponseEntity<ResponseDto> save(@Valid @RequestBody Medicine body) {
        Medicine medicine = service.findByName(body.getName());
        String message;
        if (medicine != null) {
            Integer previousStock = medicine.getStock();
            medicine.setStock(previousStock + body.getStock());
            medicine.setUpdatedAt(LocalDateTime.now());
            message = "The medicine alredy exists and the stock has been added";
            log.info(String.format("The medicine stock with name %s has been changed from %d to %d", medicine.getName(),
                    previousStock, medicine.getStock()));
        } else {
            medicine = body;
            message = "The medicine has been created";
            log.info(String.format("The medicine created: %s", medicine.toString()));
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(true, message, service.save(medicine)));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> update(@PathVariable Long id, @Valid @RequestBody Medicine body) {
        Medicine medicine = service.findById(id);
        if (medicine == null) {
            return notFound(id);
        }

        medicine.setName(body.getName());
        medicine.setFactoryLaboratory(body.getFactoryLaboratory());
        medicine.setManufacturingDate(body.getManufacturingDate());
        medicine.setDueDate(body.getDueDate());
        medicine.setStock(body.getStock());
        medicine.setUnitPrice(body.getUnitPrice());

        log.info(String.format("Medicine with id %d has been updated. New info: %s", id, body.toString()));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(true, "Medicine has been updated", service.save(medicine)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> delete(@PathVariable Long id) {
        Medicine medicine = service.findById(id);
        if (medicine == null) {
            return notFound(id);
        }
        medicine.setDeletedAt(LocalDateTime.now());
        log.info(String.format("Medicine with id %d has been deleted. Data: %s", id, medicine.toString()));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(true, "Medicine has been deleted", service.save(medicine)));
    }

    private ResponseEntity<ResponseDto> notFound(Long id) {
        log.info(String.format("Medicine with id %d not found", id));
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseDto(false, "Medicine not found", null));
    }

}

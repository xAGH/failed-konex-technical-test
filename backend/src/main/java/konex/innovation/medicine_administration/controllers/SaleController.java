package konex.innovation.medicine_administration.controllers;

import java.util.Arrays;

import org.apache.commons.math3.util.Precision;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import konex.innovation.medicine_administration.domain.Medicine;
import konex.innovation.medicine_administration.domain.Sale;
import konex.innovation.medicine_administration.dto.web.ResponseDto;
import konex.innovation.medicine_administration.services.MedicineService;
import konex.innovation.medicine_administration.services.SaleService;

@RestController
@RequestMapping("/api/v01/sale")
public class SaleController extends ExceptionHandlerController {

    @Resource
    private SaleService service;

    @Resource
    private MedicineService medicineService;

    @GetMapping()
    public ResponseEntity<ResponseDto> getAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "offset", defaultValue = "10") Integer offset,
            @RequestParam(value = "sortBy", defaultValue = "") String sortBy,
            @RequestParam(value = "filterStartDate", defaultValue = "0") Long filterStartDate,
            @RequestParam(value = "filterEndDate", defaultValue = "0") Long filterEndDate) {

        String[] fieldsToSort = new String[] {
                "id",
                "saleDateTime",
                "quantity",
                "unitPrice",
                "totalPrice",
                "medicineId"
        };
        sortBy = Arrays.stream(fieldsToSort).anyMatch(sortBy::equals) ? sortBy : "id";

        if (filterStartDate != 0 && filterEndDate != 0) {
            Page<Sale> sales = service.findBewteenDates(page, offset,
                    sortBy, filterStartDate,
                    filterEndDate);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(true, "Sales List", sales));
        }

        Page<Sale> sales = service.list(page, offset, sortBy);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(true, "Sales List", sales));
    }

    // Endpoint para calcular el valor total de una compra
    @GetMapping("/{medicine_id}/{quantity}")
    public ResponseEntity<ResponseDto> calculateTotalPrice(@PathVariable Long medicine_id,
            @PathVariable Integer quantity) {
        Medicine medicine = medicineService.findById(medicine_id);
        if (medicine == null) {
            return medicineNotFound(medicine_id);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(true, "Total price calculated",
                        Precision.round((medicine.getUnitPrice() * quantity), 3)));
    }

    @PostMapping
    public ResponseEntity<ResponseDto> save(@Valid @RequestBody Sale body) {
        Medicine medicine = medicineService.findById(body.getMedicineId());

        // Se valida si el medicamento indicado existe
        if (medicine == null) {
            return medicineNotFound(body.getMedicineId());
        }

        // Se valida si el medicamento tiene suficiente stock
        if (medicine.getStock() < body.getQuantity()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(false,
                            String.format("Medicine with id %d has not enough stock. Current stock: %d ",
                                    body.getMedicineId(), medicine.getStock()),
                            null));
        }

        body.setUnitPrice(medicine.getUnitPrice());
        body.setTotalPrice(Precision.round(body.getQuantity() * body.getUnitPrice(), 3));
        medicine.setStock(medicine.getStock() - body.getQuantity());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(true, "Sale has been registered", service.save(body)));

    }

    private ResponseEntity<ResponseDto> medicineNotFound(Long id) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseDto(false, String.format("Medicine with id %d not found", id),
                        null));
    }

}

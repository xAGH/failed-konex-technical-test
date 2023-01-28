package konex.innovation.medicine_administration.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import com.github.javafaker.Faker;
import jakarta.annotation.Resource;
import konex.innovation.medicine_administration.domain.Medicine;
import konex.innovation.medicine_administration.domain.Sale;
import konex.innovation.medicine_administration.dto.web.ResponseDto;
import konex.innovation.medicine_administration.services.MedicineService;
import konex.innovation.medicine_administration.services.SaleService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.math3.util.Precision;

@RestController
@RequestMapping("/api/v01")
public class MockController extends ExceptionHandlerController {

    @Resource
    MedicineService medicineService;

    @Resource
    SaleService saleService;

    @GetMapping("/create-data/")
    public ResponseEntity<ResponseDto> createData(
            @RequestParam(value = "medicineDataQuantity", defaultValue = "50") Short medicineDataQuantity,
            @RequestParam(value = "saleDataQuantity", defaultValue = "50") Short saleDataQuantity) {
        Faker faker = new Faker();
        ArrayList<Medicine> medicineData = new ArrayList<Medicine>();
        for (int i = 0; i < medicineDataQuantity; i++) {
            Medicine medicine = new Medicine();
            medicine.setName(faker.name().lastName());
            medicine.setFactoryLaboratory(faker.company().name());
            medicine.setManufacturingDate(
                    faker.date().past(365, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toEpochSecond());
            medicine.setDueDate(
                    faker.date().future(365, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toEpochSecond());
            medicine.setStock(rand(200, 400));
            medicine.setUnitPrice(new Random().nextDouble(1000, 10000));
            medicineData.add(medicine);
        }
        medicineService.saveAll(medicineData);

        ArrayList<Sale> saleData = new ArrayList<Sale>();
        for (int i = 0; i < saleDataQuantity; i++) {
            Sale sale = new Sale();
            sale.setSaleDateTime(LocalDateTime.of(
                    rand(2020, 2022), // Random year
                    rand(1, 12), // Random month
                    rand(1, 28), // Random day
                    rand(1, 24), // Random hour
                    rand(1, 60), // Random minute
                    rand(1, 60)).toEpochSecond(ZoneOffset.UTC)); // Random second
            sale.setQuantity(rand(1, 20));
            sale.setUnitPrice(Precision.round(new Random().nextDouble(1000, 10000), 3));
            sale.setTotalPrice(sale.getUnitPrice() * sale.getQuantity());
            int intid = rand(1, 50);
            long longid = intid;
            sale.setMedicineId(longid);
            saleData.add(sale);
        }
        saleService.saveAll(saleData);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(true, "Data created successfully", null));
    }

    public Integer rand(Integer min, Integer max) {
        return new Random().nextInt(min, max);
    }

}

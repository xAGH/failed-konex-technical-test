package konex.innovation.medicine_administration;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.math3.util.Precision;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.javafaker.Faker;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import konex.innovation.medicine_administration.domain.Medicine;
import konex.innovation.medicine_administration.domain.Sale;
import konex.innovation.medicine_administration.services.MedicineService;
import konex.innovation.medicine_administration.services.SaleService;

@SpringBootApplication
public class MedicineAdministrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicineAdministrationApplication.class, args);
    }

    // Datos para Facilitar pruebas
    @PostConstruct
    public void createData() {
        createMedicines();
        createSales();
    }

    @Resource
    MedicineService medicineService;

    @Resource
    SaleService saleService;

    public void createMedicines() {
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
            medicine.setStock(rand(200, 400));
            medicine.setUnitPrice(new Random().nextDouble(1000, 10000));
            data.add(medicine);
        }
        medicineService.saveAll(data);
    }

    public void createSales() {
        ArrayList<Sale> data = new ArrayList<Sale>();
        for (int i = 0; i < 50; i++) {
            Sale sale = new Sale();
            sale.setSaleDateTime(LocalDateTime.of(
                    rand(2020, 2022), // Random year
                    rand(1, 12), // Random month
                    rand(1, 28), // Random day
                    rand(1, 24), // Random hour
                    rand(1, 60), // Random minute
                    rand(1, 60))); // Random second
            sale.setQuantity(rand(1, 20));
            sale.setUnitPrice(Precision.round(new Random().nextDouble(1000, 10000), 3));
            sale.setTotalPrice(sale.getUnitPrice() * sale.getQuantity());
            int intid = rand(1, 50);
            long longid = intid;
            sale.setMedicineId(longid);
            data.add(sale);
        }
        saleService.saveAll(data);
    }

    public Integer rand(Integer min, Integer max) {
        return new Random().nextInt(min, max);
    }
}

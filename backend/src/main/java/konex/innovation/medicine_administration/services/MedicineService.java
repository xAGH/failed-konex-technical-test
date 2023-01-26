package konex.innovation.medicine_administration.services;

import konex.innovation.medicine_administration.domain.Medicine;

public interface MedicineService extends CrudService<Medicine> {

    public Medicine findByName(String name);

}

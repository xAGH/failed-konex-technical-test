import { Component } from '@angular/core';
import { MedicineService } from '../../services/medicine.service';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Medicine } from '../../interfaces/Medicine';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { trimStringValidator } from 'src/app/validators/trim-string.validator';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-upsert-medicine',
  templateUrl: './upsert-medicine.component.html',
  styleUrls: ['./upsert-medicine.component.css'],
})
export class UpsertMedicineComponent {
  form: FormGroup;

  loaded: boolean;

  id: number;

  medicine: Medicine;

  constructor(
    private medicineService: MedicineService,
    private messageService: MessageService,
    private fb: FormBuilder,
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig
  ) {
    this.loaded = false;
    this.id = this.config.data.id;
    this.medicine = null!;
    this.form = this.fb.group({
      name: [
        '',
        [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(150),
          trimStringValidator(),
        ],
      ],
      factoryLaboratory: [
        '',
        [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(255),
          trimStringValidator(),
        ],
      ],
      manufacturingDate: ['', [Validators.required]],
      dueDate: ['', [Validators.required]],
      stock: [null, [Validators.required, Validators.min(0)]],
      unitPrice: [null, [Validators.required, Validators.min(0)]],
    });
  }

  ngOnInit() {
    if (this.id != null) {
      this.medicineService.getMedicine(this.id).subscribe({
        next: (data) => {
          this.medicine = data;
          this.loaded = true;
          this.form.setValue({
            name: data.name,
            factoryLaboratory: data.factoryLaboratory,
            manufacturingDate: new Date(data.manufacturingDate),
            dueDate: new Date(data.dueDate),
            stock: data.stock,
            unitPrice: data.unitPrice,
          });
        },
      });
    } else this.loaded = true;
  }

  close() {
    this.ref.close();
  }

  upsert() {
    const medicine: Medicine = { ...this.form.value };
    medicine.dueDate = new Date(medicine.dueDate).getTime() as unknown as Date;
    medicine.manufacturingDate = new Date(
      medicine.manufacturingDate
    ).getTime() as unknown as Date;
    if (this.id) {
      this.medicineService
        .updateMedicine(medicine, this.medicine.id!)
        .subscribe({
          next: () => {
            this.messageService.add({
              severity: 'success',
              summary: 'Medicamento actualizado',
            });
            this.close();
          },
          error: (error) => {
            console.log(error);

            this.messageService.add({
              severity: 'error',
              summary: 'Ha ocurrido un error: ' + error.error.message,
            });
          },
        });
      return;
    }
    this.medicineService.createMedicine(medicine).subscribe({
      next: () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Medicamento creado',
        });
        this.close();
      },
      error: (error) => {
        console.log(error);

        this.messageService.add({
          severity: 'error',
          summary: 'Ha ocurrido un error: ' + error.error.message,
        });
      },
    });
  }
}

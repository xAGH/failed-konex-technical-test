import { Component } from '@angular/core';
import { MedicineService } from '../../services/medicine.service';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Medicine } from '../../interfaces/Medicine';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { trimStringValidator } from 'src/app/validators/trim-string.validator';
import { MessageService } from 'primeng/api';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-update-medicine',
  templateUrl: './update-medicine.component.html',
  styleUrls: ['./update-medicine.component.css'],
})
export class UpdateMedicineComponent {
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
      stock: ['', [Validators.required, Validators.min(0)]],
      unitPrice: ['', [Validators.required, Validators.min(0)]],
    });
  }

  ngOnInit() {
    this.id = this.config.data.id;
    this.medicineService.getMedicine(this.id).subscribe({
      next: (data) => {
        this.medicine = data;
        this.loaded = true;
        this.form.setValue({
          name: data.name,
          factoryLaboratory: data.factoryLaboratory,
          manufacturingDate: data.manufacturingDate,
          dueDate: data.dueDate,
          stock: data.stock,
          unitPrice: data.unitPrice,
        });
        console.log(new Date(this.medicine.manufacturingDate));
      },
    });
  }

  close() {
    this.ref.close();
  }

  update() {
    this.medicineService
      .updateMedicine({ ...this.form.value }, this.medicine.id)
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
  }
}

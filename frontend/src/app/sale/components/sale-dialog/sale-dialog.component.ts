import { Component } from '@angular/core';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Medicine } from '../../../medicine/interfaces/Medicine';
import { SaleService } from 'src/app/sale/services/sale.service';
import { MedicineService } from 'src/app/medicine/services/medicine.service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-sale-dialog',
  templateUrl: './sale-dialog.component.html',
  styleUrls: ['./sale-dialog.component.css'],
})
export class SaleDialogComponent {
  loaded = false;

  id = 0;
  quantity = 0;

  medicine: Medicine = null!;

  constructor(
    private medicineService: MedicineService,
    private messageService: MessageService,
    private saleService: SaleService,
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig
  ) {}

  ngOnInit() {
    this.id = this.config.data.id;
    this.medicineService.getMedicine(this.id).subscribe({
      next: (data) => {
        this.medicine = data;
        this.loaded = true;
      },
    });
  }

  close() {
    this.ref.close();
  }

  sell() {
    console.log('sell');
    this.saleService.sell(this.medicine.id, this.quantity).subscribe({
      next: (data) => {
        if (data.status) {
          this.messageService.add({
            severity: 'success',
            summary: 'Se han vendido las unidades correctamente',
          });
          this.close();
        } else
          this.messageService.add({
            severity: 'error',
            summary: 'El medicamento no cuenta con el suficiente stock',
          });
      },
    });
  }
}

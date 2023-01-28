import { Component } from '@angular/core';
import { Medicine } from '../../interfaces/Medicine';
import { MedicineService } from '../../services/medicine.service';
import { TablePagination } from 'src/app/interfaces/TablePagination';
import { PageOnChangeEvent } from '../../interfaces/PageOnChangeEvent';
import { DialogService } from 'primeng/dynamicdialog';
import { SaleDialogComponent } from '../../../sale/components/sale-dialog/sale-dialog.component';
import { UpdateMedicineComponent } from '../update-medicine/update-medicine.component';
import { MessageService } from 'primeng/api';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css'],
})
export class TableComponent {
  pagination: TablePagination = {
    medicines: [],
    rowsPerPage: [10, 25, 50],
    totalElements: 0,
    rows: 10,
    currentPage: 0,
  };

  loaded: boolean;

  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private messageService: MessageService,
    private medicineService: MedicineService,
    private dialogService: DialogService
  ) {
    this.loaded = false;
    this.form = this.fb.group({
      name: '',
      factoryLaboratory: '',
      manufacturingDate: '',
      dueDate: '',
      stock: '',
      unitPrice: '',
    });
  }

  ngOnInit() {
    this.changeData();
  }

  pageChangeEvent(event: PageOnChangeEvent) {
    this.pagination.currentPage = event.page;
    this.pagination.rows = event.rows;
    this.changeData();
  }

  private changeData() {
    this.loaded = false;
    this.medicineService
      .getMedicines({
        page: this.pagination.currentPage,
        offset: this.pagination.rows,
        sortBy: 'id',
        filters: [],
        values: [],
      })
      .subscribe({
        next: (data) => {
          this.pagination.medicines = data.content as Medicine[];
          this.pagination.totalElements = data.totalElements;
        },
      });
    this.loaded = true;
  }

  public sell(id: number) {
    this.dialogService
      .open(SaleDialogComponent, {
        data: {
          id,
        },
        header: 'Compra de medicamento',
      })
      .onClose.subscribe(() => this.changeData());
  }

  public edit(id: number) {
    this.dialogService
      .open(UpdateMedicineComponent, {
        data: {
          id,
        },
        header: 'Actualización de medicamento',
      })
      .onClose.subscribe(() => this.changeData());
  }

  public remove(id: number) {
    this.messageService.clear();
    this.messageService.add({
      key: 'c',
      summary: '¿Desea eliminar el medicamento?',
      contentStyleClass: 'delete-toast',
      data: id,
    });
  }

  public onConfirm(id: number) {
    this.medicineService.deleteMedicine(id).subscribe({
      next: () => {
        this.messageService.clear();
        this.messageService.add({
          severity: 'success',
          summary: 'Medicamento eliminado',
        });
        this.changeData();
      },
      error: (error) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Ha ocurrido un error: ' + error.error.message,
        });
      },
    });
  }
  public onReject() {
    this.messageService.clear();
  }
}

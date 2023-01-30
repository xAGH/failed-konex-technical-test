import { Component } from '@angular/core';
import { Medicine } from '../../interfaces/Medicine';
import { MedicineService } from '../../services/medicine.service';
import { TablePagination } from 'src/app/interfaces/TablePagination';
import { PageOnChangeEvent } from '../../interfaces/PageOnChangeEvent';
import { DialogService } from 'primeng/dynamicdialog';
import { SaleDialogComponent } from '../../../sale/components/sale-dialog/sale-dialog.component';
import { UpsertMedicineComponent } from '../upsert-medicine/upsert-medicine.component';
import { MessageService } from 'primeng/api';
import { AbstractControl, FormBuilder, FormGroup } from '@angular/forms';
import { Subscription, debounceTime } from 'rxjs';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css'],
})
export class TableComponent {
  pagination: TablePagination = {
    data: [],
    rowsPerPage: [10, 25, 50],
    totalElements: 0,
    rows: 10,
    currentPage: 0,
    filters: [],
    values: [],
  };

  loaded: boolean;

  form: FormGroup;

  formChangeSuscription$: Subscription;

  constructor(
    private fb: FormBuilder,
    private messageService: MessageService,
    private medicineService: MedicineService,
    private dialogService: DialogService
  ) {
    this.loaded = false;
    this.form = this.fb.group({
      name: null,
      factoryLaboratory: null,
      manufacturingDate: null,
      dueDate: null,
      stock: null,
      unitPrice: null,
    });

    this.formChangeSuscription$ = this.form.valueChanges
      .pipe(debounceTime(500))
      .subscribe((data) => {
        this.pagination.filters = [];
        this.pagination.values = [];

        for (const key of Object.keys(data)) {
          if (data[key] != '' && data[key] != null) {
            if (['manufacturingDate', 'dueDate'].includes(data[key])) {
              data[key] = new Date(data[key]).getTime();
            }
            this.pagination.filters.push(key);
            this.pagination.values.push(data[key]);
          }
        }
        this.changeData();
        console.log(this.pagination);
      });
  }

  ngOnInit() {
    this.changeData();
  }

  ngOnDestroy() {
    this.formChangeSuscription$.unsubscribe();
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
        filters: this.pagination.filters,
        values: this.pagination.values,
      })
      .subscribe({
        next: (data) => {
          this.pagination.data = data.content as Medicine[];
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

  public upsert(id?: number) {
    this.dialogService
      .open(UpsertMedicineComponent, {
        data: {
          id,
        },
        width: '35%',
        height: '80%',
        header: `${id ? 'Actualización de ' : 'Crear'} medicamento`,
      })
      .onClose.subscribe(() => this.changeData());
  }

  public remove(id: number) {
    this.messageService.clear();
    this.messageService.add({
      key: 'c',
      life: 1000 * 360,
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

  resetFilter(control: AbstractControl | null) {
    if (control != null) {
      control.setValue(null);
    }
  }

  get formName(): AbstractControl | null {
    return this.form.controls['name'];
  }
  get formfactoryLaboratory(): AbstractControl | null {
    return this.form.controls['factoryLaboratory'];
  }
  get formManufacturingDate(): AbstractControl | null {
    return this.form.controls['manufacturingDate'];
  }
  get formdueDate(): AbstractControl | null {
    return this.form.controls['dueDate'];
  }
  get formStock(): AbstractControl | null {
    return this.form.controls['stock'];
  }
  get formunitPrice(): AbstractControl | null {
    return this.form.controls['unitPrice'];
  }
}

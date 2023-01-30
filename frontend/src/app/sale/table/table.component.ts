import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup } from '@angular/forms';
import { Subscription, debounceTime } from 'rxjs';
import { TablePagination } from 'src/app/interfaces/TablePagination';
import { MedicineService } from 'src/app/medicine/services/medicine.service';
import { Sale } from '../interfaces/Sale';
import { PageOnChangeEvent } from 'src/app/medicine/interfaces/PageOnChangeEvent';
import { SaleService } from '../services/sale.service';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css'],
})
export class TableComponent {
  loaded: boolean;

  form: FormGroup;

  pagination: TablePagination = {
    data: [],
    rowsPerPage: [10, 25, 50],
    totalElements: 0,
    rows: 10,
    currentPage: 0,
    filters: ['0'],
    values: ['0'],
  };

  formChangeSuscription$: Subscription;

  constructor(
    private fb: FormBuilder,
    private medicineService: MedicineService,
    private saleService: SaleService
  ) {
    this.loaded = false;
    this.form = this.fb.group({
      startDate: null,
      endDate: null,
    });

    this.formChangeSuscription$ = this.form.valueChanges
      .pipe(debounceTime(500))
      .subscribe((data) => {
        this.pagination.values = [];
        this.changeData(
          new Date(data.startDate).getTime(),
          new Date(data.endDate).getTime()
        );
      });
  }

  private changeData(startDate: number = 0, endDate: number = 0) {
    this.loaded = false;
    this.saleService
      .getSales(startDate, endDate, {
        page: this.pagination.currentPage,
        offset: this.pagination.rows,
        sortBy: 'id',
      })
      .subscribe({
        next: async (data) => {
          for (const sale of data.content as Sale[]) {
            const medicineName = await new Promise<string>((res) => {
              this.medicineService.getMedicine(sale.medicineId).subscribe({
                next: (medicine) => {
                  res(`${medicine.name}-${medicine.factoryLaboratory}`);
                },
                error: () => {
                  res('Undefined');
                },
              });
            });
            sale.medicineName = medicineName;
          }
          this.pagination.data = data.content as Sale[];
          this.pagination.totalElements = data.totalElements;
        },
      });
    this.loaded = true;
  }

  resetFilter(control: AbstractControl | null) {
    if (control != null) {
      control.setValue(null);
    }
  }

  ngOnInit() {
    this.changeData();
  }

  ngOnDestroy() {
    this.formChangeSuscription$.unsubscribe();
  }

  get startDate(): AbstractControl | null {
    return this.form.controls['startDate'];
  }

  get endDate(): AbstractControl | null {
    return this.form.controls['endDate'];
  }

  get medicine(): string {
    return '';
  }

  pageChangeEvent(event: PageOnChangeEvent) {
    this.pagination.currentPage = event.page;
    this.pagination.rows = event.rows;
    this.changeData();
  }
}

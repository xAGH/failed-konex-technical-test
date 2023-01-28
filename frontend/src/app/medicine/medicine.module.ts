import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { TableComponent } from './components/table/table.component';
import { UpdateMedicineComponent } from './components/update-medicine/update-medicine.component';

import { MedicineRoutingModule } from './medicine-routing.module';
import { MessageService, SharedModule as SharedPrime } from 'primeng/api';
import { DialogService } from 'primeng/dynamicdialog';
import { TableModule } from 'primeng/table';
import { PaginatorModule } from 'primeng/paginator';
import { InputTextModule } from 'primeng/inputtext';
import { CalendarModule } from 'primeng/calendar';
import { ToastModule } from 'primeng/toast';

@NgModule({
  declarations: [TableComponent, UpdateMedicineComponent],
  providers: [DialogService, MessageService],
  imports: [
    SharedPrime,
    CommonModule,
    MedicineRoutingModule,
    TableModule,
    PaginatorModule,
    ReactiveFormsModule,
    InputTextModule,
    CalendarModule,
    ToastModule,
  ],
})
export class MedicineModule {}

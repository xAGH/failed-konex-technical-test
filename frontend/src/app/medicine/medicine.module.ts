import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { TableComponent } from './components/table/table.component';
import { UpsertMedicineComponent } from './components/upsert-medicine/upsert-medicine.component';

import { MedicineRoutingModule } from './medicine-routing.module';
import { MessageService, SharedModule } from 'primeng/api';
import { DialogService } from 'primeng/dynamicdialog';
import { TableModule } from 'primeng/table';
import { PaginatorModule } from 'primeng/paginator';
import { InputTextModule } from 'primeng/inputtext';
import { CalendarModule } from 'primeng/calendar';
import { ToastModule } from 'primeng/toast';
import { InputMaskModule } from 'primeng/inputmask';
import { TooltipModule } from 'primeng/tooltip';

@NgModule({
  declarations: [TableComponent, UpsertMedicineComponent],
  providers: [DialogService, MessageService],
  imports: [
    SharedModule,
    CommonModule,
    MedicineRoutingModule,
    TableModule,
    PaginatorModule,
    ReactiveFormsModule,
    InputTextModule,
    CalendarModule,
    ToastModule,
    InputMaskModule,
    TooltipModule,
  ],
})
export class MedicineModule {}

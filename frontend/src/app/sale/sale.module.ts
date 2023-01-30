import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { SaleDialogComponent } from '../sale/components/sale-dialog/sale-dialog.component';
import { InputNumberModule } from 'primeng/inputnumber';
import { PaginatorModule } from 'primeng/paginator';
import { ButtonModule } from 'primeng/button';
import { SaleRoutingModule } from './sale-routing.module';
import { TableComponent } from './table/table.component';
import { SharedModule } from 'primeng/api';
import { DialogService } from 'primeng/dynamicdialog';
import { TableModule } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';
import { CalendarModule } from 'primeng/calendar';
import { InputMaskModule } from 'primeng/inputmask';
import { TooltipModule } from 'primeng/tooltip';

@NgModule({
  declarations: [SaleDialogComponent, TableComponent],
  providers: [DialogService],
  imports: [
    CommonModule,
    InputNumberModule,
    PaginatorModule,
    ButtonModule,
    SharedModule,
    SaleRoutingModule,
    TableModule,
    InputTextModule,
    CalendarModule,
    InputMaskModule,
    TooltipModule,
    ReactiveFormsModule,
  ],
})
export class SaleModule {}

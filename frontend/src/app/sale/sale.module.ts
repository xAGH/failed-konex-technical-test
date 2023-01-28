import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';

import { SaleDialogComponent } from '../sale/components/sale-dialog/sale-dialog.component';
import { InputNumberModule } from 'primeng/inputnumber';
import { PaginatorModule } from 'primeng/paginator';
import { ButtonModule } from 'primeng/button';
import { SharedModule } from 'primeng/api';

@NgModule({
  declarations: [SaleDialogComponent],
  imports: [
    CommonModule,
    InputNumberModule,
    BrowserModule,
    PaginatorModule,
    ButtonModule,
    SharedModule,
  ],
})
export class SaleModule {}

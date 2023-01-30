import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'medicine',
    loadChildren: () =>
      import('./medicine/medicine.module').then((m) => m.MedicineModule),
  },
  {
    path: 'sale',
    loadChildren: () => import('./sale/sale.module').then((m) => m.SaleModule),
  },
  {
    path: '',
    redirectTo: 'medicine',
    pathMatch: 'full',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}

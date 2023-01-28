import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { HttpClientService } from 'src/app/services/http-client.service';
import { Sale } from '../interfaces/Sale';
import { APIResponse } from 'src/app/interfaces/ApiResponse';

@Injectable({
  providedIn: 'root',
})
export class SaleService {
  constructor(private _httpClient: HttpClientService) {}

  readonly URL = 'http://localhost:8080/api/v01/sale';

  sell(medicineId: number, quantity: number): Observable<APIResponse<Sale>> {
    let body: Sale = {
      saleDateTime: new Date().getTime(),
      quantity,
      medicineId,
    };
    return this._httpClient.post<APIResponse<Sale>>(this.URL, body);
  }
}

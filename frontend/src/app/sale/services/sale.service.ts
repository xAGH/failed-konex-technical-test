import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { HttpClientService } from 'src/app/services/http-client.service';
import { Sale } from '../interfaces/Sale';
import { APIResponse, ApiData } from 'src/app/interfaces/ApiResponse';
import { Pagination } from 'src/app/interfaces/Pagination';
import { HttpParams } from '@angular/common/http';

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

  getSales(
    startDate: number,
    endDate: number,
    options?: Pagination
  ): Observable<ApiData> {
    let params = new HttpParams();
    params = params
      .append('page', options!.page)
      .append('offset', options!.offset)
      .append('sortBy', options!.sortBy!)
      .append('startDate', startDate)
      .append('endDate', endDate);

    return this._httpClient
      .get<APIResponse<ApiData>>(this.URL, { params })
      .pipe(map((data) => data.data));
  }
}

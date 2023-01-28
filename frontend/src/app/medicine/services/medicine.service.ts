import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { MedicinePagination } from '../interfaces/MedicinePagination';
import { APIResponse, ApiData } from 'src/app/interfaces/ApiResponse';
import { HttpClientService } from 'src/app/services/http-client.service';
import { Medicine } from '../interfaces/Medicine';

@Injectable({
  providedIn: 'root',
})
export class MedicineService {
  readonly URL = 'http://localhost:8080/api/v01/medicine';

  constructor(private _httpClient: HttpClientService) {}

  getMedicines(options?: MedicinePagination): Observable<ApiData> {
    let params = new HttpParams();
    params = params
      .append('page', options!.page)
      .append('offset', options!.offset)
      .append('sortBy', options!.sortBy!);

    for (let i = 0; i < options!.filters!.length; i++) {
      params = params
        .append('filterBy', options!.filters![i])
        .append('value', options!.values![i]);
    }
    // TODO Api status
    return this._httpClient
      .get<APIResponse<ApiData>>(this.URL, { params })
      .pipe(map((data) => data.data));
  }

  getMedicine(id: number): Observable<Medicine> {
    return this._httpClient
      .get<APIResponse<Medicine>>(this.URL + '/' + id)
      .pipe(map((data) => data.data as Medicine));
  }

  updateMedicine(
    medicine: Medicine,
    id: number
  ): Observable<APIResponse<Medicine>> {
    return this._httpClient.put<APIResponse<Medicine>>(
      this.URL + '/' + id,
      medicine
    );
  }

  deleteMedicine(id: number): Observable<APIResponse<Medicine>> {
    return this._httpClient.delete<APIResponse<Medicine>>(this.URL + '/' + id);
  }
}

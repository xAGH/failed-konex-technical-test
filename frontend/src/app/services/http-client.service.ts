import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class HttpClientService {
  constructor(private _http: HttpClient) {}

  get<T>(url: string, options?: object): Observable<T> {
    return options ? this._http.get<T>(url, options) : this._http.get<T>(url);
  }

  post<T>(url: string, data: any, options?: object): Observable<T> {
    return options
      ? this._http.post<T>(url, data, options)
      : this._http.post<T>(url, data);
  }

  put<T>(url: string, data: any, options?: object): Observable<T> {
    return options
      ? this._http.put<T>(url, data, options)
      : this._http.put<T>(url, data);
  }

  delete<T>(url: string, options?: object): Observable<T> {
    return options
      ? this._http.delete<T>(url, options)
      : this._http.delete<T>(url);
  }
}

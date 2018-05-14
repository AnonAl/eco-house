import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Product } from '../../models/product';
import { ConfigService } from "../config/config.service";

/**
 * Service to access products data
 */
@Injectable()
export class ProductService {

  constructor(
    private http: HttpClient,
    private config: ConfigService
  ) { }

  uri() {
    return this.config.getApiHref() + 'products';
  }

  getAllProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.uri());
  }

  getProductById(id: number): Observable<Product> {
    return this.http.get<Product>(this.uri() + '/' + id);
  }

  saveProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(this.uri(), product);
  }

  deleteProduct(id: number) {
    return this.http.delete(this.uri() + '/' + id);
  }
}

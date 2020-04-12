import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CheckoutService {

  url = "http://localhost:8080/api";

  constructor(private httpClient: HttpClient) { }

  getPaymentMethods(){
    // return this.httpClient.get(this.url+"/payment_methods") as Observable<any>;
    return this.httpClient.post(this.url+"/payment_methods", "test@test.com") as Observable<any>;
  }

  choosePaymentMethod(choice){
    return this.httpClient.post(this.url+"/payment_choice", choice) as Observable<any>;
  }

  payBank(paymentRequest){
    return this.httpClient.post(this.url+"/pay", paymentRequest, { responseType: 'text' as 'json'}) as Observable<any>;
  }

}

import { Component, OnInit } from '@angular/core';
import { CheckoutService } from 'src/app/services/checkout.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {

  private merchantId = "mrch1";
  private merchangPass = "mrchpass123";

  private merchantEmail = "test@test.com";
  private price = 350.00;
  private orderId = 12345;
  private timestamp = Math.floor(Date.now() / 1000);

  private formFieldsDto = null;
  private formFields = [];
  private paymentMethods = [];

  private magazine;

  constructor(private checkoutService : CheckoutService) {
    console.log(localStorage.getItem('checkoutMagazine'));
    this.magazine = localStorage.getItem('checkoutMagazine');

    let x = checkoutService.getPaymentMethods();

    
    x.subscribe(
      res => {
        console.log(res);
        this.paymentMethods = res;
    //     this.enumValues = res;
    //     //this.categories = res;
    //     // this.formFieldsDto = res;
    //     // this.formFields = res.formFields;
    //     // this.formFields.forEach( (field) =>{
          
    //     //   if( field.type.name=='enum'){
    //     //     this.enumValues = Object.keys(field.type.values);
    //     //   }
    //     // });
      },
      err => {
        console.log(err);
        console.log("Error occured");
      }
    );
  }

  ngOnInit() {
  }

  handlePayment(payment){
    if (payment=="bank"){
      let request = {
                     "paymentMethod": "bank",
                     "email": this.merchantEmail,
                     "amount": this.price,
                     "orderId": this.orderId,
                     "timestamp": this.timestamp
                    }
      this.checkoutService.payBank(request).subscribe(
        resp => {
          console.log(resp);
          window.location.href = resp;
        },
        err => {
          console.log(err);
        }
      );
    }else if(payment=="paypal"){
      console.log("paypal");
    }else if (payment=="bitcoin"){
      console.log("bitcoin");
    }
  }
}

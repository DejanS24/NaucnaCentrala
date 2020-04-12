import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-success',
  templateUrl: './success.component.html',
  styleUrls: ['./success.component.css']
})
export class SuccessComponent implements OnInit {
  private paymentId;

  constructor(private route: ActivatedRoute) { 
    this.paymentId = this.route.snapshot.paramMap.get('paymentId');
  }

  ngOnInit() {
  }

}

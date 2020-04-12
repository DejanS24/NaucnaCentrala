import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit {
  private paymentId;

  constructor(private route: ActivatedRoute) { 
    this.paymentId = this.route.snapshot.paramMap.get('paymentId');
  }

  ngOnInit() {
  }

}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-fail',
  templateUrl: './fail.component.html',
  styleUrls: ['./fail.component.css']
})
export class FailComponent implements OnInit {
  private paymentId;

  constructor(private route: ActivatedRoute) { 
    this.paymentId = this.route.snapshot.paramMap.get('paymentId');
  }

  ngOnInit() {
  }

}

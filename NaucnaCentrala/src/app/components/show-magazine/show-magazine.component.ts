import { Component, OnInit } from '@angular/core';
import { MagazineService } from 'src/app/services/magazine.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-show-magazine',
  templateUrl: './show-magazine.component.html',
  styleUrls: ['./show-magazine.component.css']
})
export class ShowMagazineComponent implements OnInit {
  private magazines = [];

  constructor(private magazineService: MagazineService, private router:Router) {
    let x = magazineService.getAllMagazines();

    x.subscribe(
      res => {
        this.magazines = res;
      },
      err => {
        console.log("Error occured");
      }
    )
  }

  ngOnInit() {
  }

  handlePayClick(magazineId){
    // console.log(magazineId)
    localStorage.setItem("checkoutMagazine", magazineId);
    this.router.navigateByUrl("checkout");
  }

}

import { Component, OnInit } from '@angular/core';
import { ScientificWorkService } from 'src/app/services/scientific-work.service';

@Component({
  selector: 'app-sci-work-correction',
  templateUrl: './sci-work-correction.component.html',
  styleUrls: ['./sci-work-correction.component.css']
})
export class SciWorkCorrectionComponent implements OnInit {

  constructor(private scientificWorkService: ScientificWorkService) {
    // let x = scientificWorkService

    // x.subscribe(
    //   res => {
    //     console.log(res);
    //     //this.categories = res;
    //     this.formFieldsDto = res;
    //     this.formFields = res.formFields;
    //     this.processInstance = res.processInstanceId;
    //     this.formFields.forEach( (field) =>{
          
    //       if( field.type.name=='enum'){
    //         this.enumValues = Object.keys(field.type.values);
    //       }
    //     });
    //   },
    //   err => {
    //     console.log("Error occured");
    //   }
    // );
  }

  ngOnInit() {
  }

}

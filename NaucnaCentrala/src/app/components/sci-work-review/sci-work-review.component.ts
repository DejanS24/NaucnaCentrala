import { Component, OnInit } from '@angular/core';
import { ScientificWorkService } from 'src/app/services/scientific-work.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-sci-work-review',
  templateUrl: './sci-work-review.component.html',
  styleUrls: ['./sci-work-review.component.css']
})
export class SciWorkReviewComponent implements OnInit {
  id: any;
  private formFieldsDto = null;
  private formFields = [];
  private choosen_category = -1;
  private processInstance = "";
  private enumValues = [];
  private tasks = [];

  constructor(private scientificWorkService: ScientificWorkService, private route: ActivatedRoute, private router: Router) {
    this.id = this.route.snapshot.paramMap.get('instanceId');
    console.log(this.id);

    let x = scientificWorkService.fetchReview(this.id);

    x.subscribe(
      res => {
        console.log(res);
        //this.categories = res;
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.processInstance = res.processInstanceId;
        this.formFields.forEach( (field) =>{
          
          if( field.type.name=='enum'){
            this.enumValues = Object.keys(field.type.values);
          }
        });
      },
      err => {
        console.log("Error occured");
      }
    );
  }

  ngOnInit() {
  }

  onSubmit(value, form){
    console.log(value);
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});
    }

    console.log(o);
    let x;
    x = this.scientificWorkService.submitReview(o, this.formFieldsDto.taskId);
    x.subscribe(
      res => {
        console.log("Uspesno prijavljeno");
        console.log(res);
        this.router.navigate(['']);          
      },
      err => {
        console.log(err);
        this.router.navigate(['']);          
      }
    );
  }

}

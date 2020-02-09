import { Component, OnInit } from '@angular/core';
import { ScientificWorkService } from 'src/app/services/scientific-work.service';

@Component({
  selector: 'app-sci-work-magazine-choice',
  templateUrl: './sci-work-magazine-choice.component.html',
  styleUrls: ['./sci-work-magazine-choice.component.css']
})
export class SciWorkMagazineChoiceComponent implements OnInit {

  private repeated_password = "";
  private categories = [];
  private formFieldsDto = null;
  private formFields = [];
  private choosen_category = -1;
  private processInstance = "";
  private enumValues = [];
  private tasks = [];

  constructor(private scientificWorkService: ScientificWorkService) {
    let x = scientificWorkService.fetchChooseMagazineForm();

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
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});
    }

    console.log(o);
    let x = this.scientificWorkService.confirmMagazineChoice(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        console.log(res);
        
        alert("You registered successfully!")
      },
      err => {
        console.log("Error occured");
      }
    );
  }
}

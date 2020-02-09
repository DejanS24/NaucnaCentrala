import { Component, OnInit } from '@angular/core';
import { ScientificWorkService } from 'src/app/services/scientific-work.service';

@Component({
  selector: 'app-sci-work-creation',
  templateUrl: './sci-work-creation.component.html',
  styleUrls: ['./sci-work-creation.component.css']
})
export class SciWorkCreationComponent implements OnInit {
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
    let x;
    if (o[0].fieldId == "naslov"){
      x = this.scientificWorkService.submitSciWorkForm(o, this.formFieldsDto.taskId);
    }else{
      x = this.scientificWorkService.confirmMagazineChoice(o, this.formFieldsDto.taskId);
    }
    x.subscribe(
      res => {
        console.log(res);

        this.renderSciWorkForm();        
      },
      err => {
        console.log(err);
        console.log("Error occured");

        this.renderSciWorkForm();        
      }
    );
  }

  renderSciWorkForm(){
    let x = this.scientificWorkService.fetchSciWorkForm(this.formFieldsDto.processInstanceId);

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
}

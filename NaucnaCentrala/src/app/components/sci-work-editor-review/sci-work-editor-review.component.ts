import { Component, OnInit } from '@angular/core';
import { ScientificWorkService } from 'src/app/services/scientific-work.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-sci-work-editor-review',
  templateUrl: './sci-work-editor-review.component.html',
  styleUrls: ['./sci-work-editor-review.component.css']
})
export class SciWorkEditorReviewComponent implements OnInit {
  id: any;
  private formFieldsDto = null;
  private formFields = [];
  private choosen_category = -1;
  private processInstance = "";
  private enumValues = [];
  private tasks = [];

  constructor(private scientificWorkService: ScientificWorkService, private route: ActivatedRoute) {
    this.id = this.route.snapshot.paramMap.get('instanceId');
    console.log(this.id);

    let x = scientificWorkService.fetchEditorStep(this.id);

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
    x = this.scientificWorkService.submitEditorStep(o, this.formFieldsDto.taskId);
    x.subscribe(
      res => {
        console.log("Uspesno prijavljeno");
        console.log(res);

        this.renderNextStep();
      },
      err => {
        console.log(err);

        this.renderNextStep();
      }
    );
  }

  renderNextStep(){
    let x = this.scientificWorkService.fetchEditorStep(this.formFieldsDto.processInstanceId);

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

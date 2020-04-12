import { Component, OnInit, Injectable, Inject } from '@angular/core';
import { MagazineService } from 'src/app/services/magazine.service';
import {Router} from '@angular/router';
import { ProcessService } from 'src/app/services/process.service';


@Component({
  selector: 'app-magazine',
  templateUrl: './magazine-creation.component.html',
  styleUrls: ['./magazine-creation.component.css']
})
export class MagazineCreationComponent implements OnInit {
  private formFieldsDto = null;
    private formFields = [];
    private processInstanceId = '';
    private enumValues = [];
    private tasks = [];
    private nextTask = '';
    private selectedFields = [];
    taskId = '';


  constructor(private magazineService: MagazineService, private processService: ProcessService, private router: Router) {
  // constructor(private magazineService: MagazineService, private processService: ProcessService, private router: Router) {

    const x = magazineService.getMagazineForm();

    x.subscribe(
      res => {
        // console.log(res);
        // this.categories = res;
        this.taskId = res.taskId;
        // console.log('dobijeno je ' + res);
        // console.log('id taska je: ' + this.taskId);
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.processInstanceId = res.processInstanceId;

      },
      err => {
        console.log('Error occured');
      }
    );
   }

    getId() {
    return this.processInstanceId;

   }


  ngOnInit() {
  }

  getEnumValues(values){
    let vals = [];
    for (let i in values){
      vals.push(i);
    }
    return vals;
  }

  onSubmit(value, form) {
    const o = new Array();
    for (const property in value) {
      // console.log(property);
      // console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});
    }

    // console.log(o);
    const x = this.magazineService.createMagazine(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        // console.log(res);

        alert('You registered successfully!');
        this.router.navigate(['/task',  this.processInstanceId]);
      },
      err => {
        console.log('Error occured');
        alert('Value of field is empty or not valid!');

      }
    );
  }
}

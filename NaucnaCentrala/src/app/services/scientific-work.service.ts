import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ScientificWorkService {



  constructor(private httpClient: HttpClient) { }

  fetchChooseMagazineForm() {
    return this.httpClient.get("http://localhost:8070/work/choose") as Observable<any>;
  }

  confirmMagazineChoice(choice, taskId) {
    // var headers: HttpHeaders = new HttpHeaders({ 'dataType': 'text' });

    return this.httpClient.post("http://localhost:8070/work/choose/".concat(taskId), choice) as Observable<any>;
  }

  fetchSciWorkForm(instanceId) {
    return this.httpClient.get("http://localhost:8070/work/workForm/".concat(instanceId)) as Observable<any>;
  }

  submitSciWorkForm(sciWork, taskId) {
    return this.httpClient.post("http://localhost:8070/work/submit/".concat(taskId), sciWork) as Observable<any>;
  }

  fetchEditorStep(instanceId){
    return this.httpClient.get("http://localhost:8070/work/editorStep/".concat(instanceId)) as Observable<any>;
  }

  submitEditorStep(res, taskId){
    return this.httpClient.post("http://localhost:8070/work/editorStep/".concat(taskId), res) as Observable<any>;
  }


}

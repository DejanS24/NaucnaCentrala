import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HomeComponent} from './components/home/home.component';
import {LoginComponent} from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';

const appRoutes: Routes = [
    {path: '', component: HomeComponent},
    {path: 'register', component : RegisterComponent},
    {path: 'login', component: LoginComponent}
];

@NgModule({
    declarations: [],
    imports: [RouterModule.forRoot(appRoutes, { useHash: true }), FormsModule],
    exports: [RouterModule]
})
export class AppRoutingModule { }

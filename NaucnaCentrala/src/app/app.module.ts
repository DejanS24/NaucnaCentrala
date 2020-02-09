import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule }   from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';

import { RepositoryService } from './services/repository/repository.service';
import { UserService } from './services/user.service';


import { MagazineComponent } from './components/magazine/magazine.component';
import { MagazineService } from './services/magazine.service';
import { from } from 'rxjs';
import { LoginComponent } from './components/login/login.component';
import { AuthGuardService } from './services/security/auth-guard.service';
import { JwtUtilsService } from './services/security/jwt-utils.service';
import { AuthenticationService } from './services/security/authentication.service';
import { TokenInterceptorService } from './services/security/token-interceptor.service';
import { LoginGuardService } from './services/security/login-guard.service';
import { HomeComponent } from './components/home/home.component';
import { RegisterComponent } from './components/register/register.component';
import { SciWorkMagazineChoiceComponent } from './components/sci-work-magazine-choice/sci-work-magazine-choice.component';
import { MagazinePaySubscriptionComponent } from './components/magazine-pay-subscription/magazine-pay-subscription.component';
import { SciWorkCreationComponent } from './components/sci-work-creation/sci-work-creation.component';
import { SciWorkCorrectionComponent } from './components/sci-work-correction/sci-work-correction.component';


const ChildRoutes =
  [
  ]

  const RepositoryChildRoutes =
  [
    
  ]

const Routes = [
  {
    path: "",
    component: HomeComponent
  },
  {
    path: "login",
    component: LoginComponent,
    canActivate: [LoginGuardService]
  },
  {
    path: "register",
    component: RegisterComponent,
    canActivate: [LoginGuardService]
  },
  {
    path: "magazine",
    component: MagazineComponent,
    data: { roles: ["Author"] },
    canActivate: [AuthGuardService]
  },{
    path: "createSciWork",
    component: SciWorkCreationComponent,
    data: { roles: ["Author"] },
    canActivate: [AuthGuardService]
  }
]

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    RegisterComponent,
    MagazineComponent,
    LoginComponent,
    SciWorkMagazineChoiceComponent,
    MagazinePaySubscriptionComponent,
    SciWorkCreationComponent,
    SciWorkCorrectionComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    RouterModule.forRoot(Routes),
    HttpClientModule, 
    HttpModule
  ],
  
  providers:  [
    // Admin,
    // Authorized,
    // Notauthorized
    AuthGuardService,
    JwtUtilsService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true
    },
    AuthenticationService
    ],
  bootstrap: [AppComponent]
})
export class AppModule { }

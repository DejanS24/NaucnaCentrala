import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule }   from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { HttpModule } from '@angular/http';
import { AppComponent } from './app.component';
import { RepositoryService } from './services/repository/repository.service';
import { UserService } from './services/user.service';
import { MagazineCreationComponent } from './components/magazine-creation/magazine-creation.component';
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
import { MagazinePaySubscriptionComponent } from './components/magazine-pay-subscription/magazine-pay-subscription.component';
import { SciWorkCreationComponent } from './components/sci-work-creation/sci-work-creation.component';
import { SciWorkCorrectionComponent } from './components/sci-work-correction/sci-work-correction.component';
import { SciWorkReviewComponent } from './components/sci-work-review/sci-work-review.component';
import { SciWorkEditorReviewComponent } from './components/sci-work-editor-review/sci-work-editor-review.component';
import {NgMultiSelectDropDownModule} from 'ng-multiselect-dropdown';
import { CheckoutComponent } from './components/checkout/checkout.component';
import { CheckoutService } from './services/checkout.service';
import { ShowMagazineComponent } from './components/show-magazine/show-magazine.component';
import { SuccessComponent } from './components/payment/success/success.component';
import { FailComponent } from './components/payment/fail/fail.component';
import { ErrorComponent } from './components/payment/error/error.component';


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
    path: "checkout",
    component: CheckoutComponent
  },
  {
    path: "register",
    component: RegisterComponent,
    canActivate: [LoginGuardService]
  },{
    path: "showMagazine",
    component: ShowMagazineComponent
  },  
  {
    path: "magazine",
    component: MagazineCreationComponent,
    data: { roles: ["Author"] },
    canActivate: [AuthGuardService]
  },{
    path: "createSciWork",
    component: SciWorkCreationComponent,
    data: { roles: ["Author"] },
    canActivate: [AuthGuardService]
  },
  {
    path: "reviewWorkEditor/:instanceId",
    component: SciWorkEditorReviewComponent,
    data: { roles: ["Editor"]},
    canActivate: [AuthGuardService]
  },
  {
    path: "reviewWork/:instanceId",
    component: SciWorkReviewComponent,
    data: { roles: ["Reviewer"]},
    canActivate: [AuthGuardService]
  },
  {
    path: "payment/success/:paymentId",
    component: SuccessComponent
  },
  {
    path: "payment/fail/:paymentId",
    component: FailComponent
  },
  {
    path: "payment/error/:paymentId",
    component: ErrorComponent
  }
]

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    RegisterComponent,
    MagazineCreationComponent,
    LoginComponent,
    MagazinePaySubscriptionComponent,
    SciWorkCreationComponent,
    SciWorkCorrectionComponent,
    SciWorkReviewComponent,
    SciWorkEditorReviewComponent,
    CheckoutComponent,
    ShowMagazineComponent,
    SuccessComponent,
    FailComponent,
    ErrorComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    RouterModule.forRoot(Routes),
    HttpClientModule,
    NgMultiSelectDropDownModule.forRoot(),
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

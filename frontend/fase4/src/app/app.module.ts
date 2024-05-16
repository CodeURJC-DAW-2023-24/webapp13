import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { ReactiveFormsModule } from '@angular/forms';
<<<<<<< HEAD
import { HttpClientModule } from '@angular/common/http';
=======
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
>>>>>>> 1ffb219ae89e7e1976bea9b4ccbe33498824cbfa

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { IndexComponent } from './index/index.component';
import { ProfileComponent } from './profile/profile.component';
import { AdminPanelComponent } from './admin-panel/admin-panel.component';
import { NewProductComponent } from './new-product/new-product.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { ProfileConsultComponent } from './profile-consult/profile-consult.component';
import { IndividualProductComponent } from './individual-product/individual-product.component';
import { ReportFormComponent } from './report-form/report-form.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    IndexComponent,
    ProfileComponent,
    AdminPanelComponent,
    NewProductComponent,
    SignUpComponent,
    ProfileConsultComponent,
    IndividualProductComponent,
    ReportFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule,
<<<<<<< HEAD
    HttpClientModule,
    ReactiveFormsModule
=======
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule
>>>>>>> 1ffb219ae89e7e1976bea9b4ccbe33498824cbfa
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}

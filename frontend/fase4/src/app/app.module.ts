import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router'; // Importa RouterModule
import { AppRoutingModule } from './app-routing.module';
import { ReactiveFormsModule } from '@angular/forms';

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
    AppComponent, LoginComponent, IndexComponent, ProfileComponent, AdminPanelComponent, NewProductComponent, SignUpComponent, ProfileConsultComponent, IndividualProductComponent, ReportFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}

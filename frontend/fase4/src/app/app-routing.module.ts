import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { IndexComponent } from './index/index.component';
import { ProfileComponent } from './profile/profile.component';
import { AdminPanelComponent } from './admin-panel/admin-panel.component';
import { NewProductComponent } from './new-product/new-product.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { ProfileConsultComponent } from './profile-consult/profile-consult.component';
import { IndividualProductComponent } from './individual-product/individual-product.component';
import { ReportFormComponent } from './report-form/report-form.component';
import { ProductComponent } from './product/product.component';

const routes: Routes = [
  { path: '', component: IndexComponent},
  { path: 'login', component: LoginComponent},
  { path: 'profile', component: ProfileComponent},
  { path: 'admin', component: AdminPanelComponent},
  { path: 'newProduct', component: NewProductComponent},
  { path: 'signUp', component: SignUpComponent},
  { path: 'profileConsult', component: ProfileConsultComponent},
  { path: 'product/:id', component: IndividualProductComponent},
  { path: 'report', component: ReportFormComponent},
  { path: 'checkout/:id', component: ProductComponent}
];

@NgModule({
  declarations: [],
  imports: [
    CommonModule, RouterModule.forRoot(routes)
  ],
  exports : [RouterModule]
})
export class AppRoutingModule { }

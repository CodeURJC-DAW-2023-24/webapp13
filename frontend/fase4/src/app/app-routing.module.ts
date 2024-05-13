import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { IndexComponent } from './index/index.component';
import { ProfileComponent } from './profile/profile.component';
import { AdminPanelComponent } from './admin-panel/admin-panel.component';
import { NewProductComponent } from './new-product/new-product.component';

const routes: Routes = [
  { path: '', component: IndexComponent},
  { path: 'login', component: LoginComponent},
  { path: 'profile', component: ProfileComponent},
  { path: 'admin', component: AdminPanelComponent},
  { path: 'newProduct', component: NewProductComponent} // Asume que tienes un componente llamado LoginComponent
  // Puedes agregar más rutas aquí según las necesidades de tu aplicación
];

@NgModule({
  declarations: [],
  imports: [
    CommonModule, RouterModule.forRoot(routes)
  ],
  exports : [RouterModule]
})
export class AppRoutingModule { }

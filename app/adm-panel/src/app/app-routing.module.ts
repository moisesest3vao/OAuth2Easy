import { InjectionToken, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const lazyPathValue = 'lazy';
export const LAZY_PATH = new InjectionToken('LAZY_PATH');

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: lazyPathValue
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [{ provide: LAZY_PATH, useValue: lazyPathValue }],
})
export class AppRoutingModule { }

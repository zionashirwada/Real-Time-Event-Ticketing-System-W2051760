import { enableProdMode } from '@angular/core';
import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { provideRouter, Routes } from '@angular/router';
import { ConfigurationFormComponent } from './app/components/configuration-form/configuration-form.component';

const routes: Routes = [
  { path: '', component: ConfigurationFormComponent },
  { path: 'configuration', component: ConfigurationFormComponent },
];

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),

  ],
}).catch((err) => console.error(err));

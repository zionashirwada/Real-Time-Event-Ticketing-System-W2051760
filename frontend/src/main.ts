import { HttpClientModule, provideHttpClient } from '@angular/common/http';
import { enableProdMode, importProvidersFrom } from '@angular/core';
import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { provideRouter, Routes } from '@angular/router';
import { ConfigurationFormComponent } from './app/components/configuration-form/configuration-form.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { ToastService } from './app/services/toast.service';

const routes: Routes = [
  { path: '', component: ConfigurationFormComponent },
  { path: 'configuration', component: ConfigurationFormComponent },
];

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),
    importProvidersFrom(HttpClientModule), provideAnimationsAsync(),
    provideAnimationsAsync(),
    provideHttpClient(),
    ToastService,

  ],
}).catch((err) => console.error(err));

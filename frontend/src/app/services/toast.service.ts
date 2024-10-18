// toast.service.ts
import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Subject } from 'rxjs';

export interface Toast {
  message: string;
  type: 'success' | 'error' | 'warning' | 'info';
  duration?: number;
  title?: string;
}

@Injectable({
  providedIn: 'root'
})
export class ToastService {
  private toastSubject = new Subject<Toast>();
  toasts$ = this.toastSubject.asObservable();

  constructor(private snackBar: MatSnackBar) {}

  // Simple snackbar toast
  showSnackbar(message: string, action: string = 'Close', duration: number = 3000) {
    this.snackBar.open(message, action, {
      duration,
      horizontalPosition: 'end',
      verticalPosition: 'top',
      panelClass: ['custom-snackbar']
    });
  }

  // Custom toast notifications
  show(toast: Toast) {
    this.toastSubject.next({
      ...toast,
      duration: toast.duration || 5000
    });
  }

  success(message: string, title?: string) {
    this.show({
      message,
      title,
      type: 'success'
    });
  }

  error(message: string, title?: string) {
    this.show({
      message,
      title,
      type: 'error',
      duration: 8000 // Longer duration for errors
    });
  }

  warning(message: string, title?: string) {
    this.show({
      message,
      title,
      type: 'warning'
    });
  }

  info(message: string, title?: string) {
    this.show({
      message,
      title,
      type: 'info'
    });
  }
}

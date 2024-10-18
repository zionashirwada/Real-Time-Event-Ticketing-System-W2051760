// toast-container.component.ts
import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { Toast, ToastService } from '../../services/toast.service';

@Component({
  selector: 'app-toast-container',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="fixed top-4 right-4 z-50 flex flex-col gap-2">
      <div *ngFor="let toast of toasts; let i = index"
           [@toastAnimation]
           class="min-w-[300px] max-w-md p-4 rounded-lg shadow-lg text-white"
           [ngClass]="{
             'bg-green-600': toast.type === 'success',
             'bg-red-600': toast.type === 'error',
             'bg-yellow-500': toast.type === 'warning',
             'bg-blue-600': toast.type === 'info'
           }">
        <div class="flex items-start justify-between">
          <div class="flex-1">
            <h6 *ngIf="toast.title" class="font-bold mb-1">{{ toast.title }}</h6>
            <p class="text-sm">{{ toast.message }}</p>
          </div>
          <button (click)="removeToast(i)"
                  class="ml-4 text-white hover:text-gray-200">
            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
            </svg>
          </button>
        </div>
        <div class="mt-2 h-1 w-full bg-white/20 rounded">
          <div class="h-1 bg-white rounded animate-shrink"
               [style.animation-duration]="toast.duration + 'ms'"></div>
        </div>
      </div>
    </div>
  `,
  animations: [
    trigger('toastAnimation', [
      state('void', style({
        transform: 'translateX(100%)',
        opacity: 0
      })),
      state('*', style({
        transform: 'translateX(0)',
        opacity: 1
      })),
      transition('void => *', [
        animate('300ms ease-out')
      ]),
      transition('* => void', [
        animate('300ms ease-in')
      ])
    ])
  ]
})
export class ToastContainerComponent implements OnInit, OnDestroy {
  toasts: Toast[] = [];
  private subscription: Subscription = new Subscription;

  constructor(private toastService: ToastService) {}

  ngOnInit() {
    this.subscription = this.toastService.toasts$.subscribe(toast => {
      this.toasts.push(toast);
      setTimeout(() => this.removeToast(this.toasts.indexOf(toast)), toast.duration);
    });
  }

  removeToast(index: number) {
    this.toasts.splice(index, 1);
  }

  ngOnDestroy() {
    this.subscription?.unsubscribe();
  }
}


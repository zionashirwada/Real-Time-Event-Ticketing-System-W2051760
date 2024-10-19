import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketPoolStatusComponent } from './ticket-pool-status.component';

describe('TicketPoolStatusComponent', () => {
  let component: TicketPoolStatusComponent;
  let fixture: ComponentFixture<TicketPoolStatusComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketPoolStatusComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TicketPoolStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

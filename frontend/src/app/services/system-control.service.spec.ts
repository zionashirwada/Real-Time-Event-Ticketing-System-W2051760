import { TestBed } from '@angular/core/testing';

import { SystemControlService } from './system-control.service';

describe('SystemControlService', () => {
  let service: SystemControlService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SystemControlService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

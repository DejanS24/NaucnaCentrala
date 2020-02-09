import { TestBed, inject } from '@angular/core/testing';

import { ScientificWorkService } from './scientific-work.service';

describe('ScientificWorkService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ScientificWorkService]
    });
  });

  it('should be created', inject([ScientificWorkService], (service: ScientificWorkService) => {
    expect(service).toBeTruthy();
  }));
});

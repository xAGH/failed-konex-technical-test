import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function trimStringValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;

    if (!value) {
      return { trim: false };
    }

    if (!value.toString().trim()) {
      return { trim: false };
    }

    return null;
  };
}

import {ActivatedRoute, Router} from '@angular/router';

export class Utils {
  static isUndefined(obj: any): boolean {
    return typeof obj === 'undefined';
  }

  static formatDate(date?: Date): string {
    if (!date) {
      return '';
    }

    return date.toISOString().slice(0, -5).replace("T", " ").replace(/:/g, "-");
  }

  static changeQueryParam(route: ActivatedRoute, router: Router, params: any): void {
    router.navigate([], {
      relativeTo: route,
      queryParams: params,
      queryParamsHandling: 'merge'
    });
  }
}

import {ActivatedRoute, Router} from '@angular/router';

export class Utils {
  static isUndefined(obj: any): boolean {
    return typeof obj === 'undefined';
  }

  static changeQueryParam(route: ActivatedRoute, router: Router, params: any): void {
    router.navigate([], {
      relativeTo: route,
      queryParams: params,
      queryParamsHandling: 'merge'
    });
  }
}

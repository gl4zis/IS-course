import {BidRequest} from './bid.request';

export interface DepartureRequest extends BidRequest {
  dayFrom: Date;
  dayTo: Date;
}

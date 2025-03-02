import {BidRequest} from './bid.request';

export interface OccupationRequest extends BidRequest {
  universityId: number;
  dormitoryId: number;
}

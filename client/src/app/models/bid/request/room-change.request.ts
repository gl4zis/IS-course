import {BidRequest} from './bid.request';
import {RoomType} from '../../room-type.model';

export interface RoomChangeRequest extends BidRequest {
  roomToId?: number;
  roomPreferType?: RoomType;
}

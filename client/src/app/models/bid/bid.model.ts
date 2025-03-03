import {User} from '../user/user.model';
import {RoomType} from '../room/room.model';

export interface Bid {
  number: number;
  sender: User;
  manager?: User;
  text: string;
  type: BidType;
  attachments: string[];
  status: BidStatus;
}

export interface OccupationBid extends Bid {
  universityId: number;
  dormitoryId: number;
}

export interface DepartureBid extends Bid {
  dayFrom: Date;
  dayTo: Date;
}

export interface RoomChangeBid extends Bid {
  roomToId?: number;
  roomPreferType?: RoomType;
}

export enum BidStatus {
  IN_PROCESS = 'IN_PROCESS',
  ACCEPTED = 'ACCEPTED',
  DENIED = 'DENIED'
}

export enum BidType {
  OCCUPATION = 'OCCUPATION',
  DEPARTURE = 'DEPARTURE',
  ROOM_CHANGE = 'ROOM_CHANGE',
  EVICTION = 'EVICTION'
}


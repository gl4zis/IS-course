import {RoomType} from '../room/room.model';

export interface BidRequest {
  text: string;
  attachments: string[];
}

export interface OccupationRequest extends BidRequest {
  universityId: number;
  dormitoryId: number;
}

export interface DepartureRequest extends BidRequest {
  dayFrom: Date;
  dayTo: Date;
}

export interface RoomChangeRequest extends BidRequest {
  roomToId?: number;
  roomPreferType?: RoomType;
}

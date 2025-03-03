import {RoomRequest} from './room.request';

export interface Room extends RoomRequest {
  id: number;
  residentsNumber: number;
}

export enum RoomType {
  BLOCK = 'BLOCK',
  AISLE = 'AISLE'
}

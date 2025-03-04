import {RoomRequest} from './room.request';
import {User} from '../user/user.model';

export interface Room extends RoomRequest {
  id: number;
  residents: User[];
}

export enum RoomType {
  BLOCK = 'BLOCK',
  AISLE = 'AISLE'
}

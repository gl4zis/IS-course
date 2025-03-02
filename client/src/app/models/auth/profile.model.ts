import {Role} from './role.model';

export interface Profile {
  name: string;
  surname: string;
  role: Role;
  university?: string;
  room?: Room;
}

export interface Room {
  dormitory: string;
  number: number;
  type: RoomType;
  capacity: number;
  floor: number;
  cost: number;
}

export enum RoomType {
  BLOCK = 'BLOCK',
  AISLE = 'AISLE'
}

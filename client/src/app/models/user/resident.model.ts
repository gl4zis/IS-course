import {User} from './user.model';

export interface Resident extends User {
  university: string;
  dormitory: string;
  roomNumber: number;
  toEviction: boolean;
}

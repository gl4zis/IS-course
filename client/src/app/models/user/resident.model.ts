import {User} from './user.model';
import {EvictionReason} from '../eviction-reason.model';

export interface Resident extends User {
  university: string;
  dormitory: string;
  roomNumber: number;
  debt: number;
  lastCameOut?: Date;
  evictionReason?: EvictionReason;
}

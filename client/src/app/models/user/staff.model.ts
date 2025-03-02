import {User} from './user.model';

export interface Staff extends User {
  role: StaffRole;
}

export enum StaffRole {
  GUARD = 'GUARD',
  MANAGER = 'MANAGER'
}

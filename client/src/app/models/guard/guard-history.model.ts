import {GuardType} from './guard-type.model';

export interface GuardHistory {
  login: string;
  type: GuardType;
  timestamp: Date;
}

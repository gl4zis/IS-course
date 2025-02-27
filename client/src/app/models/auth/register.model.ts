import {LoginReq} from './login.model';
import {Role} from './role.model';

export interface RegisterReq extends LoginReq {
  role: Role;
}

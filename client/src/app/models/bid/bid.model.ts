import {BidType} from './bid-type.model';
import {BidStatus} from './bid-status.model';
import {User} from '../user/user.model';

export interface Bid {
  number: number;
  sender: User;
  manager?: User;
  text: string;
  type: BidType;
  status: BidStatus;
  attachments: string[];
}

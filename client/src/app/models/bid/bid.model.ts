import {User} from './user.model';
import {BidType} from './bid-type.model';
import {BidStatus} from './bid-status.model';

export interface Bid {
  number: number;
  sender: User;
  manager?: User;
  text: string;
  type: BidType;
  status: BidStatus;
  attachments: string[];
}

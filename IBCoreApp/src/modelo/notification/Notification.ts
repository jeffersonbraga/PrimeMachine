import { Profile } from './../profile/Profile';
import { Account } from './../account/Account';

export interface Notification {
    idNotification?: number;
    account: Account;
    profile: Profile;
    message: string;
    action: string;
    date: Date;
    color: string;
    unread: boolean;
}

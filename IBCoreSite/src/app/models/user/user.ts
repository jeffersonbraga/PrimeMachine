import { Account } from './../account/account';

export class User {

    ACTIVE = 'T';
    INACTIVE = 'F';
    ADMINISTRATOR = 'A';
    ANALYST = 'N';

    idUser: number;

    name: string;

    email: string;

    password: string;

    active = 'T';

    cadastreDate: Date;

    type: string;

    avatar: string;

    account: Account;

    sessionId = '';

    phone: string;

    showInstagramAuthWindow = 'F';

    showLettersToClientsWindow = 'F';

    timezone: number;
}

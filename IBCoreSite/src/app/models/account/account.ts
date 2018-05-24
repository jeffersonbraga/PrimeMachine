export class Account {
    /*
    public FREE: number = 0; // até 5
    public OPLAY: number = 1; // até 5
    public OPOP: number = 2; // até 10
    public OPOWER: number = 3; // até 15
    public OPLUS: number = 4; // até 25
    public OPRIME: number = 5; // até 35
    public OPREMIUM: number = 6; // até 50
    public CUSTOM: number = 7; // customizado
    */
    idAccount: number;

    public active = 'T';

    public city: string;

    public state: string;

    public enterprise: string;

    public planType: number;

    public cadastre: Date;

    public testExpire: Date;

    public planExpire: Date;

    public extendTest = 'F';

    public clientType = 'T';

    public usesSac = false;

    public sacLastUse: Date;

    public promotionalCode: string;

    public monitorings: number;

    public profiles: number;
}

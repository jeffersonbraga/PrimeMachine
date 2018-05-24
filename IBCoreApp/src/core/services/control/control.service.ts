import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';
import { Profile } from 'modelo/profile/Profile';
import { User } from 'modelo/user/User';
import { Subject } from 'rxjs/Subject';

import { GeneralService } from './../general.service';

@Injectable()
export class ControlService extends GeneralService {

    headers = new Headers({ 'Content-Type': 'application/json' });

    user: User;
    admins: any;
    analysts: any;
    account: any;

    public teste;

    isAuthUser: boolean = false;
    showedUnlock = false;

    // Production Paths
    // public enviroment = 'prod';
    // public context_app_front    : string    = "https://app.woopsocial.com/";
    // public static context_app_front_aux    : string    = "https://app.woopsocial.com/";
    // public context_app          : string    = "https://app.woopsocial.com/";
    // public static context_app_aux          : string    = "https://app.woopsocial.com/";
    // public thumbnail_image_path : string    = 'https://app.woopsocial.com/woopsocial/images/thumbnails/';
    // public static thumbnail_image_path_aux   : string    = 'https://app.woopsocial.com/woopsocial/images/posts/';
    // public temp_image_path      : string    = 'https://s3.amazonaws.com/woopsocial.com/images/posts/';
    // public static temp_image_path_aux   : string    = 'https://s3.amazonaws.com/woopsocial.com/images/posts/';
    // public video_image_path     : string    = 'https://s3.amazonaws.com/woopsocial.com/videos/posts/';
    // public avatar_path          : string    = 'https://s3.amazonaws.com/woopsocial.com/images/avatar/';

    // Development Paths 
    public enviroment = 'dev';
    public host_ip                          : string    = "http://192.168.100.9";
    public static host_ipctx                : string    = "http://192.168.100.9";
    public context_app_front                : string    = this.host_ip + ":4200/";
    public static context_app_front_aux     : string    = ControlService.host_ipctx + ":4200/";
    public context_app                      : string    = this.host_ip + ":8080/OpSocialBack/";
    public static context_app_aux           : string    = ControlService.host_ipctx + ":8080/OpSocialBack/";
    public thumbnail_image_path             : string    = ControlService.host_ipctx + ':8080/OpSocialBack/woopsocial/images/thumbnails/';
    public temp_image_path                  : string    = this.host_ip + ':8080/OpSocialBack/woopsocial/images/posts/';
    public static temp_image_path_aux       : string    = ControlService.host_ipctx + ':8080/OpSocialBack/woopsocial/images/posts/';
    public static thumbnail_image_path_aux  : string    = ControlService.host_ipctx + ':8080/OpSocialBack/woopsocial/images/thumbnails/';
    public video_image_path                 : string    = this.host_ip + ':8080/OpSocialBack/woopsocial/videos/posts/';
    public avatar_path                      : string    = this.host_ip + ':8080/OpSocialBack/woopsocial/images/avatar/';

    //Sabedot Dev
    // public context_app          : string    = "http://192.168.0.18:8080/OpSocialBack/";
    // public thumbnail_image_path : string    = 'http://192.168.0.18:8080/OpSocialBack/woopsocial/images/thumbnails/';
    // public temp_image_path      : string    = 'http://192.168.0.18:8080/OpSocialBack/woopsocial/images/temp/';
    // public video_image_path     : string    = 'http://192.168.0.18:8080/OpSocialBack/woopsocial/videos/posts/';
    // public avatar_path          : string    = 'http://192.168.0.18:8080/OpSocialBack/woopsocial/images/avatar/';

    public latUser: number = 0;

    public longUser: number = 0;

    uploadingAvatar = false;

    public userSource = new Subject<any>();
    public analystsSource = new Subject<any>();
    public adminSource = new Subject<any>();
    public urlSource = new Subject<string>();

    user$ = this.userSource.asObservable();
    analysts$ = this.analystsSource.asObservable();
    admins$ = this.adminSource.asObservable();
    url$ = this.urlSource.asObservable();

    profileAuth: Profile;

    constructor(private http: Http) {
        super();
    }

    public setUser(usr: User) {
        this.user = usr;
    }

    public getUser(): User {
        return this.user;
    }

    public setIsAuthUser(bool: boolean) {
        this.isAuthUser = bool;
    }

    public getIsAuthUser(): boolean {
        return this.isAuthUser;
    }

    public alterarStatusMenuTopo(vl: boolean): void {

    }

    mobileAndTabletcheck() {
        let check = false;
        ( a => {
            if (/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows ce|xda|xiino|android|ipad|playbook|silk/i.test(a) || /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(a.substr(0, 4))) {
                check = true;
            }
        })
        (navigator.userAgent || navigator.vendor);
        return check;
    };

    getInfoAccount() {
        return this.http.get(this.context_app + 'woopsocial/config', { headers: this.headers, withCredentials: true })
            .map(response => super.extractData(response))
            .catch(super.handleError);
    }
}

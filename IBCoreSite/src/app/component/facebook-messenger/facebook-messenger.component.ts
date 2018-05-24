import { Component, OnInit } from '@angular/core';

declare const $;

@Component({
    selector: 'facebook-messenger',
    templateUrl: './facebook-messenger.component.html',
    styleUrls: ['./facebook-messenger.component.css']
})
export class FacebookMessengerComponent implements OnInit {

    constructor() { }

    ngOnInit() {
        const t = {
            delay: 125, overlay: $('.fb-overlay'),
            widget: $('.fb-widget'),
            button: $('.fb-button')
        };

        setTimeout(function () {
            $('div.fb-livechat').fadeIn();
        },
            8 * t.delay);

        $('.ctrlq').on('click', function (e) {
            e.preventDefault();
            t.overlay.is(':visible') ?
                (t.overlay.fadeOut(t.delay),
                    t.widget.stop().animate({
                        opacity: 0
                    }, 2 * t.delay,
                        function () {
                            $(this).hide('slow'),
                                t.button.show();
                        }
                    )
                ) :
                t.button.fadeOut('medium', function () {
                    t.widget.stop().show().animate({
                        bottom: '40px',
                        opacity: 1
                    }, 2 * t.delay),
                        t.overlay.fadeIn(t.delay);
                });
            }
        );
    }

}

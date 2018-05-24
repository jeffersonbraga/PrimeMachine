import { Directive, ElementRef, HostListener, Input, OnChanges, OnDestroy, OnInit, Renderer2 } from '@angular/core';

declare const $;

@Directive({
    selector: '[streamWidthAdjust]'
})
export class StreamWidthAdjustDirective implements OnInit, OnChanges, OnDestroy {

    @Input()
    totalStreams: number;

    fixedWidthStream = 350;

    constructor(
        private ElementRef: ElementRef,
        private Renderer: Renderer2
    ) {

    }

    @HostListener('window:resize') onResize() {
        this.adjust();
    }

    ngOnInit() {
        this.adjust();
    }

    ngOnChanges() {
        this.adjust();
    }

    adjust() {
        let divisor: number;
        const qtdeMax: number = Math.floor(window.innerWidth / this.fixedWidthStream);

        divisor = this.totalStreams < qtdeMax ? this.totalStreams : qtdeMax;

        $('html').css('overflow', 'hidden');

        divisor = divisor >= 8 ? 8 : divisor;

        const streamWidth = (window.innerWidth - 63 - (12 * divisor)) / divisor;

        this.Renderer.setStyle(
            this.ElementRef.nativeElement,
            'width',
            streamWidth + 'px'
        );
    }

    public ngOnDestroy(): void {
        $('html').css('overflow', '');
    }
}

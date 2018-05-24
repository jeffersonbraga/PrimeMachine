import { Component, DoCheck, ElementRef, EventEmitter, HostListener, Input, Output, ViewChild } from '@angular/core';

import { NgBoxService } from './ngbox.service';


@Component({
    selector: 'my-ngbox, ngbox',
    templateUrl: 'ngbox.component.html',
    styleUrls: ['ngbox.component.css']
})
export class NgBoxComponent implements DoCheck {

    offsetHeight: number;
    interval: any;
    @Input() data: any;
    @Output() showMore = new EventEmitter();
    @ViewChild('ngBoxContent') elementView: ElementRef;
    @ViewChild('ngBoxButtons') elementButtons: ElementRef;

    constructor(
        public ngBox: NgBoxService
    ) {
    }

    ngDoCheck() {
        if (this.ngBox.open === true && this.elementView === undefined) {
            this.checkInterval();
        }
    }

    closeOutside($event) {
        if ($event.target.getAttribute('id') === 'ngBoxContent' || $event.target.getAttribute('id') === 'ngBoxWrapper') {
            this.closeNgBox();
        }
    }

    checkInterval() {
        const t = setInterval(() => {
            if (this.elementView && this.elementButtons  ) {
                this.resize();
                // Stop Loading on frames
                if (this.ngBox.current.type === 2 || this.ngBox.current.type === 3 || this.ngBox.current.type === 4) {
                    this.ngBox.loading = false;
                }

                clearInterval(t);
            }
        }, 10);
    }

    closeNgBox() {
        this.ngBox.open = false;
    }

    elementExist() {
        if (this.elementView !== undefined) {
            return true;
        }
        return false;
    }

    @HostListener('window:resize', ['$event'])
    resize() {
        // Resize big images

        if ( this.elementView && this.elementButtons) {
            const currentWidth = this.calcPercent(this.ngBox.current.width, window.innerWidth);
            const currentHeight = this.calcPercent(this.ngBox.current.height, window.innerHeight);

            const realWidth   = this.elementView.nativeElement.naturalWidth ?
                              this.elementView.nativeElement.naturalWidth : currentWidth;
            const realHeight  = this.elementView.nativeElement.naturalHeight ?
                              this.elementView.nativeElement.naturalHeight : currentHeight;


            const maxWidth    = Math.min((window.innerWidth - 70), currentWidth ? currentWidth : realWidth);
            const maxHeight   = Math.min((window.innerHeight - 60), currentHeight ? currentHeight : realHeight);

            const ratio       = Math.min( maxWidth / realWidth, maxHeight / realHeight);

            this.elementView.nativeElement.width    = realWidth * ratio;
            this.elementView.nativeElement.height   = realHeight * ratio;


            this.elementButtons.nativeElement.style.width = this.elementView.nativeElement.offsetWidth + 'px';

            // Calculate top padding
            this.offsetHeight = (window.innerHeight - 40 - this.elementView.nativeElement.offsetHeight) / 2;
            if (this.offsetHeight < 0) {
                this.offsetHeight = 0;
            }
        }
    }




    @HostListener('window:keydown', ['$event'])
    checkKeyPress(event: KeyboardEvent) {/*
        if ( event.keyCode === 39 ) {
            this.nextNgBox();
        }else if ( event.keyCode === 37 ) {
            this.previousNgBox();
        }else if ( event.keyCode === 27 ) {
            this.closeNgBox();
        }*/
    }

    calcPercent(value, of) {
        if (value !== undefined && value.toString().search('%') >= 0) {
            return of * parseInt(value.toString(), 0) / 100;
        }
        return value;
    }

    getHasGroup() {
        return this.ngBox.current.group !== undefined;
    }

    getCount() {
        return this.ngBox.images.filter(image => image.group === this.ngBox.current.group).length;
    }

    getCurrentIndex() {
        const currentGroup = this.ngBox.images.filter(image => image.group === this.ngBox.current.group);
        return currentGroup.map(function (e) {
            return e.id;
        }).indexOf(this.ngBox.current.id) + 1;
    }

    nextNgBox() {
        if ( this.ngBox.current.group === undefined ) {
            return false;
        }
        this.ngBox.loading = true;
        const currentGroup = this.ngBox.images.filter(image => image.group === this.ngBox.current.group);
        const pos = currentGroup.map(function (e) {
            return e.id;
        }).indexOf(this.ngBox.current.id);
        if (pos >= currentGroup.length - 1) {
            this.ngBox.current = currentGroup[0];
        } else {
            this.ngBox.current = currentGroup[pos + 1];
        }
        this.checkInterval();
    }

    previousNgBox() {
        if ( this.ngBox.current.group === undefined ) {
            return false;
        }
        this.ngBox.loading = true;
        const currentGroup = this.ngBox.images.filter(image => image.group === this.ngBox.current.group);
        let pos = currentGroup.map(function (e) {
            return e.id;
        }).indexOf(this.ngBox.current.id);
        if (pos === 0) {
            pos = currentGroup.length;
        }
        this.ngBox.current = currentGroup[pos - 1];
        this.checkInterval();
    }

    isLoaded() {
        if (this.ngBox.current.type === 1) {
            this.ngBox.loading = false;
        }

        this.checkInterval();
    }
}

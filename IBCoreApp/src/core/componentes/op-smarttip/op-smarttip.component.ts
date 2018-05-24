import { Op_TimePickerComponent } from 'core/componentes/op_timepicker/op-timepicker.component';
import { SmartTipService } from './../../services/smartTip/smartTip.service';
import 'fs';

import { Component, ElementRef, Input, OnInit, ViewChild, HostListener } from '@angular/core';

import { PostService } from './../../services/post/post.service';

declare var fs;
@Component({
  selector: 'op-smarttip-component',
  templateUrl: './op-smarttip.component.html',
  styleUrls: ['./op-smarttip.component.css'],
  // host: {
  //   '(document:click)': 'onClick($event)'
  // }
  /*,
animations: [
trigger('mouseenter', [
state('state01', style({
backgroundColor: '#eee',
transform: 'scale(1)'
}))
]),
trigger('mouseout', [
state('state02',   style({
backgroundColor: '#cfd8dc',
transform: 'scale(1.5)'
}))
])
]*/
})

export class OpSmartTipComponent implements OnInit {
  isConsented: any;

  public text: string;

  @Input('opTimePicker')
  optimepicker: Op_TimePickerComponent;

  @ViewChild('containerEmojiRight')
  content: any;

  @ViewChild('smartTip')
  smartTip: ElementRef;

  public listaEmoji: any = [];

  public listResultSearch: any = [];

  public listRecently: any = [];

  @Input('dataSource')
  public dataSource: any;

  @Input('dataField')
  public dataField = '';

  public classDropDownActive = 'state02';

  public searchText = '';

  public iniciado = false;

  constructor(
    private smartTipService : SmartTipService
  ) {  }

  ngOnInit() {
  }

  public loadDataProvider() {

  }

  @HostListener('document:click', ['$event', '$event.target']) onClick(event: MouseEvent, targetElement: HTMLElement) {
    const target = event.srcElement || event.target;
    if (!this.smartTip.nativeElement.contains(event.target) && target['getAttribute']('data-toggle-element') !== 'smartTip') {
      this.smartTipService.closeSmartTip();
    } else if (target['getAttribute']('data-toggle-element') === 'timeToSchedule') {
      this.dataSource.optimerPicker.hours	= 9;
      this.dataSource.optimerPicker.minutes	= 5;
      //this.dataSource.optimerPicker.timezone	= this.controlApp.account.timezone.value;
    }
  }
}
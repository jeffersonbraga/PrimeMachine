import { AfterViewInit, Component, ElementRef, Input, ViewChild } from '@angular/core';

declare const $;

@Component({
  selector: 'small-line-chart',
  templateUrl: './small-line-chart.component.html',
  styleUrls: ['./small-line-chart.component.css']
})
export class SmallLineChartComponent implements AfterViewInit {

  @Input()
  data;

  @Input()
  height;

  @Input()
  width;

  @ViewChild('chart') chart: ElementRef;

  ngAfterViewInit() {
      $(this.chart.nativeElement).sparkline(this.data, {
        type: 'line',
        height: this.height ? this.height : 35,
        width: this.width ? this.width : '100%',
        lineWidth: 2,
        lineColor: '#00d6f6',
        fillColor: '#4ce8ff',
        spotColor: '#00d6f6',
        minSpotColor: false,
        maxSpotColor: false,
        highlightLineColor: false
      });
  }

}

import { Component, Input, ViewChild } from '@angular/core';
import { IAsideSmallLineChart } from 'modelo/complements/Complements';

@Component({
  selector: 'aside-small-line-chart',
  templateUrl: './aside-small-line-chart.component.html',
  styleUrls: ['./aside-small-line-chart.component.css']
})
export class AsideSmallLineChartComponent {

  @Input() data: IAsideSmallLineChart;

  @ViewChild('smallLineChart')
  chart: any;
}

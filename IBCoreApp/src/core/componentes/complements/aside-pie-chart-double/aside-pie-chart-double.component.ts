import { Component, Input, ViewChild } from '@angular/core';
import { IAsidePieChartDouble } from 'modelo/complements/Complements';

@Component({
  selector: 'aside-pie-chart-double',
  templateUrl: './aside-pie-chart-double.component.html',
  styleUrls: ['./aside-pie-chart-double.component.css']
})
export class AsidePieChartDoubleComponent {

  @Input() data: IAsidePieChartDouble;

  @ViewChild('pieChart')
  chart: any;
}

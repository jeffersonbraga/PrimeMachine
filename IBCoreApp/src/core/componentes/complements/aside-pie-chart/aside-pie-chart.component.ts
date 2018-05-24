import { Component, Input, ViewChild } from '@angular/core';
import { IAsidePieChart } from 'modelo/complements/Complements';

@Component({
    selector: 'aside-pie-chart',
    templateUrl: './aside-pie-chart.component.html',
    styleUrls: ['./aside-pie-chart.component.css']
})

export class AsidePieChartComponent {

    // Use the same interface complement for aside-text-icon
    @Input() data: IAsidePieChart;

    @ViewChild('pieChart')
    chart: any;
}

import { Component, Input, OnInit, ViewChild } from '@angular/core';
import * as d3 from 'd3';

@Component({
    selector: 'stacked-column-chart',
    templateUrl: './stacked-column-chart.component.html',
    styleUrls: ['./stacked-column-chart.component.css']
})
export class StackedColumnChartComponent implements OnInit {

    @ViewChild('stackedColumnChart')
    chart: any;

    options;

    @Input()
    data;

    @Input()
    heigth;

    @Input()
    legends;

    @Input()
    rotateLabels;

    @Input()
    reduceXTicks;

    ngOnInit() {

        this.options = {
            chart: {
                type: 'multiBarChart',
                height: this.heigth ? this.heigth : 250,
                showLegend: this.legends ? this.legends.legends : false,
                staggerLabels: false,
                showValues: false,
                reduceXTicks: this.reduceXTicks,
                stacked: true,
                duration: 0,
                margin: {
                    // right: 45,
                    // left: 60,
                    top: 25
                },
                legend: {
                    color: this.legends ? this.legends.color : [],
                    margin: {
                        top: 1,
                        left: 0,
                        rigth: 0,
                        bottom: 0
                    }
                },
                useInteractiveGuideline: false,
                showControls: false,
                xAxis: {
                    tickPadding: 15,
                    rotateLabels: this.rotateLabels ? this.rotateLabels : 0
                },
                yAxis: {
                    tickFormat: d3.format('d'),
                    tickPadding: 15,
                    axisLabelDistance: -10
                }
            }
        };
    }
}

import { Component, Input, OnInit } from '@angular/core';
import * as d3 from 'd3';

@Component({
    selector: 'bubble-chart',
    templateUrl: './bubble-chart.component.html',
    styleUrls: ['./bubble-chart.component.css']
})
export class BubbleChartComponent implements OnInit {

    @Input()
    public static standardXAxisFormatPattern = '.02f';

    options;

    @Input()
    data;

    @Input()
    chartHeight = 250;

    @Input()
    color;

    @Input()
    tickValuesX: any[] = null;

    @Input()
    tickValuesY: any[] = null;

    @Input()
    public tickFormatFunctionY: Function = this.standardTickFormatFunctionYfunction;

    @Input()
    public tickFormatFunctionX: Function = this.standardTickFormatFunctionXfunction;

    @Input()
    showYAxis = true;

    ngOnInit() {
        this.options = {
            chart: {
                type: 'scatterChart',
                height: this.chartHeight,
                showLegend: false,
                showYAxis: this.showYAxis,
                color: this.color ? this.color : ['0x5fd6ff'],
                margin: {
                    top: 25
                },
                scatter: {
                    onlyCircles: true
                },
                useInteractiveGuideline: false,
                xAxis: {
                    tickPadding: 15,
                    tickValues: this.tickValuesX,
                    tickFormat: this.tickFormatFunctionX
                    // ,showMaxMin: false
                },
                yAxis: {
                    tickFormat: this.tickFormatFunctionY,
                    tickValues: this.tickValuesY,
                    tickPadding: 15,
                    axisLabelDistance: -10
                    // ,showMaxMin: false
                }
            }
        };
    }

    standardTickFormatFunctionYfunction(d) {
        return d3.format('.02f')(d);
    }

    standardTickFormatFunctionXfunction(d) {
        return d3.format(BubbleChartComponent.standardXAxisFormatPattern)(d);
    }
}

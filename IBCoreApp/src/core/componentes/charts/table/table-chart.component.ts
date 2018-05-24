import { Component, ElementRef, Input, ViewEncapsulation } from '@angular/core';

declare const $;

@Component({
    selector: 'table-chart',
    templateUrl: './table-chart.component.html',
    styleUrls: ['./table-chart.component.css'],
    encapsulation: ViewEncapsulation.None
})
export class TableChartComponent {

    @Input() data;

    @Input()
    isDataTable: boolean;

    table;
    htmlElement;

    constructor(private element: ElementRef) {
        this.htmlElement = this.element.nativeElement;
    }

    // ngAfterViewInit(){
    // 	if(this.isDataTable && this.data.rows){
    // 		this.table = $(this.htmlElement).find('table').DataTable({
    // 			"paging":   false,
    // 			"info":     false,
    // 			"searching": false,
    // 			"responsive": true
    // 		});
    // 	}
    // }
}

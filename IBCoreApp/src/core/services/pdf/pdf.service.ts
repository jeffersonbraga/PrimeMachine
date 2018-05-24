import { Injectable } from '@angular/core';
import * as d3 from 'd3';
import * as jsPDF from 'jspdf';
import { Nvd3Model } from 'modelo/css-models/nvd3';

declare const canvg;
declare const html2canvas;
declare const $;

@Injectable()
export class PdfService{
    CSSRules = Nvd3Model.NVD3MODEL;
    doc : jsPDF;

	constructor(){}

    new(){
        this.doc = new jsPDF('p','px','a4');
        this.doc.setTextColor(44, 44, 44);
        this.doc.setFont('Arial');
        this.doc.setFontSize(8);
    }

	setInlineStyle(){
        let tam = this.CSSRules.length;

        for (let i = 0; i < tam; i++){
            d3.select('#content-pdf').selectAll(this.CSSRules[i].selector).style(this.CSSRules[i].properties);
            // d3.selectAll(this.CSSRules[i].selector).style(this.CSSRules[i].properties);
        }
        
		let svgList = $('#content-pdf *');
		let component = this;
		
        tam = svgList.length;

		for(let i = 0; i < tam; i++){
			this.setMatchedCSSRules(svgList[i]);

			$(svgList[i]).find('*').each(function(){
				component.setMatchedCSSRules(this);
			})
		}
	}

	setMatchedCSSRules(element){
		let arr,
			cssRules,
			tam;

		arr = window.getMatchedCSSRules(element); 
			
		for (let j in arr){
			if(Number.parseInt(j) >= 0){
				cssRules = arr[j].style;

				tam = cssRules.length;

				for (let i=0; i < tam; i++){
					$(element).css(cssRules[i], cssRules[cssRules[i]]);
				}
			}
		}
	}

    appendSvg(container, x, y){
        let chartArea : any = $('#' + container).find("svg")[0];

        var svg = chartArea.outerHTML;

        var canvas = document.createElement('canvas');
        canvas.setAttribute('width', chartArea.offsetWidth);
        canvas.setAttribute('height', chartArea.offsetHeight);

        canvas.style.visibility = 'hidden';

        document.body.appendChild(canvas);
        canvg(canvas, svg, {scaleWidth: chartArea.offsetWidth, scaleHeight: chartArea.offsetHeight});
        var imgData = canvas.toDataURL("image/png");

        canvas.parentNode.removeChild(canvas);

        this.doc.addImage(imgData, 'PNG', x, y);
    }

    appendHtml(element, x, y, page){
        let component = this;

        return html2canvas(element, {
            onrendered: function(canvas) {
                var imgData = canvas.toDataURL("image/png");

                component.setPage(page);
                component.doc.addImage(imgData, 'PNG', x, y);
            }
        });
    }

    save(name: string){
        this.doc.save(name);
    }

    addPage(){
        this.doc.addPage();
    }

    addText(text, x, y){
        this.doc.text(x, y, text);
    }

    setPage(page: number){
        this.doc.setPage(page);
    }
}
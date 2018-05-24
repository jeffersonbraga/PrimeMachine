import { Component, Input } from '@angular/core';
import { IAsideTextIcon } from 'modelo/complements/Complements';


@Component({
    selector: 'footer-text-icon-alternative',
    templateUrl: './footer-text-icon-alternative.component.html',
    styleUrls: ['./footer-text-icon-alternative.component.css']
})

export class FooterTextIconAlternativeComponent {

    // Use the same interface complement for aside-text-icon
    @Input() data: IAsideTextIcon;

    @Input() isAbbreviateValue: Boolean = true;
}

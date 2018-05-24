import { Component, Input } from '@angular/core';
import { IAsideTextIcon } from 'modelo/complements/Complements';


@Component({
    selector: 'footer-text-icon',
    templateUrl: './footer-text-icon.component.html',
    styleUrls: ['./footer-text-icon.component.css']
})

export class FooterTextIconComponent {

    // Use the same interface complement for aside-text-icon
    @Input() data: IAsideTextIcon;

    @Input() isAbbreviateValue: Boolean = true;
}

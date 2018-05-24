import { Component, Input } from '@angular/core';
import { IAsideTextIcon } from 'modelo/complements/Complements';

@Component({
    selector: 'aside-text-icon',
    templateUrl: './aside-text-icon.component.html',
    styleUrls: ['./aside-text-icon.component.css']
})

export class AsideTextIconComponent {

    @Input() data: IAsideTextIcon;
}

import { Component, Input } from '@angular/core';
import { IAsideTextIcon } from 'modelo/complements/Complements';

@Component({
  selector: 'aside-horizontal-text-icon',
  templateUrl: './aside-horizontal-text-icon.component.html',
  styleUrls: ['./aside-horizontal-text-icon.component.css']
})
export class AsideHorizontalTextIconComponent {

  @Input() data: IAsideTextIcon;

}

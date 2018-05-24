import { Component, Input } from '@angular/core';
import { IAsideTextIcon } from 'modelo/complements/Complements';

@Component({
  selector: 'aside-text-growth',
  templateUrl: './aside-text-growth.component.html',
  styleUrls: ['./aside-text-growth.component.css']
})
export class AsideTextGrowthComponent {

  @Input() data: IAsideTextIcon;

}

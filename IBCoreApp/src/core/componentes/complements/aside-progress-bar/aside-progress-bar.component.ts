import { AfterViewInit, Component, Input } from '@angular/core';
import { IAsideProgressBar } from 'modelo/complements/Complements';

declare const $;

@Component({
  selector: 'aside-progress-bar',
  templateUrl: './aside-progress-bar.component.html',
  styleUrls: ['./aside-progress-bar.component.css']
})
export class AsideProgressBarComponent implements AfterViewInit {

  @Input() data: IAsideProgressBar;

  ngAfterViewInit(): void {
    $('[data-toggle="tooltip"]').tooltip();
  }
}

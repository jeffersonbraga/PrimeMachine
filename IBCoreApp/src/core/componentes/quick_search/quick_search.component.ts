import { Component } from '@angular/core';
import { Search } from 'modelo/search/Search';

@Component({
  selector: 'quick_search-root',
  templateUrl: './quick_search.component.html'
})

export class Quick_searchComponent {
  
  title = 'app works!';
  search : Search;

	ngOnInit() {
    this.search = new Search();
	}
}

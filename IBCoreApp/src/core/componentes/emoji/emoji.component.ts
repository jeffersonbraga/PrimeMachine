import 'fs';

import { Component, ElementRef, Input, OnInit, ViewChild, HostListener } from '@angular/core';

import { EmojiService } from './../../services/emojis/emojis.service';
import { PostService } from './../../services/post/post.service';

declare var fs;
@Component({
  selector: 'emoji-component',
  templateUrl: './emoji.component.html',
  styleUrls: ['./emoji.component.css'],
  // host: {
  //   '(document:click)': 'onClick($event)'
  // }
  /*,
animations: [
trigger('mouseenter', [
state('state01', style({
backgroundColor: '#eee',
transform: 'scale(1)'
}))
]),
trigger('mouseout', [
state('state02',   style({
backgroundColor: '#cfd8dc',
transform: 'scale(1.5)'
}))
])
]*/
})

export class EmojiComponent implements OnInit {
  isConsented: any;

  public text: string;

  @ViewChild('containerEmojiRight')
  content: any;

  @ViewChild('emoji')
  emoji: ElementRef;


  public listaEmoji: any = [];

  public listResultSearch: any = [];

  public listRecently: any = [];

  @Input('dataSource')
  public dataSource: any;

  @Input('dataField')
  public dataField = '';

  public classDropDownActive = 'state02';

  public searchText = '';

  public iniciado = false;

  constructor(
    private dataService: PostService,
    private emojiService: EmojiService
  ) {

    if (!this.iniciado) {
      this.dataService.getEmojisList().subscribe(
        result => this.loadEmojiHandler(result),
        error => console.log(error));
    }
  }

  ngOnInit() {
  }

  public loadDataProvider() {

    this.iniciado = true;
    this.dataService.getEmojisList().subscribe(
      result => this.loadEmojiHandler(result),
      error => console.log(error));
  }

  fillTextSearch() {

    this.listResultSearch = [];
    for (let i = 0; i < this.listaEmoji.length; i++) {

      const classEmoji: any = this.listaEmoji[i];

      if (classEmoji.emojis !== undefined) {
        for (let j = 0; j < classEmoji.emojis.length; j++) {

          const itemEmoji: any = classEmoji.emojis[j];

          itemEmoji.keywords.map(
            fav => {
              if (fav.indexOf(this.searchText) > -1) {
                this.listResultSearch.push(itemEmoji);
              }
            }
          );
        }
      }
    }
  }

  loadEmojiHandler(result: any) {
    /*
        for (var i : number = 0; i < result.length; i++) {
          for (var j : number = 0; j < result[i]["emojis"].length; j++) {
            result[i]["emojis"][j]["path_src"] = this.getElementSource(result[i]["emojis"][j]["path_src"]);
          }
          result[i]["path_src"] = this.getElementSource(result[i]);
        }
    */
    this.listaEmoji = result;
    this.loadRecentFromCookie();
  }

  loadRecentFromCookie() {

    this.listRecently = [];
    const cookieValue = this.getCookie('opEmoji');
    if (cookieValue !== '') {
      this.listRecently = JSON.parse(cookieValue);
    }

  }

  public getElementSource(item: any) {
    // return "#" + item;

    let it_nome_png = '';
    if (item !== undefined && item.unicode_alt !== undefined && item.unicode_alt !== '') {
      it_nome_png = '/assets/img/emoji/2/svg/' + item.unicode_alt + '.svg';
    } else {
      it_nome_png = '/assets/img/emoji/2/svg/' + item.unicode + '.svg';
    }

    // return "/assets/img/emoji/2/svg/" + it_nome_png +".svg";
    return it_nome_png;
  }

  public getElementSourceTwitter(item: any) {

    let it_nome_png = '';
    if (item !== undefined && item.unicode_alt !== undefined && item.unicode_alt !== '') {
      it_nome_png = item.unicode_alt;
    } else {
      it_nome_png = item.unicode;
    }

    return '/assets/img/emoji/2/72x72/' + it_nome_png + '.png';
  }

  public getElementUnicode(item: any) {

    let it_nome_png: any = '';
    if (item !== undefined && item.unicode_alt !== undefined && item.unicode_alt !== '') {
      it_nome_png = item.unicode_alt;
    } else {
      it_nome_png = item.unicode;
    }

    return it_nome_png;
  }

  public onMouseOver(emoji_item) {
    // console.log(emoji_item);
  }

  public getElementSourceByUnicode(item: any) {
    return '/assets/img/emoji/2/72x72/' + item + '.png';
  }

  public getDropDownActive(elem) {
    elem.currentTarget.heroState = 'state02';
  }

  public getDropDownInactive(elem) {
    elem.currentTarget.heroState = 'state01';
  }

  public getElementChar(item: any) {

    let it_nome_png: any = '';
    if (item !== undefined && item.char !== undefined && item.char !== '') {
      it_nome_png = item.char;
    } else {
      it_nome_png = item.char;
    }

    return it_nome_png;
  }

  public selectEmojiItem(emoji_it) {
    /*if (emoji_it != undefined && emoji_it.fitzpatrick != undefined && emoji_it.fitzpatrick) {
    this.getListOptions(emoji_it);
    } else {*/

    // cursorPosition = 3
    // datafieldLength = 12;
    // cursorToInsert = 9;

    let datafield = this.dataSource[this.dataField];
    const cursorPosition = this.emojiService.cursorPosition ? this.emojiService.cursorPosition : 0;

    if (datafield === undefined) {
      datafield = '';
    }

    const datafieldLength = datafield.length;
    const cursorToInsert = datafieldLength - cursorPosition;

    const emoji_string = String.fromCharCode(this.getElementUnicode(emoji_it));
    datafield = datafield.slice(0, cursorPosition) + this.getElementChar(emoji_it) + datafield.slice(cursorPosition);
    this.dataSource[this.dataField] = datafield;
    this.emojiService.cursorPosition = this.emojiService.cursorPosition + this.getElementChar(emoji_it).length;

    if (this.listRecently === undefined) {
      this.listRecently = [];
    }

    if (this.validateRecently(emoji_it)) {

      this.listRecently.push(emoji_it);
      this.setCookie('opEmoji', JSON.stringify(this.listRecently), 4);
      // this.setCookie("opEmoji", "", 4);
      // this.checkCookie("opEmoji");
    }
    // this.post.text = this.post.text + " \\" + this.getElementUnicode(emoji_it);
    // }
  }

  public getListOptions(emoji_it) {
    // document.getElementById("file-input").addEventListener("change", this.readSingleFile, false);
    this.readSingleFile();
  }

  readSingleFile() {
    /*    var fileName = "/assets/img/emoji/2/svg/";
    if (!fileName) {
    return;
    }

    var reader = new FileReader();

    reader.onload = file => {
    var contents: any = file.target;
    console.log(contents.result);
    this.text = contents.result;
    };
    reader.readAsText(fileName);
    */
    // var fs = require('fs');

    fs.readdir('./assets', function (err, files) {
      const page = fs.readFileSync('./preview-template.html').toString().replace(
        '{{emoji-list}}',
        '<li>' + files.map(function (file) {
          return file.replace('.ai', '').split('-').map(function (hex) {
            return '&#x' + hex.toUpperCase() + ';';
          }).join('');
        }).join('</li>\n      <li>') + '</li>'
      );
      fs.writeFileSync(
        './preview.html',
        page.replace(
          '{{emoji-options}}',
          JSON.stringify({
            size: 72
          })
        )
      );
      fs.writeFileSync(
        './preview-svg.html',
        page.replace(
          '{{emoji-options}}',
          JSON.stringify({
            folder: 'svg',
            ext: '.svg',
            base: ''
          })
        )
      );
    });
  }

  scrollToItem(itemName: string) {
    const element: any = document.getElementById(itemName);
    /*
    if (itemName == "people") {
    element =
    } else if (itemName == "objects") {
    element = document.getElementById("objects");
    } else if (itemName == "activity") {
    element = document.getElementById("activity");
    } else if (itemName == "nature") {
    element = document.getElementById("nature");
    } else if (itemName == "travel") {
    element = document.getElementById("travel");
    } else if (itemName == "symbols") {
    element = document.getElementById("symbols");
    } else if (itemName == "food") {
    element = document.getElementById("food");
    } else if (itemName == "flags") {
    element = document.getElementById("flags");
    } else if (itemName == "regional") {
    element = document.getElementById("regional");
    }
    */
    this.content.nativeElement.scrollTop = element.offsetTop - 40;
    // element.focus();
  }

  @HostListener('document:click', ['$event', '$event.target']) onClick(event: MouseEvent, targetElement: HTMLElement) {
    const target = event.srcElement || event.target;
    if (!this.emoji.nativeElement.contains(event.target) && target['getAttribute']('data-toggle-element') !== 'emoji') {
      this.emojiService.closeEmoji();
    }
  }

  setCookie(cname, cvalue, exdays) {
    const d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    const expires = 'expires=' + d.toUTCString();
    document.cookie = cname + '=' + cvalue + ';' + expires + ';path=/';
  }

  getCookie(cname) {
    const name = cname + '=';
    // console.log(document.cookie);
    const ca = document.cookie.split(';');
    for (let i = 0; i < ca.length; i++) {
      let c = ca[i];
      while (c.charAt(0) === ' ') {
        c = c.substring(1);
      }
      if (c.indexOf(name) === 0) {
        const itReturn = c.substring(name.length, c.length);
        // console.log(itReturn);
        return itReturn;
      }
    }
    return '';
  }

  checkCookie(nameCookie) {
    /*var cookieRecently = this.getCookie(nameCookie);
    if (cookieRecently != "") {
      alert("Welcome again " + cookieRecently);
    } else {
      cookieRecently = prompt("Please enter your name:", "");
      if (cookieRecently != "" && cookieRecently != null) {
        this.setCookie(nameCookie, cookieRecently, 365);
      }
    }*/
  }

  clearRecently() {
    this.setCookie('opEmoji', '', 0);
    this.loadRecentFromCookie();
  }

  validateRecently(itemCompare) {

    for (let i = 0; i < this.listRecently.length; i++) {

      if (this.listRecently[i].unicode === itemCompare.unicode) {
        return false;
      }
    }
    return true;
  }
}

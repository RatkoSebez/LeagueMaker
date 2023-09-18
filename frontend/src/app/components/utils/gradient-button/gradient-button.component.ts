import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-gradient-button',
  templateUrl: './gradient-button.component.html',
  styleUrls: ['./gradient-button.component.css'],
})
export class GradientButtonComponent implements OnInit {
  @Input() title = '';
  @Input() form = new FormGroup({});
  @Input() type = 'text';
  @Output() onClick = new EventEmitter<any>();

  constructor() {}

  ngOnInit(): void {}

  onClickButton(event: any) {
    this.onClick.emit(event);
  }
}

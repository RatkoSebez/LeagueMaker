import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-input',
  templateUrl: './input.component.html',
  styleUrls: ['./input.component.css'],
})
export class InputComponent implements OnInit {
  @Input() placeholder = '';
  @Input() type = '';
  @Input() control = new FormControl();
  @Input() label = '';
  @Input() labelDanger = '';
  @Input() errorMessage = '';
  @Input() marginBottom = 'mb-2';
  @Input() isForm = true;

  constructor() {}

  ngOnInit(): void {}

  test() {
    // console.log('promene');
    // console.log(this.control.value);
  }
}

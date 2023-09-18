import { Component, OnInit, Input } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-textarea',
  templateUrl: './textarea.component.html',
  styleUrls: ['./textarea.component.css'],
})
export class TextareaComponent implements OnInit {
  @Input() placeholder = '';
  @Input() type = '';
  @Input() control = new FormControl();
  @Input() label = '';
  @Input() labelDanger = '';
  @Input() errorMessage = '';

  constructor() {}

  ngOnInit(): void {}
}

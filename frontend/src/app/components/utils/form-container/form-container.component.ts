import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-form-container',
  templateUrl: './form-container.component.html',
  styleUrls: ['./form-container.component.css'],
})
export class FormContainerComponent implements OnInit {
  @Input() formGroup!: FormGroup;
  @Input() formTitle = '';

  constructor() {}

  ngOnInit(): void {}
}

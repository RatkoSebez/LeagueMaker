import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css'],
})
export class ModalComponent implements OnInit {
  @Input() modalTitle = '';
  @Input() modalId = '';
  @Output() onClick = new EventEmitter<any>();
  @Input() form = new FormGroup({});

  constructor() {}

  ngOnInit(): void {}

  onClickButton(event: any) {
    this.onClick.emit(event);
  }
}

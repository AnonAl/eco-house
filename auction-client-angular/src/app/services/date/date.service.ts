import { Injectable } from '@angular/core';

@Injectable()
export class DateService {

  constructor() { }

  getDateTime(days?:number) {
    let currentDate = new Date();
    if (days) {
      currentDate.setDate(currentDate.getDate() + days);
    }
    return this.currentDateToString(currentDate);
  }

  private currentDateToString(date) {
    var dd = date.getDate();
    if (dd < 10) dd = "0" + dd;

    var mm = date.getMonth() + 1;
    if (mm < 10) mm = '0' + mm;

    var yy = date.getFullYear();
    if (yy < 10) yy = '0' + yy;

    var hh = date.getHours();

    var min = date.getMinutes();
    if (min < 10) min = '0' + min;

    return dd + '.' + mm + '.' + yy + " " + hh + ":" + min;
  }

}
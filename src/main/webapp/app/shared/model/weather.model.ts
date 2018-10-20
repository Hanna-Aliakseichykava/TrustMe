import { Moment } from 'moment';

export interface IWeather {
  id?: number;
  humid?: number;
  temp?: number;
  tempMin?: number;
  tempMax?: number;
  date?: Moment;
  weight?: number;
  json?: any;
}

export const defaultValue: Readonly<IWeather> = {};

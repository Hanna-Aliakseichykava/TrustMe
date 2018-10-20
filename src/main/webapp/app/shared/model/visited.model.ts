import { Moment } from 'moment';
import { IWeather } from 'app/shared/model//weather.model';
import { IPlace } from 'app/shared/model//place.model';

export interface IVisited {
  id?: number;
  score?: number;
  date?: Moment;
  weather?: IWeather;
  place?: IPlace;
}

export const defaultValue: Readonly<IVisited> = {};

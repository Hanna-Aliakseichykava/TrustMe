export const enum PlaceType {
  GMAP = 'GMAP',
  EVENT = 'EVENT'
}

export interface IPlace {
  id?: number;
  title?: string;
  category?: string;
  type?: PlaceType;
  lat?: number;
  lon?: number;
  json?: any;
}

export const defaultValue: Readonly<IPlace> = {};

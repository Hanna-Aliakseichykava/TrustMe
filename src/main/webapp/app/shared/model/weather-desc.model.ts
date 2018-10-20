export interface IWeatherDesc {
  id?: number;
  weight?: number;
  shortDesc?: string;
  longDesc?: string;
}

export const defaultValue: Readonly<IWeatherDesc> = {};

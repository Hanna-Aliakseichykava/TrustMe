import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IWeatherDesc, defaultValue } from 'app/shared/model/weather-desc.model';

export const ACTION_TYPES = {
  FETCH_WEATHER: 'weather/FETCH_WEATHER',
  RESET: 'weather/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entity: defaultValue
};

export type WeatherWidgetState = Readonly<typeof initialState>;

// Reducer

export default (state: WeatherWidgetState = initialState, action): WeatherWidgetState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_WEATHER):
      return {
        ...state,
        errorMessage: null,
        loading: true
      };
    case FAILURE(ACTION_TYPES.FETCH_WEATHER):
      return {
        ...state,
        loading: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_WEATHER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/weather';

// Actions

export const getEntity: ICrudGetAction<IWeatherDesc> = () => {
  const requestUrl = `${apiUrl}`;
  return {
    type: ACTION_TYPES.FETCH_WEATHER,
    payload: axios.get<IWeather>(requestUrl)
  };
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});

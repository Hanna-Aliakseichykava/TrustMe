import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IWeather, defaultValue } from 'app/shared/model/weather.model';

export const ACTION_TYPES = {
  FETCH_WEATHER_LIST: 'weather/FETCH_WEATHER_LIST',
  FETCH_WEATHER: 'weather/FETCH_WEATHER',
  CREATE_WEATHER: 'weather/CREATE_WEATHER',
  UPDATE_WEATHER: 'weather/UPDATE_WEATHER',
  DELETE_WEATHER: 'weather/DELETE_WEATHER',
  SET_BLOB: 'weather/SET_BLOB',
  RESET: 'weather/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IWeather>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type WeatherState = Readonly<typeof initialState>;

// Reducer

export default (state: WeatherState = initialState, action): WeatherState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_WEATHER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_WEATHER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_WEATHER):
    case REQUEST(ACTION_TYPES.UPDATE_WEATHER):
    case REQUEST(ACTION_TYPES.DELETE_WEATHER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_WEATHER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_WEATHER):
    case FAILURE(ACTION_TYPES.CREATE_WEATHER):
    case FAILURE(ACTION_TYPES.UPDATE_WEATHER):
    case FAILURE(ACTION_TYPES.DELETE_WEATHER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_WEATHER_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_WEATHER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_WEATHER):
    case SUCCESS(ACTION_TYPES.UPDATE_WEATHER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_WEATHER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.SET_BLOB:
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/weathers';

// Actions

export const getEntities: ICrudGetAllAction<IWeather> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_WEATHER_LIST,
    payload: axios.get<IWeather>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IWeather> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_WEATHER,
    payload: axios.get<IWeather>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IWeather> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_WEATHER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IWeather> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_WEATHER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IWeather> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_WEATHER,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});

import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IWeatherDesc, defaultValue } from 'app/shared/model/weather-desc.model';

export const ACTION_TYPES = {
  FETCH_WEATHERDESC_LIST: 'weatherDesc/FETCH_WEATHERDESC_LIST',
  FETCH_WEATHERDESC: 'weatherDesc/FETCH_WEATHERDESC',
  CREATE_WEATHERDESC: 'weatherDesc/CREATE_WEATHERDESC',
  UPDATE_WEATHERDESC: 'weatherDesc/UPDATE_WEATHERDESC',
  DELETE_WEATHERDESC: 'weatherDesc/DELETE_WEATHERDESC',
  RESET: 'weatherDesc/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IWeatherDesc>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type WeatherDescState = Readonly<typeof initialState>;

// Reducer

export default (state: WeatherDescState = initialState, action): WeatherDescState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_WEATHERDESC_LIST):
    case REQUEST(ACTION_TYPES.FETCH_WEATHERDESC):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_WEATHERDESC):
    case REQUEST(ACTION_TYPES.UPDATE_WEATHERDESC):
    case REQUEST(ACTION_TYPES.DELETE_WEATHERDESC):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_WEATHERDESC_LIST):
    case FAILURE(ACTION_TYPES.FETCH_WEATHERDESC):
    case FAILURE(ACTION_TYPES.CREATE_WEATHERDESC):
    case FAILURE(ACTION_TYPES.UPDATE_WEATHERDESC):
    case FAILURE(ACTION_TYPES.DELETE_WEATHERDESC):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_WEATHERDESC_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_WEATHERDESC):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_WEATHERDESC):
    case SUCCESS(ACTION_TYPES.UPDATE_WEATHERDESC):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_WEATHERDESC):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/weather-descs';

// Actions

export const getEntities: ICrudGetAllAction<IWeatherDesc> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_WEATHERDESC_LIST,
  payload: axios.get<IWeatherDesc>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IWeatherDesc> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_WEATHERDESC,
    payload: axios.get<IWeatherDesc>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IWeatherDesc> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_WEATHERDESC,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IWeatherDesc> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_WEATHERDESC,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IWeatherDesc> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_WEATHERDESC,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});

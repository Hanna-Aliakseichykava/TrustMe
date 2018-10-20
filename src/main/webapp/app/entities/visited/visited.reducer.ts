import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IVisited, defaultValue } from 'app/shared/model/visited.model';

export const ACTION_TYPES = {
  FETCH_VISITED_LIST: 'visited/FETCH_VISITED_LIST',
  FETCH_VISITED: 'visited/FETCH_VISITED',
  CREATE_VISITED: 'visited/CREATE_VISITED',
  UPDATE_VISITED: 'visited/UPDATE_VISITED',
  DELETE_VISITED: 'visited/DELETE_VISITED',
  RESET: 'visited/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IVisited>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type VisitedState = Readonly<typeof initialState>;

// Reducer

export default (state: VisitedState = initialState, action): VisitedState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_VISITED_LIST):
    case REQUEST(ACTION_TYPES.FETCH_VISITED):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_VISITED):
    case REQUEST(ACTION_TYPES.UPDATE_VISITED):
    case REQUEST(ACTION_TYPES.DELETE_VISITED):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_VISITED_LIST):
    case FAILURE(ACTION_TYPES.FETCH_VISITED):
    case FAILURE(ACTION_TYPES.CREATE_VISITED):
    case FAILURE(ACTION_TYPES.UPDATE_VISITED):
    case FAILURE(ACTION_TYPES.DELETE_VISITED):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_VISITED_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_VISITED):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_VISITED):
    case SUCCESS(ACTION_TYPES.UPDATE_VISITED):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_VISITED):
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

const apiUrl = 'api/visiteds';

// Actions

export const getEntities: ICrudGetAllAction<IVisited> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_VISITED_LIST,
    payload: axios.get<IVisited>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IVisited> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_VISITED,
    payload: axios.get<IVisited>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IVisited> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_VISITED,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IVisited> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_VISITED,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IVisited> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_VISITED,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});

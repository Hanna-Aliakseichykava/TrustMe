import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import WeatherDesc from './weather-desc';
import WeatherDescDetail from './weather-desc-detail';
import WeatherDescUpdate from './weather-desc-update';
import WeatherDescDeleteDialog from './weather-desc-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={WeatherDescUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={WeatherDescUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={WeatherDescDetail} />
      <ErrorBoundaryRoute path={match.url} component={WeatherDesc} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={WeatherDescDeleteDialog} />
  </>
);

export default Routes;

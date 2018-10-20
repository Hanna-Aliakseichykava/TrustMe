import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Weather from './weather';
import WeatherDetail from './weather-detail';
import WeatherUpdate from './weather-update';
import WeatherDeleteDialog from './weather-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={WeatherUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={WeatherUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={WeatherDetail} />
      <ErrorBoundaryRoute path={match.url} component={Weather} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={WeatherDeleteDialog} />
  </>
);

export default Routes;

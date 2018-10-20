import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Place from './place';
import Weather from './weather';
import WeatherDesc from './weather-desc';
import Visited from './visited';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/place`} component={Place} />
      <ErrorBoundaryRoute path={`${match.url}/weather`} component={Weather} />
      <ErrorBoundaryRoute path={`${match.url}/weather-desc`} component={WeatherDesc} />
      <ErrorBoundaryRoute path={`${match.url}/visited`} component={Visited} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;

import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Visited from './visited';
import VisitedDetail from './visited-detail';
import VisitedUpdate from './visited-update';
import VisitedDeleteDialog from './visited-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VisitedUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VisitedUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VisitedDetail} />
      <ErrorBoundaryRoute path={match.url} component={Visited} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={VisitedDeleteDialog} />
  </>
);

export default Routes;

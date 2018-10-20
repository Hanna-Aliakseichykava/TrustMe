import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IWeather } from 'app/shared/model/weather.model';
import { getEntities as getWeathers } from 'app/entities/weather/weather.reducer';
import { IPlace } from 'app/shared/model/place.model';
import { getEntities as getPlaces } from 'app/entities/place/place.reducer';
import { getEntity, updateEntity, createEntity, reset } from './visited.reducer';
import { IVisited } from 'app/shared/model/visited.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IVisitedUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IVisitedUpdateState {
  isNew: boolean;
  weatherId: string;
  placeId: string;
}

export class VisitedUpdate extends React.Component<IVisitedUpdateProps, IVisitedUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      weatherId: '0',
      placeId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getWeathers();
    this.props.getPlaces();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { visitedEntity } = this.props;
      const entity = {
        ...visitedEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/visited');
  };

  render() {
    const { visitedEntity, weathers, places, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="trustMeApp.visited.home.createOrEditLabel">Create or edit a Visited</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : visitedEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="visited-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="scoreLabel" for="score">
                    Score
                  </Label>
                  <AvField id="visited-score" type="string" className="form-control" name="score" />
                </AvGroup>
                <AvGroup>
                  <Label id="dateLabel" for="date">
                    Date
                  </Label>
                  <AvField id="visited-date" type="date" className="form-control" name="date" />
                </AvGroup>
                <AvGroup>
                  <Label for="weather.id">Weather</Label>
                  <AvInput id="visited-weather" type="select" className="form-control" name="weather.id">
                    <option value="" key="0" />
                    {weathers
                      ? weathers.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="place.id">Place</Label>
                  <AvInput id="visited-place" type="select" className="form-control" name="place.id">
                    <option value="" key="0" />
                    {places
                      ? places.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/visited" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  weathers: storeState.weather.entities,
  places: storeState.place.entities,
  visitedEntity: storeState.visited.entity,
  loading: storeState.visited.loading,
  updating: storeState.visited.updating,
  updateSuccess: storeState.visited.updateSuccess
});

const mapDispatchToProps = {
  getWeathers,
  getPlaces,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(VisitedUpdate);

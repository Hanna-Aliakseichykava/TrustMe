import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './weather.reducer';
import { IWeather } from 'app/shared/model/weather.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IWeatherUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IWeatherUpdateState {
  isNew: boolean;
}

export class WeatherUpdate extends React.Component<IWeatherUpdateProps, IWeatherUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { weatherEntity } = this.props;
      const entity = {
        ...weatherEntity,
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
    this.props.history.push('/entity/weather');
  };

  render() {
    const { weatherEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    const { json } = weatherEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="trustMeApp.weather.home.createOrEditLabel">Create or edit a Weather</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : weatherEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="weather-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="humidLabel" for="humid">
                    Humid
                  </Label>
                  <AvField id="weather-humid" type="string" className="form-control" name="humid" />
                </AvGroup>
                <AvGroup>
                  <Label id="tempLabel" for="temp">
                    Temp
                  </Label>
                  <AvField id="weather-temp" type="string" className="form-control" name="temp" />
                </AvGroup>
                <AvGroup>
                  <Label id="tempMinLabel" for="tempMin">
                    Temp Min
                  </Label>
                  <AvField id="weather-tempMin" type="string" className="form-control" name="tempMin" />
                </AvGroup>
                <AvGroup>
                  <Label id="tempMaxLabel" for="tempMax">
                    Temp Max
                  </Label>
                  <AvField id="weather-tempMax" type="string" className="form-control" name="tempMax" />
                </AvGroup>
                <AvGroup>
                  <Label id="dateLabel" for="date">
                    Date
                  </Label>
                  <AvField id="weather-date" type="date" className="form-control" name="date" />
                </AvGroup>
                <AvGroup>
                  <Label id="weightLabel" for="weight">
                    Weight
                  </Label>
                  <AvField id="weather-weight" type="string" className="form-control" name="weight" />
                </AvGroup>
                <AvGroup>
                  <Label id="jsonLabel" for="json">
                    Json
                  </Label>
                  <AvInput id="weather-json" type="textarea" name="json" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/weather" replace color="info">
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
  weatherEntity: storeState.weather.entity,
  loading: storeState.weather.loading,
  updating: storeState.weather.updating,
  updateSuccess: storeState.weather.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(WeatherUpdate);

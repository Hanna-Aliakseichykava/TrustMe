import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './weather-desc.reducer';
import { IWeatherDesc } from 'app/shared/model/weather-desc.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IWeatherDescUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IWeatherDescUpdateState {
  isNew: boolean;
}

export class WeatherDescUpdate extends React.Component<IWeatherDescUpdateProps, IWeatherDescUpdateState> {
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

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { weatherDescEntity } = this.props;
      const entity = {
        ...weatherDescEntity,
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
    this.props.history.push('/entity/weather-desc');
  };

  render() {
    const { weatherDescEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="trustMeApp.weatherDesc.home.createOrEditLabel">Create or edit a WeatherDesc</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : weatherDescEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="weather-desc-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="weightLabel" for="weight">
                    Weight
                  </Label>
                  <AvField id="weather-desc-weight" type="string" className="form-control" name="weight" />
                </AvGroup>
                <AvGroup>
                  <Label id="shortDescLabel" for="shortDesc">
                    Short Desc
                  </Label>
                  <AvField id="weather-desc-shortDesc" type="text" name="shortDesc" />
                </AvGroup>
                <AvGroup>
                  <Label id="longDescLabel" for="longDesc">
                    Long Desc
                  </Label>
                  <AvField id="weather-desc-longDesc" type="text" name="longDesc" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/weather-desc" replace color="info">
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
  weatherDescEntity: storeState.weatherDesc.entity,
  loading: storeState.weatherDesc.loading,
  updating: storeState.weatherDesc.updating,
  updateSuccess: storeState.weatherDesc.updateSuccess
});

const mapDispatchToProps = {
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
)(WeatherDescUpdate);

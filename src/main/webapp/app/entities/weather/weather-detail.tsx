import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './weather.reducer';
import { IWeather } from 'app/shared/model/weather.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWeatherDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class WeatherDetail extends React.Component<IWeatherDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { weatherEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Weather [<b>{weatherEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="humid">Humid</span>
            </dt>
            <dd>{weatherEntity.humid}</dd>
            <dt>
              <span id="temp">Temp</span>
            </dt>
            <dd>{weatherEntity.temp}</dd>
            <dt>
              <span id="tempMin">Temp Min</span>
            </dt>
            <dd>{weatherEntity.tempMin}</dd>
            <dt>
              <span id="tempMax">Temp Max</span>
            </dt>
            <dd>{weatherEntity.tempMax}</dd>
            <dt>
              <span id="date">Date</span>
            </dt>
            <dd>
              <TextFormat value={weatherEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="weight">Weight</span>
            </dt>
            <dd>{weatherEntity.weight}</dd>
            <dt>
              <span id="json">Json</span>
            </dt>
            <dd>{weatherEntity.json}</dd>
          </dl>
          <Button tag={Link} to="/entity/weather" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/weather/${weatherEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ weather }: IRootState) => ({
  weatherEntity: weather.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(WeatherDetail);

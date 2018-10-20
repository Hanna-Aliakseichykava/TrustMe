import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './weather-desc.reducer';
import { IWeatherDesc } from 'app/shared/model/weather-desc.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWeatherDescDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class WeatherDescDetail extends React.Component<IWeatherDescDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { weatherDescEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            WeatherDesc [<b>{weatherDescEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="weight">Weight</span>
            </dt>
            <dd>{weatherDescEntity.weight}</dd>
            <dt>
              <span id="shortDesc">Short Desc</span>
            </dt>
            <dd>{weatherDescEntity.shortDesc}</dd>
            <dt>
              <span id="longDesc">Long Desc</span>
            </dt>
            <dd>{weatherDescEntity.longDesc}</dd>
          </dl>
          <Button tag={Link} to="/entity/weather-desc" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/weather-desc/${weatherDescEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ weatherDesc }: IRootState) => ({
  weatherDescEntity: weatherDesc.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(WeatherDescDetail);

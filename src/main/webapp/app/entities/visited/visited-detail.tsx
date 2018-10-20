import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './visited.reducer';
import { IVisited } from 'app/shared/model/visited.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVisitedDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class VisitedDetail extends React.Component<IVisitedDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { visitedEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Visited [<b>{visitedEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="score">Score</span>
            </dt>
            <dd>{visitedEntity.score}</dd>
            <dt>
              <span id="date">Date</span>
            </dt>
            <dd>
              <TextFormat value={visitedEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>Weather</dt>
            <dd>{visitedEntity.weather ? visitedEntity.weather.id : ''}</dd>
            <dt>Place</dt>
            <dd>{visitedEntity.place ? visitedEntity.place.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/visited" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/visited/${visitedEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ visited }: IRootState) => ({
  visitedEntity: visited.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(VisitedDetail);

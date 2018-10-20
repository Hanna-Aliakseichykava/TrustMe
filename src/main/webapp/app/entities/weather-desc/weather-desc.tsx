import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './weather-desc.reducer';
import { IWeatherDesc } from 'app/shared/model/weather-desc.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWeatherDescProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class WeatherDesc extends React.Component<IWeatherDescProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { weatherDescList, match } = this.props;
    return (
      <div>
        <h2 id="weather-desc-heading">
          Weather Descs
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Weather Desc
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Weight</th>
                <th>Short Desc</th>
                <th>Long Desc</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {weatherDescList.map((weatherDesc, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${weatherDesc.id}`} color="link" size="sm">
                      {weatherDesc.id}
                    </Button>
                  </td>
                  <td>{weatherDesc.weight}</td>
                  <td>{weatherDesc.shortDesc}</td>
                  <td>{weatherDesc.longDesc}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${weatherDesc.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${weatherDesc.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${weatherDesc.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ weatherDesc }: IRootState) => ({
  weatherDescList: weatherDesc.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(WeatherDesc);

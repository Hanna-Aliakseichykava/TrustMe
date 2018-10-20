import './home.css';

import React from 'react';
import { Link } from 'react-router-dom';

import { connect } from 'react-redux';
import { Row, Col, Alert } from 'reactstrap';

import { IRootState } from 'app/shared/reducers';
import { getSession } from 'app/shared/reducers/authentication';

import { WeatherWidget } from 'app/modules/weather/weather';

export interface IHomeProp extends StateProps, DispatchProps {}

export class Home extends React.Component<IHomeProp> {
  componentDidMount() {
    this.props.getSession();
  }

  render() {
    const { account } = this.props;
    return (
      <>
        <Row>
          <Col md="9">
            <WeatherWidget />
          </Col>
        </Row>
        <hr />
        <Row>
          <Col md="9">
            <div class="container">
              <div class="row">
                <div class="col-md-4">
                  <div class="card mb-4 shadow-sm">
                    <div class="card-body">
                      <h2>Музей истории Могилева</h2>
                      <p class="card-text">улица Ленинская 1А, Могилёв</p>
                      <div class="d-flex justify-content-between align-items-center">
                        <div class="btn-group">
                          <button type="button" class="btn btn-success">
                            View
                          </button>
                          <button type="button" class="btn btn-sm btn-outline-secondary">
                            I'm visiting
                          </button>
                        </div>
                        <small class="text-muted">9*</small>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-md-4">
                  <div class="card mb-4 shadow-sm">
                    <div class="card-body">
                      <h2>Художественный музей им. П.В. Масленикова</h2>
                      <p class="card-text">улица Миронова 33, Могилёв</p>
                      <div class="d-flex justify-content-between align-items-center">
                        <div class="btn-group">
                          <button type="button" class="btn btn-success">
                            View
                          </button>
                          <button type="button" class="btn btn-sm btn-outline-secondary">
                            I'm visiting
                          </button>
                        </div>
                        <small class="text-muted">9*</small>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-md-4">
                  <div class="card mb-4 shadow-sm">
                    <div class="card-body">
                      <h2>Художественный Музей В.К. Бялыницкого-бирули Филиал Национального Художественного Музея</h2>
                      <p class="card-text">улица Ленинская 37, Могилёв</p>
                      <div class="d-flex justify-content-between align-items-center">
                        <div class="btn-group">
                          <button type="button" class="btn btn-success">
                            View
                          </button>
                          <button type="button" class="btn btn-sm btn-outline-secondary">
                            I'm visiting
                          </button>
                        </div>
                        <small class="text-muted">9 *</small>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </Col>
        </Row>
      </>
    );
  }
}

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated
});

const mapDispatchToProps = { getSession };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Home);

import React from 'react';
import { Link } from 'react-router-dom';

import { connect } from 'react-redux';
import { Row, Col, Alert } from 'reactstrap';

import { IRootState } from 'app/shared/reducers';
import { getSession } from 'app/shared/reducers/authentication';

import Map from './map.tsx';

export class SimpleMapPage extends React.Component {
  componentDidMount() {
    // this.props.getSession();
  }
  constructor(props) {
    super(props);
  }

  render() {
    const { account } = this.props;
    return (
      <Row>
        <Col md="9">
          <h2>Welcome, Java Hipster!</h2>
          <p className="lead">This is your homepage</p>
          <div>
            <Alert color="success">You are logged in as user {account.login}.</Alert>
          </div>
          }<p>If you have any question on JHipster:</p>
          <div className="App">
            <header className="App-header">
              <img src={logo} className="App-logo" alt="logo" />
              <h1 className="App-title">Welcome to React</h1>
            </header>
            <Map />
          </div>
        </Col>
        <Col md="3" className="pad">
          <span className="hipster rounded" />
        </Col>
      </Row>
    );
  }
}

export default SimpleMapPage;

import React from 'react';
import { Link } from 'react-router-dom';

import { connect } from 'react-redux';
import { Row, Col, Alert } from 'reactstrap';

import { IRootState } from 'app/shared/reducers';
import { getSession } from 'app/shared/reducers/authentication';

import GoogleMapReact from 'google-map-react';

const AnyReactComponent = ({ text }) => <div>{text}</div>;

export interface ISimpleMapPage extends StateProps, DispatchProps {}

export class SimpleMapPage extends React.Component<ISimpleMapPage> {
  static defaultProps = {
    center: {
      lat: 53.8944821,
      lng: 30.3319835
    },
    zoom: 15
  };

  componentDidMount() {
    // this.props.getSession();
  }

  // shouldComponentUpdate = shouldPureComponentUpdate;

  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div style={{ height: '100vh', width: '100%' }}>
        <GoogleMapReact
          bootstrapURLKeys={{ key: 'AIzaSyAfHkXgg236YmiXVOsthl4eCPAhYMLuMds' }}
          defaultCenter={this.props.center}
          defaultZoom={this.props.zoom}
        >
          <AnyReactComponent lat={53.8944821} lng={30.3319835} text={'Kreyser Avrora'} />
          <AnyReactComponent lat={53.9080483} lng={30.33854380000001} text={'Kreyser Avrora'} />
          <AnyReactComponent lat={53.8994807} lng={30.33877910000001} text={'Kreyser Avrora'} />
        </GoogleMapReact>
      </div>
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
)(SimpleMapPage);

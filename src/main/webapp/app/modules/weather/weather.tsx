import './weather.css';

import React from 'react';
import { connect } from 'react-redux';

import { Link, RouteComponentProps } from 'react-router-dom';
import { Navbar, Nav, NavbarToggler, NavbarBrand, Collapse } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import LoadingBar from 'react-redux-loading-bar';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './weather.reducer';
import { IWeatherDesc } from 'app/shared/model/weather-desc.model';

export interface IWeatherWidgetState {
  weight: number;
}

export interface IWeatherWidgetProps extends StateProps, RouteComponentProps<{ url: string }> {}

export class WeatherWidget extends React.Component<IWeatherWidgetProps, IWeatherWidgetState> {
  constructor(props) {
    super(props);
    this.state = {
      weight: null
    };
  }

  renderWeatherImg = () => {
    const url = 'content/images/weather/w' + this.state.weather + '.png';
    if (this.state.weather) {
      return '<img src=' + url + '/>';
    } else {
      return null;
    }
  };

  render() {
    const { weatherItem } = this.props;

    return (
      <div>
        <div class="brand-icon">
          {/* {this.renderWeatherImg()} */}
          <img class="float-left" src="content/images/weather/w1.png" />
          <h2>It is Cold today</h2>
          <span>How about going in some warm place?</span>
        </div>
      </div>
    );
  }

  componentDidMount() {
    getEntity();
  }
}

const mapStateToProps = ({ weatherWidg }: IRootState) => ({
  weatherItem: weatherWidg.entity
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(WeatherWidget);

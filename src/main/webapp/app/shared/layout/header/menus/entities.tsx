import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name="Entities" id="entity-menu">
    <DropdownItem tag={Link} to="/entity/place">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Place
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/weather">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Weather
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/weather-desc">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Weather Desc
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/visited">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Visited
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);

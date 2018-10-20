import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './place.reducer';
import { IPlace } from 'app/shared/model/place.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPlaceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IPlaceUpdateState {
  isNew: boolean;
}

export class PlaceUpdate extends React.Component<IPlaceUpdateProps, IPlaceUpdateState> {
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

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { placeEntity } = this.props;
      const entity = {
        ...placeEntity,
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
    this.props.history.push('/entity/place');
  };

  render() {
    const { placeEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    const { json } = placeEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="trustMeApp.place.home.createOrEditLabel">Create or edit a Place</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : placeEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="place-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="titleLabel" for="title">
                    Title
                  </Label>
                  <AvField id="place-title" type="text" name="title" />
                </AvGroup>
                <AvGroup>
                  <Label id="categoryLabel" for="category">
                    Category
                  </Label>
                  <AvField id="place-category" type="text" name="category" />
                </AvGroup>
                <AvGroup>
                  <Label id="typeLabel">Type</Label>
                  <AvInput
                    id="place-type"
                    type="select"
                    className="form-control"
                    name="type"
                    value={(!isNew && placeEntity.type) || 'GMAP'}
                  >
                    <option value="GMAP">GMAP</option>
                    <option value="EVENT">EVENT</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="latLabel" for="lat">
                    Lat
                  </Label>
                  <AvField id="place-lat" type="string" className="form-control" name="lat" />
                </AvGroup>
                <AvGroup>
                  <Label id="lonLabel" for="lon">
                    Lon
                  </Label>
                  <AvField id="place-lon" type="string" className="form-control" name="lon" />
                </AvGroup>
                <AvGroup>
                  <Label id="jsonLabel" for="json">
                    Json
                  </Label>
                  <AvInput id="place-json" type="textarea" name="json" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/place" replace color="info">
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
  placeEntity: storeState.place.entity,
  loading: storeState.place.loading,
  updating: storeState.place.updating,
  updateSuccess: storeState.place.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PlaceUpdate);

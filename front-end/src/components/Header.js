import React, { Component } from 'react';
import '../styles/Header.css';
import CreateSurveyModal from 'react-modal';
import {customStyles} from "./util/modalStyles";
import * as API from '../api/API';
import { Route, withRouter } from 'react-router-dom';

class Header extends Component {
    constructor() {
        super();
        this.state = {
            createSurveyIsOpen: false,
            isClosed: true,
            surveytype: 'general'
        };

        this.openCreateSurveyModal = this.openCreateSurveyModal.bind(this);
        this.closeCreateSurveyModal = this.closeCreateSurveyModal.bind(this);
    };

    openCreateSurveyModal() {
        this.setState({
            createSurveyIsOpen: true
        });
    }

    closeCreateSurveyModal() {
        this.setState({createSurveyIsOpen: false});
    }

    handleLogout = (event) => {

        event.preventDefault();
        API.doLogout()
            .then((res) => {
                console.log("Logout: ", res);
                this.props.history.push("/");
            });

    };




    render() {
        return (
            <div className="header-bar">
                <button className="btn btn-primary survey-button"
                        onClick={this.props.handleGetSurveyListing}
                >Surveys</button>
                <button className="btn btn-primary survey-button"
                        onClick={this.openCreateSurveyModal}
                >Create</button>
                <button className="btn btn-primary share-button" onClick={this.handleLogout}>Sign Out</button>
                <CreateSurveyModal
                    isOpen={this.state.createSurveyIsOpen}
                    onAfterOpen={this.openCreateSurveyModal}
                    onRequestClose={this.closeCreateSurveyModal}
                    style={customStyles}
                >
                    <div className="modal-body">
                        <form className="form-horizontal">
                            <input type="text" className="form-control" name="survey" placeholder = "Enter Survey Name"
                                   onChange={(event) => {
                                       this.setState({
                                           surveyname : event.target.value
                                       });
                                   }}/><br/>
                            <label>Survey type:</label>
                            <select className="form-control" id="sel1"
                                    onChange={(event) => {
                                        if(event.target.value == 'General Survey')
                                        {
                                            this.setState({
                                                surveytype: 'general',
                                                isClosed: true
                                            })
                                        }
                                        else if(event.target.value == 'Closed invitation-only survey')
                                        {
                                            this.setState({
                                                surveytype: 'closed',
                                                isClosed: false
                                            })
                                        }
                                        else if(event.target.value == 'Open unique survey')
                                        {
                                            this.setState({
                                                surveytype: 'unique',
                                                isClosed: true
                                            })
                                        }
                                    }}>
                                <option>General Survey</option>
                                <option>Closed invitation-only survey</option>
                                <option>Open unique survey</option>
                            </select><br/>
                            <input type="text" className="form-control" name="survey" placeholder = "Invitees Emails (comma-separated)"
                                   disabled={this.state.isClosed}
                                   onChange={(event) => {
                                       this.setState({
                                           invitees : event.target.value
                                       });
                                   }}/><br/>
                            <label>Expires on:</label>
                            <input type="date" className="form-control"
                                   onChange={(event) => {
                                       this.setState({
                                           validity: event.target.value
                                       })
                                   }}>
                            </input><br/>
                            <button type="button" id="createSurvey" className="button-register"
                                    onClick={() => this.props.handleAddSurvey(this.state)}
                            >Create Survey</button>
                        </form>
                    </div>
                </CreateSurveyModal>
            </div>
        );
    }
}

export default withRouter(Header);

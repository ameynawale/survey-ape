import React, { Component } from 'react';
import '../styles/Header.css';
import CreateSurveyModal from 'react-modal';
import {customStyles} from "./util/modalStyles";

class Header extends Component {
    constructor() {
        super();
        this.state = {
            createSurveyIsOpen: false,
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


    render() {
        return (
            <div className="header-bar">
                <button className="btn btn-primary survey-button"
                        onClick={this.props.handleGetSurveyListing}
                >Surveys</button>
                <button className="btn btn-primary survey-button"
                        onClick={this.openCreateSurveyModal}
                >Create</button>
                <button className="btn btn-primary share-button">Sign Out</button>
                <button className="btn btn-primary share-button">Share</button>
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
                                        this.setState({
                                            surveytype: event.target.value
                                        })
                                    }}>
                                <option>General Survey</option>
                                <option>Closed invitation-only survey</option>
                                <option>Open unique survey</option>
                            </select><br/>
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

export default Header;
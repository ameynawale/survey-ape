import React, { Component } from 'react';
import '../styles/SurveyContainer.css';
import { Route, withRouter } from 'react-router-dom';

import FormContainer from "./FormContainer";
// import {Button, Modal} from 'react-bootstrap';
import { TabContent, TabPane, Nav, NavItem, NavLink, Card,Modal, Button, CardTitle, CardText, Row, Col } from 'reactstrap';
import classnames from 'classnames';
import * as API from "../api/API";
const customStyles = {
    content : {
        top                   : '80%',
        left                  : '50%',
        right                 : 'auto',
        width                 : '50%',
        bottom                : 'auto',
        marginRight           : '-50%',
        transform             : 'translate(-50%, -50%)',
        opacity: 0.5
    }
};
class UniqueSurveyContainer extends Component {

    constructor(props){
        super(props);

        this.state = {
            activeTab: '1',
            unique:[],
            createdbyme:[
                {
                    surveyId: 1,
                    surveyName: 'Survey 1'
                },
                {
                    surveyId: 2,
                    surveyName: 'Survey 2'
                }
            ],
            sharedwithme:[
                {
                    surveyId: 3,
                    surveyName: 'Survey 3'
                },
                {
                    surveyId: 4,
                    surveyName: 'Survey 4'
                }
            ],
            user: this.props.surveydata.user
        };
    }

    componentWillMount()
    {
        console.log("will mount ", this.state.user.email);
        API.uniqueSurveyListing()
            .then((res) => {
                this.setState({
                    unique:res.data.unique
                })
                console.log("unique "+res.data.unique);
            })

    }

    toggle(tab) {
        if (this.state.activeTab !== tab) {
            this.setState({
                activeTab: tab
            });
        }
    }
    openStats = (survey) =>{
        if(localStorage.getItem("userEmail") !== null && localStorage.getItem("userEmail") !== ""
            && localStorage.getItem("userEmail") !== undefined){
            var payload= {
                "email":localStorage.getItem("userEmail"),
                "surveyid":survey.surveyid,
                "type": survey.surveytype
            }
            this.props.history.push('./SurveyQuestions', {surveyData: payload});
        } else{
            this.props.history.push('./UniqueEmailInput', { selectedSurvey: survey});
        }
    }

    render() {
        return (
            <div className="survey-container">
                <div className="form-design-container">
                    <div className="form-container">

                        <h1> Unique Surveys</h1>
                        <br/>
                            <div>
                                {this.state.unique.map((survey) =>
                                    <Row>
                                        <Col sm="12">
                                            <span>{survey.surveyname}</span>
                                            <button className="btn btn-primary share-button ml-2"
                                                    onClick={() => {
                                                        this.openStats(survey);
                                                    }}>Register</button>
                                            <br/><br/>
                                        </Col>
                                    </Row>)

                                }
                            </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default withRouter(UniqueSurveyContainer);
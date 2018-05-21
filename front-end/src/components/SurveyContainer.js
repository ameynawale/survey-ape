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
class SurveyContainer extends Component {

    constructor(props){
        super(props);

        this.state = {
            activeTab: '1',
            createdbyme:[],
            sharedwithme:[],
            uniqueSurvey:[],
            user: this.props.surveydata.user
        };
        // this.openModal = this.openModal.bind(this);
        // this.closeModal = this.closeModal.bind(this);
    }

    componentWillMount()
    {
        console.log("will mount ", this.state.user.email);
        API.surveyListing(this.state.user)
            .then((res) => {
                this.setState({
                    createdbyme:res.data.createdbyme,
                    sharedwithme:res.data.sharedwithme,
                    uniqueSurvey: res.data.uniqueSurvey
                })
            })
    }

    closeSurvey = (event) => {
        event.preventDefault();
        console.log("inside function ", event.target.name);
        var Survey = {
            surveyid: event.target.name
        }
        API.closeSurvey(Survey)
            .then((res) => {
                if(res.status === 200)
                {
                    console.log('inside 200');
                    alert("Survey is closed successfully");
                }
            });

    }

    editSurvey = (event) => {
        console.log(event.target.name);
        var surveydata = {
            surveyid: event.target.name
        }
        this.props.editSurvey(surveydata);
        /*
        event.preventDefault();
        console.log("inside function ", event.target.name);
        var Survey = {
            surveyid: event.target.name
        }
        API.closeSurvey(Survey)
            .then((res) => {
                if(res.status === 200)
                {
                    console.log('inside 200');
                    alert("Survey is closed successfully");
                }
            });
*/
    }

    unPublishSurvey = (event) => {
        // event.preventDefault();
        var surveydata = {
            surveyid: event.target.name
        }
        console.log("inside unpublish");
        API.unpublishSurvey(surveydata)
            .then(
                response => {
                    if(response.status === 200)
                    {
                        console.log('inside 200');
                        alert("Survey is unpublished successfully");
                        API.surveyListing(this.state.user)
                            .then((res) => {
                                this.setState({
                                    createdbyme:res.data.createdbyme,
                                    sharedwithme:res.data.sharedwithme
                                })
                            })

                    }
                    if(response.response.status === 400)
                    {
                        alert("Survey cannot be unpubished because at least one person has taken the survey");
                    }

                }
            );
            /*.then((res) => {
                if(res.status === 200)
                {
                    console.log('inside 200');
                    alert("Survey is unpublished successfully");
                    API.surveyListing(this.state.user)
                        .then((res) => {
                            this.setState({
                                createdbyme:res.data.createdbyme,
                                sharedwithme:res.data.sharedwithme
                            })
                        })

                }
                if(res.status === 400)
                {
                    alert("Survey cannot be unpubished because at least one person has taken the survey");
                }
            })
            .catch((res) => {
                alert("Survey cannot be unpubished because at least one person has taken the survey");
            })*/
    }

    toggle(tab) {
        if (this.state.activeTab !== tab) {
            this.setState({
                activeTab: tab
            });
        }
    }
    openStats = (survey) =>{
        this.props.history.push("/surveyStats", { selectedSurvey: survey })
           // this.openModal();
    }

    // openModal(){
    //     this.setState({
    //         show: true });
    // }
    // closeModal(){
    //     this.setState({
    //         show: false });
    // }

    openClosedSurvey(survey){
        var sur={
            "type":"close",
            "surveyid": survey.surveyid,
            "email":localStorage.getItem("userEmail")
        }
        this.props.history.push('./SurveyQuestions', {surveyData: sur});
    }

    openUniqueSurvey(survey){
        var sur={
            "type":"unique",
            "surveyid": survey.surveyid,
            "email":localStorage.getItem("userEmail")
        }
        this.props.history.push('./SurveyQuestions', {surveyData: sur});
    }
    render() {
        return (
            <div className="survey-container">
                <div className="form-design-container">
                    <div className="form-container">


                        <Nav tabs>
                            <NavItem className='col-lg-4'>
                                <NavLink
                                    className={classnames({ active: this.state.activeTab === '1'})}
                                    onClick={() => { this.toggle('1'); }}
                                >
                                    Created by Me
                                </NavLink>
                            </NavItem>
                            <NavItem className='col-lg-4'>
                                <NavLink
                                    className={classnames({ active: this.state.activeTab === '2' })}
                                    onClick={() => { this.toggle('2'); }}
                                >
                                    Shared with Me
                                </NavLink>
                            </NavItem>
                            <NavItem className='col-lg-4'>
                                <NavLink
                                    className={classnames({ active: this.state.activeTab === '3' })}
                                    onClick={() => { this.toggle('3'); }}
                                >
                                    All Unique surveys
                                </NavLink>
                            </NavItem>
                        </Nav>
                        <TabContent activeTab={this.state.activeTab}>
                            <TabPane tabId="1">
                                <br/>
                                {this.state.createdbyme.map((survey) =>
                                    <Row>
                                        <Col sm="12">
                                        <span>{survey.surveyname}</span>

                                            {survey.ispublished ? (
                                                <button className="btn btn-basic share-button ml-2"
                                                        name={survey.surveyid}
                                                        onClick={(event) => {this.unPublishSurvey(event)}}
                                                >Unpublish</button>
                                            ) : (
                                                <button className="btn btn-primary share-button ml-2"
                                                        name={survey.surveyid}
                                                        onClick={(event) => {this.editSurvey(event)}}
                                                >Edit</button>
                                            )}
                                        <button className="btn btn-danger share-button ml-2"
                                                name={survey.surveyid}
                                                onClick={(event) => {this.closeSurvey(event)}}
                                        >Close</button>
                                        <button className="btn btn-success share-button ml-2"
                                                onClick={() => {
                                                    this.openStats(survey);
                                                }}>View Stats</button>
                                            <br/><br/>
                                        </Col>
                                </Row>)
                                }
                            </TabPane>
                            <TabPane tabId="2">
                                <br/>
                                {this.state.sharedwithme.map((survey) =>
                                    <Row>
                                        <Col sm="12">
                                           <a href="javascript:void(0)" onClick={() => {
                                               this.openClosedSurvey(survey);
                                           }}><h6>{survey.surveyname}</h6></a>
                                            <br/>
                                        </Col>
                                    </Row>)
                                }
                            </TabPane>
                            <TabPane tabId="3">
                                <br/>
                                {this.state.uniqueSurvey.map((survey) =>
                                    <Row>
                                        <Col sm="12">
                                            <a href="javascript:void(0)" onClick={() => {
                                                this.openUniqueSurvey(survey);
                                            }}><h6>{survey.surveyname}</h6></a>
                                            <br/>
                                        </Col>
                                    </Row>)
                                }
                            </TabPane>
                        </TabContent>
                    </div>
                </div>
            </div>
        );
    }
}

export default withRouter(SurveyContainer);
import React, { Component } from 'react';
import '../styles/SurveyContainer.css';
import FormContainer from "./FormContainer";
import { TabContent, TabPane, Nav, NavItem, NavLink, Card, Button, CardTitle, CardText, Row, Col } from 'reactstrap';
import classnames from 'classnames';

class SurveyContainer extends Component {

    constructor(props){
        super(props);

        this.state = {
            activeTab: '1',
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
            ]
        };
    }

    toggle(tab) {
        if (this.state.activeTab !== tab) {
            this.setState({
                activeTab: tab
            });
        }
    }

    render() {
        return (
            <div className="survey-container">
                <div className="form-design-container">
                    <div className="form-container">
                        <Nav tabs>
                            <NavItem className='col-lg-6'>
                                <NavLink
                                    className={classnames({ active: this.state.activeTab === '1'})}
                                    onClick={() => { this.toggle('1'); }}
                                >
                                    Created by Me
                                </NavLink>
                            </NavItem>
                            <NavItem className='col-lg-6'>
                                <NavLink
                                    className={classnames({ active: this.state.activeTab === '2' })}
                                    onClick={() => { this.toggle('2'); }}
                                >
                                    Shared with Me
                                </NavLink>
                            </NavItem>
                        </Nav>
                        <TabContent activeTab={this.state.activeTab}>
                            <TabPane tabId="1">
                                <br/>
                                {this.state.createdbyme.map((survey) =>
                                    <Row>
                                    <Col sm="12">
                                        <h6>{survey.surveyName}</h6>
                                    </Col>
                                </Row>)
                                }
                            </TabPane>
                            <TabPane tabId="2">
                                <br/>
                                {this.state.sharedwithme.map((survey) =>
                                    <Row>
                                        <Col sm="12">
                                            <h6>{survey.surveyName}</h6>
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

export default SurveyContainer;
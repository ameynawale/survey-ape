import React, { Component } from 'react';
import Header from "./Header";
import SurveyContainer from "./SurveyContainer";
import '../styles/SurveyContainer.css';
import { TabContent, TabPane, Nav, NavItem, NavLink, Card, Button, CardTitle, CardText, Row, Col } from 'reactstrap';

class SurveyListing extends Component {
    render() {
        return (
            <div>
                <SurveyContainer surveydata={this.props.surveydata} editSurvey={this.props.editSurvey}/>
            </div>
        );
    }
}

export default SurveyListing;
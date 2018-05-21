import React, { Component } from 'react';
import Header from "./Header";
import UniqueSurveyContainer from "./UniqueSurveyContainer";
import '../styles/SurveyContainer.css';
import { TabContent, TabPane, Nav, NavItem, NavLink, Card, Button, CardTitle, CardText, Row, Col } from 'reactstrap';

class UniqueSurveyListing extends Component {
    render() {
        return (
            <div>
                <UniqueSurveyContainer surveydata={this.props.surveydata}/>
            </div>
        );
    }
}

export default UniqueSurveyListing;
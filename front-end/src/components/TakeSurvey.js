import React, { Component } from 'react';
import '../styles/SurveyContainer.css';
import queryString          from 'query-string';
import { TabContent, TabPane, Nav, NavItem, NavLink, Card,Modal, Button, CardTitle, CardText, Row, Col } from 'reactstrap';
class TakeSurvey extends Component{
    constructor(props){
        super(props);
        // let x = this.props.selectedSurvey;
        this.state={
            "type":'',
            "surveyid": ''
        }
    }

    componentWillMount(){
        let url = this.props.urlData;
        let parsed = queryString.parse(this.props.urlData.location.search);
        // let parsed = queryString.parse(url.search);
        this.setState({
            ...this.state,
            "type":this.props.type,
            "surveyid": parsed.surveyid
        });
        console.log(this.state.type+ this.state.surveyid);
        // API.
    }

    render(){
        if(this.state.type)
        return (
            <div>

            </div>
        )
    }
}
export default TakeSurvey;
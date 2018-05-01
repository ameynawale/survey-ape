import React, { Component } from 'react';
import Header from "./Header";
import SurveyContainer from "./SurveyContainer";
import '../styles/SurveyContainer.css';

class samplesurveypage extends Component {
    render() {
        return (
            <div>
                <SurveyContainer surveydata={this.props.surveydata}/>
            </div>
        );
    }
}

export default samplesurveypage;
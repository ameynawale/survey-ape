import React, { Component } from 'react';
import Header from "./Header";
import SurveyContainer from "./SurveyContainer";
import FormContainer from './FormContainer';
import '../styles/SurveyContainer.css';

class Samplesurveypage extends Component {
    render() {
        return (
            <div>
                <div className="survey-container">
                    <div className="form-design-container">
                        <FormContainer surveydata={this.props.surveydata}/>
                    </div>
                </div>
            </div>
        );
    }
}

export default Samplesurveypage;
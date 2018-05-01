import React, { Component } from 'react';
import '../styles/SurveyContainer.css';
import FormContainer from "./FormContainer";

class SurveyContainer extends Component {
    render() {
        return (
            <div className="survey-container">
                <div className="form-design-container">
                    <FormContainer surveydata={this.props.surveydata}/>
                </div>
            </div>
        );
    }
}

export default SurveyContainer;
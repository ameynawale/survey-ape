import React, { Component } from 'react';
import '../styles/SurveyContainer.css';
import FormContainer from "./FormContainer";

class SurveyContainer extends Component {
    render() {
        return (
            <div className="survey-container">
                <FormContainer/>
            </div>
        );
    }
}

export default SurveyContainer;
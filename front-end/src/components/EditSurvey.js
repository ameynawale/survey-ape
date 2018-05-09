import React, { Component } from 'react';
import Header from "./Header";
import SurveyContainer from "./SurveyContainer";
import FormContainerEdit from './FormContainerEdit';
import '../styles/SurveyContainer.css';

class EditSurvey extends Component {
    render() {
        return (
            <div>
                <div className="survey-container">
                    <div className="form-design-container">
                        <FormContainerEdit surveydata={this.props.surveydata}/>
                    </div>
                </div>
            </div>
        );
    }
}

export default EditSurvey;
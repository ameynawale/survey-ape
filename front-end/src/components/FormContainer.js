import React, { Component } from 'react';

import FormContent from './FormContent';
import '../styles/FormContainer.css';

class FormContainer extends Component {
    render() {
        return (
            <div className="form-container">
                <FormContent surveydata={this.props.surveydata}/>
            </div>
        );
    }
}

export default FormContainer;
import React, { Component } from 'react';

import FormContentEdit from './FormContentEdit';
import '../styles/FormContainer.css';

class FormContainerEdit extends Component {
    render() {
        return (
            <div className="form-container">
                <FormContentEdit surveydata={this.props.surveydata}/>
            </div>
        );
    }
}

export default FormContainerEdit;
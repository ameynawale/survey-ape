import React, { Component } from 'react';

import FormContent from './FormContent';
import '../styles/FormContainer.css';

class FormContainer extends Component {
    render() {
        return (
            <div className="form-container">
                <FormContent/>
            </div>
        );
    }
}

export default FormContainer;
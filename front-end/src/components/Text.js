import React, { Component } from 'react';
import PropTypes from 'prop-types';

import '../styles/FormContent.css';
import '../styles/Header.css';
import '../styles/SingleSelect.css';
import * as API from "../api/API";


class Text extends Component {
    constructor(props) {
        super(props);
        this.state = {
            options: [],
            question: "",
            optionsarray: [],
            questionid: this.props.surveydata.questionid,
            surveyid: this.props.surveydata.surveyid,
            questiontype: 'text'
        };

        this.handleInputChange = this.handleInputChange.bind(this);
    }

    componentWillMount() {
        console.log("index in child "+this.props.index);
    }

    static propTypes = {
        handleSubmit: PropTypes.func.isRequired
    }

    handleInputChange() {
        API.addQuestion(this.state);
    }

    render() {
        return (
            <div className="question-container">
                <div className="col-lg-12">
                    <input type="text" className="form-control question-input"
                           onChange={(event) => {
                               this.setState({
                                   question: event.target.value
                               });
                               this.handleInputChange();
                           }}
                    /><br/>
                </div>
            </div>
        );
    }
}

export default Text;
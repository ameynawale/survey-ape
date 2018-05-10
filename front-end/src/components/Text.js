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
            question: this.props.surveydata.question,
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

    handleInputChange(event) {
        let question = {
            question: event.target.value,
            questionid: this.state.questionid,
            surveyid: this.state.surveyid,
            questiontype: this.state.questiontype
        }
        API.addQuestion(question);
    }

    render() {
        return (
            <div className="question-container">
                <div className="col-lg-12">
                    <input type="text" className="form-control question-input"
                           defaultValue={this.state.question}
                           onBlurCapture={(event) => {
                               this.setState({
                                   question: event.target.value
                               });
                               this.handleInputChange(event);
                           }}
                    /><br/>
                </div>
            </div>
        );
    }
}

export default Text;
import React, { Component } from 'react';
import PropTypes from 'prop-types';

import '../styles/FormContent.css';
import '../styles/Header.css';
import '../styles/SingleSelect.css';
import * as API from "../api/API";


class Rating extends Component {
    constructor(props) {
        super(props);
        this.state = {
            options: [],
            question: this.props.surveydata.question,
            optionsarray: [],
            rating: {},
            questionid: this.props.surveydata.questionid,
            surveyid: this.props.surveydata.surveyid,
            questiontype: 'rating',
            scale: ''
        };

        this.handleInputChange = this.handleInputChange.bind(this);
    }

    componentWillMount() {
        console.log("index in child "+this.props.index);
        let option = {
            options: 5,
            questionid: this.state.questionid
        }
        API.addOption(option)
            .then((res) => {
                this.setState({
                    optionid: res.data.optionid,
                    rating: <div className="form-inline">
                        <label>Scale:</label>
                        <select className="form-control" id="sel1"
                                name={res.data.optionid}
                                onChange={(event) => {
                                    this.setState({
                                        scale: event.target.value
                                    });
                                    this.handleOptionInputChange();
                                }}>
                            <option>5</option>
                            {/*<option>10</option>*/}
                        </select><br/>
                    </div>
                })
            })
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

    handleOptionInputChange(event) {
        let option = {
            optionid: this.state.optionid,
            options: event.target.value,
            questionid: this.state.questionid
        }
        console.log(this.state.scale);
        API.addOption(option);
    }

    render() {
        return (
            <div className="question-container">
                <div className="col-lg-12">
                    <input type="text" className="form-control question-input"
                           defaultValue={this.state.question}
                           onChange={(event) => {
                               this.setState({
                                   question: event.target.value
                               });
                               this.handleInputChange(event);
                           }}
                    /><br/><br/>
                </div>
                <div className="form-inline">
                    <label>Scale:</label>
                    <select className="form-control" id="sel1"
                            // name={this.state.optionid}
                            onChange={(event) => {
                                this.handleOptionInputChange(event);
                            }}>
                        <option>5</option>
                        {/*<option>10</option>*/}
                    </select><br/>
                </div>
                {/*<button className="btn btn-link add-option" onClick={this.addOption}>Add Option</button>*/}
            </div>
        );
    }
}

export default Rating;
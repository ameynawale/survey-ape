import React, { Component } from 'react';
import PropTypes from 'prop-types';

import '../styles/FormContent.css';
import '../styles/Header.css';
import '../styles/SingleSelect.css';
import * as API from "../api/API";


class SingleSelect extends Component {
    constructor(props) {
        super(props);
        this.state = {
            options: [],
            question: this.props.surveydata.question,
            optionsarray: [],
            questionid: this.props.surveydata.questionid,
            surveyid: this.props.surveydata.surveyid,
            questiontype: 'radio'
        };

        this.handleInputChange = this.handleInputChange.bind(this);
    }

    componentWillMount() {
        console.log("index in child "+this.props.index);
        API.getOptions(this.state)
            .then((res) => {
                // console.log("options", res.data.options[0].options);
                this.setState({
                    optionsarray: res.data.options
                })
                res.data.options.map((option) => {
                    var optionstemp = this.state.options;
                    optionstemp.push(
                        <div className="form-group">
                            <label>
                                <input type="checkbox" disabled/>
                                <input type="text" className="form-control ml-2"
                                       defaultValue={option.options}
                                       onBlurCapture={(event) => {
                                           this.handleOptionInputChange(event)}}
                                    // placeholder="Option"
                                       name={option.optionid}
                                />
                                <span className="fa fa-trash ml-2" onClick={this.removeOption} ></span>
                            </label>
                            <br/><br/>
                        </div>
                    )
                    this.setState({
                        options: optionstemp
                    })
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
            optionid: event.target.name,
            options: event.target.value,
            questionid: this.state.questionid
        }
        API.addOption(option);
    }

    addOption = (event) => {
        event.preventDefault();
        let option = {
            questionid: this.state.questionid
        }

        API.addOption(option)
            .then((res) => {
                if(res.status === 201)
                {
                    this.state.optionsarray.push("");
                    let options = this.state.options;
                    options.push(
                        <div className="form-group">
                            <label>
                                <input type="radio" disabled/>
                                <input type="text" className="form-control ml-2"
                                       onBlurCapture={(event) => {
                                           this.handleOptionInputChange(event)}}
                                       placeholder="Option"
                                       name={res.data.optionid}
                                />
                                <span className="fa fa-trash ml-2" onClick={this.removeOption} ></span>
                            </label>
                            <br/><br/>
                        </div>
                    );
                    this.setState({
                        options: options
                    })
                }
            })
    }

    removeOption = (event) => {
        event.preventDefault();

        this.state.optionsarray.splice(event.currentTarget.dataset.id, 1);
        let optionsarray = this.state.optionsarray;
        // optionsarray.r
        let options = optionsarray.map((option, index) =>
            // console.log("index "+index)
            <div className="form-group">
                <label>
                    <input type="radio" disabled/>
                    <input type="text" className="form-control ml-2"
                           defaultValue={option}
                           onBlurCapture={(event) => {
                               let value = event.target.value;
                               let optionsarray = this.state.optionsarray;
                               optionsarray[index] = value;
                               // this.state.optionsarray[index] = event.target.value;
                               this.setState({
                                   optionsarray: optionsarray
                               });
                           }}
                    />
                    <span className="fa fa-trash ml-2" onClick={this.removeOption} data-id={index}></span>
                </label>
                <br/><br/>
            </div>
        );
        this.setState({
            options: options
        });
        console.log(this.state.optionsarray);

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
                <div className="form-inline">
                    <p>{this.state.options}</p>
                </div>
                <button className="btn btn-link add-option" onClick={this.addOption}>Add Option</button>
            </div>
        );
    }
}

export default SingleSelect;
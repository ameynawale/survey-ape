import React, { Component } from 'react';
import PropTypes from 'prop-types';

import '../styles/FormContent.css';
import '../styles/Header.css';
import '../styles/SingleSelect.css';
import * as API from "../api/API";


class Dropdown extends Component {
    constructor(props) {
        super(props);
        this.state = {
            options: [],
            question: this.props.surveydata.question,
            optionsarray: [],
            questionid: this.props.surveydata.questionid,
            surveyid: this.props.surveydata.surveyid,
            questiontype: 'dropdown'
        };

        this.handleInputChange = this.handleInputChange.bind(this);
    }

    componentWillMount() {
        console.log("index in child "+this.state.question);
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
                                <input type="checkbox" disabled/>
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

/*

    addOption = (event) => {
        event.preventDefault();
        console.log(this.state.optionsarray);
        this.state.optionsarray.push("");
        let optionsarray = this.state.optionsarray;
        let options = optionsarray.map((option, index) =>
            // console.log("index "+index)
            <div className="form-group">
                <label>
                    <input type="text" className="form-control ml-3"
                        // onBlurCapture={this.handleInputChange}
                        // value={option}
                           placeholder="Option"
                        // value={this.state.optionsarray[index]}
                           onChange={(event) => {
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


        /*var options = this.state.options;
        let index = options.length;
        options.push(
            <div className="form-group">
                <label>
                    <input type="radio" disabled/>
                    <input type="text" className="form-control ml-2" name={index} onBlurCapture={this.handleInputChange}/>
                    <span className="fa fa-trash ml-2" name={index} onClick={this.removeOption} data-id={index}></span>
                </label>
            </div>
        );*/
        // options.push(<br/>);
  /*      this.setState({
            options: options
        });

        this.props.handleSubmit(this.state);

    }
*/
    removeOption = (event) => {
        console.log("dataid "+event.currentTarget.dataset.id);
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
                        // onBlurCapture={this.handleInputChange}
                           defaultValue={option}
                        // placeholder="Option"
                        // value={this.state.optionsarray[index]}
                           onChange={(event) => {
                               let value = event.target.value;
                               let optionsarray = this.state.optionsarray;
                               optionsarray[index] = value;
                               // this.state.optionsarray[index] = event.target.value;
                               this.setState({
                                   optionsarray: optionsarray
                               });
                           }}
                    />
                    <span className="fa fa-trash ml-2" onClick={(e) => this.removeOption(e)} data-id={index}></span>
                </label>
                <br/><br/>
            </div>
        );
        this.setState({
            options: options
        });
        console.log(this.state.optionsarray);

        /*console.log(this.state.options);
        var options = this.state.options;
        var index = event.currentTarget.dataset.id/2;
        console.log("index "+index);
        console.log("name "+event.target.name);
        options.splice(index, 2);
        // this.state.options.splice(index, 2);
        var optionsarray = this.state.optionsarray;
        optionsarray.splice(index, 1);
        // var index = options.indexOf(event.target);
        // delete options[index];
        this.setState({
            options: options,
            optionsarray: optionsarray
        });*/
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

export default Dropdown;
import React, { Component } from 'react';
import PropTypes from 'prop-types';

import '../styles/FormContent.css';
import '../styles/Header.css';
import '../styles/SingleSelect.css';
import * as API from "../api/API";


class Date extends Component {
    constructor(props) {
        super(props);
        this.state = {
            options: [],
            question: this.props.surveydata.question,
            optionsarray: [],
            index: this.props.index,
            questionid: this.props.surveydata.questionid,
            surveyid: this.props.surveydata.surveyid,
            questiontype: 'date'
        };

        this.handleInputChange = this.handleInputChange.bind(this);
    }

    componentWillMount() {
        console.log("index in child "+this.props.index);
    }

    static propTypes = {
        handleSubmit: PropTypes.func.isRequired
    }

    /*handleInputChange(event) {
        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const name = target.name;
        var index = name/2;
        console.log("index"+index);
        var optionsarray = this.state.optionsarray;
        optionsarray[index] = value;
        // optionsarray.push(value);
        this.setState({
            optionsarray: optionsarray
        });
        console.log(this.state.optionsarray);
    }*/

    handleInputChange(event) {
        let question = {
            question: event.target.value,
            questionid: this.state.questionid,
            surveyid: this.state.surveyid,
            questiontype: this.state.questiontype
        }
        API.addQuestion(question);
    }

    /*addOption = (event) => {
        event.preventDefault();
        console.log(this.state.optionsarray);
        this.state.optionsarray.push("");
        let optionsarray = this.state.optionsarray;
        let options = optionsarray.map((option, index) =>
            // console.log("index "+index)
            <div className="form-group">
                <label>
                    <input type="checkbox" disabled/>
                    <input type="text" className="form-control ml-2"
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

        /!*var options = this.state.options;
        let index = options.length;
        options.push(
            <div className="form-group">
                <label>
                    <input type="radio" disabled/>
                    <input type="text" className="form-control ml-2" name={index} onBlurCapture={this.handleInputChange}/>
                    <span className="fa fa-trash ml-2" name={index} onClick={this.removeOption} data-id={index}></span>
                </label>
            </div>
        );*!/
        // options.push(<br/>);
        this.setState({
            options: options
        });

        this.props.handleSubmit(this.state);

    }

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

*/

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
                <div className="form-inline ml-3">
                    <label>Datepicker as seen by the surveyee :</label>
                    <input type="date" className="form-control"
                           onChange={(event) => {
                               this.setState({
                                   duedate: event.target.value
                               })
                           }}>
                    </input><br/>
                </div>
                {/*<button className="btn btn-link add-option" onClick={this.addOption}>Add Option</button>*/}
            </div>
        );
    }
}

export default Date;
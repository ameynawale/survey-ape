import React, { Component } from 'react';

import '../styles/FormContent.css';
import '../styles/Header.css';
import '../styles/SingleSelect.css';


class SingleSelect extends Component {
    constructor(props) {
        super(props);
        this.state = {
            options: [],
            question: "",
            optionsarray: []
        };

        this.handleInputChange = this.handleInputChange.bind(this);
    }

    handleInputChange(event) {
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
    }

    addOption = (event) => {
        event.preventDefault();
        // console.log(this.state.options);
        var options = this.state.options;
        let index = options.length;
        options.push(
            <div className="form-group">
                <label>
                    <input type="radio" disabled/>
                    <input type="text" className="form-control ml-2" name={index} onBlurCapture={this.handleInputChange}/>
                    <span className="fa fa-trash ml-2" name={index} onClick={this.removeOption} data-id={index}></span>
                </label>
            </div>
        );
        options.push(<br/>);
        this.setState({
            options: options
        });
    }

    removeOption = (event) => {
        console.log("dataid "+event.currentTarget.dataset.id);
        event.preventDefault();
        console.log(this.state.options);
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
        });
    }

    render() {
        return (
            <div className="question-container">
                <input type="text" className="form-control" onChange={this.handleInputChange}/><br/>
                <div className="form-inline">
                    <p>{this.state.options}</p>
                </div>
                <button className="btn btn-link add-option" onClick={this.addOption}>Add Option</button>
            </div>
        );
    }
}

export default SingleSelect;
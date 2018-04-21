import React, { Component } from 'react';

import '../styles/FormContent.css';
import '../styles/Header.css';
import '../styles/SingleSelect.css';


class SingleSelect extends Component {
    constructor(props) {
        super(props);
        this.state = {
            options: []
        };

        this.handleInputChange = this.handleInputChange.bind(this);
    }

    handleInputChange(event) {
        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const name = target.name;

        this.setState({
            [name]: value
        });
    }

    addOption = (event) => {
        event.preventDefault();
        // console.log(this.state.options);
        var options = this.state.options;
        options.push(
            <div className="form-group">
                <label>
                    <input type="radio" disabled/>
                    <input type="text" className="form-control ml-2" onChange={this.handleInputChange}/>
                    <span className="fa fa-trash ml-2" onClick={this.removeOption}></span>
                </label>
            </div>
        );
        options.push(<br/>);
        this.setState({
            options: options
        });
    }

    removeOption = (event) => {
        event.preventDefault();
        // console.log(this.state.options);
        var options = this.state.options;
        var index = options.indexOf(event.target);
        delete options[index];
        this.setState({
            options: options
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
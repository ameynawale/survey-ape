import React, { Component } from 'react';
import SingleSelect from "./SingleSelect";

import '../styles/FormContent.css';


class FormContent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isGoing: true,
            numberOfGuests: 2,
            questions: []
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

    addQuestion = (event) => {
        event.preventDefault();
        var questions = this.state.questions;
        let index = questions.length;
        questions.push(
            <div className="form-inline">
                <SingleSelect/>
            </div>
        );
        questions.push(<br/>);
        this.setState({
            questions: questions
        });
    }

    render() {
        return (
            <form>
                <div className="form-design-container">
                    <div className="form-inline">
                        <p>{this.state.questions}</p>
                    </div>
                    <SingleSelect/>
                    <button className="btn btn-link add-option" onClick={this.addQuestion}>Add Option</button>
                </div>
            </form>
        );
    }
}

export default FormContent;
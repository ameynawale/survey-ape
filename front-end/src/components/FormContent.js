import React, { Component } from 'react';
import SingleSelect from "./SingleSelect";

import '../styles/FormContent.css';


class FormContent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isGoing: true,
            numberOfGuests: 2,
            questions: [],
            questionsarray: [],
            index: 0
        };
        // var index = 0;

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentWillMount(){
        console.log("indside parent will mount");
        this.in
        this.state.questions.push(<SingleSelect index={this.state.index++} handleSubmit={this.handleSubmit}/>);
        this.state.questionsarray.push({});
    }

    handleInputChange(event) {
        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const name = target.name;

        this.setState({
            [name]: value
        });
    }

    handleSubmit = (question) => {
        console.log("questionslength "+ this.state.questions.length);
        for(let i=0; i < this.state.questionsarray.length; i++)
        {
            console.log("index "+question.index);
            if(question.index == i)
            {
                this.state.questionsarray[i] = question;
                break;
            }
        }
        this.state.questions.forEach(question => console.log(question.question + " " + question.index + " " + question.optionsarray));
    }

    addQuestion = (event) => {
        event.preventDefault();
        var questions = this.state.questions;
        questions.push(
            <div className="form-inline">
                <SingleSelect index={this.state.index++} handleSubmit={this.handleSubmit}/>
            </div>
        );
        this.state.questionsarray.push({});
        // questions.push(<br/>);
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
                    <button className="btn btn-link add-option" onClick={this.addQuestion}>Add Option</button>
                </div>
            </form>
        );
    }
}

export default FormContent;
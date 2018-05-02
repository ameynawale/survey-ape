import React, { Component } from 'react';
import SingleSelect from "./Radio";
import Checkbox from './Checkbox';
import Dropdown from "./Dropdown";
import Text from "./Text";
import Rating from "./Rating";
import Date from "./Date";
import '../styles/FormContent.css';
import { Button, ButtonGroup } from 'reactstrap';


class FormContent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isGoing: true,
            numberOfGuests: 2,
            questions: [],
            questionsarray: [],
            index: 0,

            surveyName      :   this.props.surveydata.surveyName,
            surveyId        :   this.props.surveydata.surveyId
        };
        // var index = 0;

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentWillMount(){
        // console.log("indside parent will mount");
        // this.in
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
        this.setState({
            questions: questions
        });
    }
    addCheckbox = (event) => {
        event.preventDefault();
        var questions = this.state.questions;
        questions.push(
            <div className="form-inline">
                <Checkbox index={this.state.index++} handleSubmit={this.handleSubmit}/>
            </div>
        );
        this.state.questionsarray.push({});
        this.setState({
            questions: questions
        });
    }
    addDropdown = (event) => {
        event.preventDefault();
        var questions = this.state.questions;
        questions.push(
            <div className="form-inline">
                <Dropdown index={this.state.index++} handleSubmit={this.handleSubmit}/>
            </div>
        );
        this.state.questionsarray.push({});
        this.setState({
            questions: questions
        });
    }
    addText = (event) => {
        event.preventDefault();
        var questions = this.state.questions;
        questions.push(
            <div className="form-inline">
                <Text index={this.state.index++} handleSubmit={this.handleSubmit}/>
            </div>
        );
        this.state.questionsarray.push({});
        this.setState({
            questions: questions
        });
    }
    addRating = (event) => {
        event.preventDefault();
        var questions = this.state.questions;
        questions.push(
            <div className="form-inline">
                <Rating index={this.state.index++} handleSubmit={this.handleSubmit}/>
            </div>
        );
        this.state.questionsarray.push({});
        this.setState({
            questions: questions
        });
    }
    addDate = (event) => {
        event.preventDefault();
        var questions = this.state.questions;
        questions.push(
            <div className="form-inline">
                <Date index={this.state.index++} handleSubmit={this.handleSubmit}/>
            </div>
        );
        this.state.questionsarray.push({});
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
                    {/*<button className="btn btn-link add-option" onClick={this.addQuestion}>Add Option</button>*/}
                    <ButtonGroup>
                        <Button onClick={this.addQuestion}>Radio</Button>
                        <Button onClick={this.addDropdown}>Dropdown</Button>
                        <Button onClick={this.addCheckbox}>Checkbox</Button>
                        <Button onClick={this.addText}>Text</Button>
                        <Button onClick={this.addRating}>Rating</Button>
                        <Button onClick={this.addDate}>Date</Button>
                    </ButtonGroup>
                </div>
            </form>
        );
    }
}

export default FormContent;
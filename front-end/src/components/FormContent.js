import React, { Component } from 'react';
import SingleSelect from "./Radio";
import Checkbox from './Checkbox';
import Dropdown from "./Dropdown";
import Text from "./Text";
import Rating from "./Rating";
import Date from "./Date";
import * as API from "../api/API";
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

            // surveyName      :   this.props.surveydata.surveyName,
            surveyid        :   this.props.surveydata.surveyid
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
        API.addQuestion(this.state)
            .then((res) => {
                if(res.status === 201)
                {
                    var questions = this.state.questions;
                    questions.push(
                        <div className="form-inline">
                            <SingleSelect surveydata={res.data} handleSubmit={this.handleSubmit}/>
                        </div>
                    );
                    this.setState({
                        questions: questions
                    });
                }
            });
    }

    addCheckbox = (event) => {
        event.preventDefault();
        API.addQuestion(this.state)
            .then((res) => {
                if(res.status === 201)
                {
                    var questions = this.state.questions;
                    questions.push(
                        <div className="form-inline">
                            <Checkbox surveydata={res.data} handleSubmit={this.handleSubmit}/>
                        </div>
                    );
                    this.setState({
                        questions: questions
                    });
                }
            });
    }

    addDropdown = (event) => {
        event.preventDefault();
        API.addQuestion(this.state)
            .then((res) => {
                if(res.status === 201)
                {
                    var questions = this.state.questions;
                    questions.push(
                        <div className="form-inline">
                            <Dropdown surveydata={res.data} handleSubmit={this.handleSubmit}/>
                        </div>
                    );
                    this.setState({
                        questions: questions
                    });
                }
            });
    }
    addText = (event) => {
        event.preventDefault();
        API.addQuestion(this.state)
            .then((res) => {
                if(res.status === 201)
                {
                    var questions = this.state.questions;
                    questions.push(
                        <div className="form-inline">
                            <Text surveydata={res.data} handleSubmit={this.handleSubmit}/>
                        </div>
                    );
                    this.setState({
                        questions: questions
                    });
                }
            });
    }
    addRating = (event) => {
        event.preventDefault();
        API.addQuestion(this.state)
            .then((res) => {
                if(res.status === 201)
                {
                    var questions = this.state.questions;
                    questions.push(
                        <div className="form-inline">
                            <Rating surveydata={res.data} handleSubmit={this.handleSubmit}/>
                        </div>
                    );
                    this.setState({
                        questions: questions
                    });
                }
            });
    }
    addDate = (event) => {
        event.preventDefault();
        API.addQuestion(this.state)
            .then((res) => {
                if(res.status === 201)
                {
                    var questions = this.state.questions;
                    questions.push(
                        <div className="form-inline">
                            <Date surveydata={res.data} handleSubmit={this.handleSubmit}/>
                        </div>
                    );
                    this.setState({
                        questions: questions
                    });
                }
            });
    }


    publishSurvey = () => {
        // event.preventDefault();
        API.publishSurvey(this.state)
            .then((res) => {
                if(res.status === 200)
                {
                    console.log('inside 200');
                    alert("Survey is published successfully");
                }
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
                    </ButtonGroup><br/><br/>
                    <button className="btn btn-basic">Save</button>
                    <button className="btn btn-primary" onClick={this.publishSurvey}>Publish</button>
                </div>
            </form>
        );
    }
}

export default FormContent;
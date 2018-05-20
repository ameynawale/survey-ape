import React, { Component } from 'react';
import SingleSelect from "./Radio";
import Checkbox from './Checkbox';
import Dropdown from "./Dropdown";
import Text from "./Text";
import Rating from "./Rating";
import Date from "./Date";
import * as API from "../api/API";
import '../styles/FormContent.css';
import ExportSurveyModal from 'react-modal';
import {customStyles} from "./util/modalStyles";
import { Button, ButtonGroup } from 'reactstrap';


class FormContentEdit extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isGoing: true,
            numberOfGuests: 2,
            questions: [],
            exportSurveyIsOpen: false,
            questionsarray: this.props.surveydata.questions.questions,
            index: 0,

            // surveyName      :   this.props.surveydata.surveyName,
            surveyid        :   this.props.surveydata.questions.questions[0].surveyid,
            filename: 'SurveyJson',
            fileurl: 'http://localhost:8080/SurveyFiles/' + this.props.surveydata.questions.questions[0].surveyid

        };
        // var index = 0;

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        // this.handleExportSurvey = this.handleExportSurvey.bind(this);

        this.openExportSurveyModal = this.openExportSurveyModal.bind(this);
        this.closeExportSurveyModal = this.closeExportSurveyModal.bind(this);
    }

    openExportSurveyModal() {
        this.setState({
            exportSurveyIsOpen: true
        });
    }

    closeExportSurveyModal() {
        this.setState({exportSurveyIsOpen: false});
    }


    componentWillMount(){
        console.log("comp will mount ", this.state);
        console.log(this.props.surveydata.questions.questions[0].question);
        this.state.questionsarray.map((question) => {
            if(question.questiontype === 'dropdown')
            {
                this.state.questions.push(
                    <div className="form-inline">
                        <Dropdown surveydata={question} handleSubmit={this.handleSubmit}/>
                    </div>
                )
            }
            else if(question.questiontype === 'checkbox')
            {
                this.state.questions.push(
                    <div className="form-inline">
                        <Checkbox surveydata={question} handleSubmit={this.handleSubmit}/>
                    </div>
                )
            }
            else if(question.questiontype === 'radio')
            {
                this.state.questions.push(
                    <div className="form-inline">
                        <SingleSelect surveydata={question} handleSubmit={this.handleSubmit}/>
                    </div>
                )
            }
            else if(question.questiontype === 'text')
            {
                this.state.questions.push(
                    <div className="form-inline">
                        <Text surveydata={question} handleSubmit={this.handleSubmit}/>
                    </div>
                )
            }
            else if(question.questiontype === 'rating')
            {
                this.state.questions.push(
                    <div className="form-inline">
                        <Rating surveydata={question} handleSubmit={this.handleSubmit}/>
                    </div>
                )
            }

        })
        /*API.getQuestions(this.state)
            .then((res) => {
                console.log("questions", res.data.questions);
            })*/
    }

    handleExportSurvey = (event) => {
        event.preventDefault();
        API.exportSurvey(this.state);
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


    publishSurvey = (event) => {
        event.preventDefault();
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
                    <button className="btn btn-basic" onClick={(event) => {
                        alert("Your survey is saved successfully");
                        event.preventDefault();
                    }}>Save</button>
                    <button className="btn btn-primary" onClick={(event) => this.publishSurvey(event)}>Publish</button>
                    {/*<button className="btn btn-primary" onClick={(event) => this.handleExportSurvey(event)}>Export</button>*/}
                    <a href={this.state.fileurl} download>Export</a>
                </div>
                <ExportSurveyModal
                    isOpen={this.state.exportSurveyIsOpen}
                    onAfterOpen={this.openExportSurveyModal}
                    onRequestClose={this.closeExportSurveyModal}
                    style={customStyles}
                >
                    <div className="modal-body">
                        <form className="form-horizontal">
                            <input type="text" className="form-control" name="survey" placeholder = "Enter File Name"
                                   onChange={(event) => {
                                       this.setState({
                                           ...this.state,
                                           filename : event.target.value
                                       });
                                   }}/><br/>
                            <button type="button" id="exportSurvey" className="button-register"
                                     onClick={this.handleExportSurvey}
                            >Export</button>
                            <a href={this.state.fileurl} download>Export</a>
                        </form>
                    </div>
                </ExportSurveyModal>
            </form>
        );
    }
}

export default FormContentEdit;